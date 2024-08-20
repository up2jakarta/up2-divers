package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.Listable;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRow;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

/**
 * Up2 Configurable Factory for {@link Mapper}.
 */
@Named
@Singleton
public final class MapperFactory {

    /**
     * Mapper Factory Logger.
     */
    public final static Logger LOGGER = LoggerFactory.getLogger(MapperFactory.class);

    private final BeanContext context;
    private final Validator validator;

    /**
     * Constructor with dependencies injections.
     *
     * @param context   the bean context
     * @param validator the JS-303 validator
     */
    @Inject
    public MapperFactory(BeanContext context, Validator validator) {
        this.context = context;
        this.validator = validator;
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map input data to bean properties annotated by {@link Position}.
     * Note that only {@link String} property are supported.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Mapper<S> build(final Class<S> type) throws BeanException {
        BeanSupport.checkBean(type);
        return new DefaultMapper<>(type, context, validator);
    }

    /**
     * Internal Mapper implementation.
     *
     * @param <S> the segment type
     */
    private static final class DefaultMapper<S extends Segment> extends Mapper<S> implements Listable<Property<?>> {

        private final Constructor<S> constructor;
        private final Property<?>[] properties;
        private final Validator validator;

        private DefaultMapper(Class<S> type, BeanContext context, Validator validator) throws BeanException {
            super(type);
            this.validator = validator;
            this.constructor = Beans.getDefaultConstructor(type);
            this.properties = BeanSupport.getProperties(type, new BeanStack(context));
        }

        @Override
        <R extends InputRow, V extends InputError<R, ?>> void validate(Object bean, Class<?>[] g, EventHandler<R, ?, V> h) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(bean, g);
            for (final ConstraintViolation<?> v : violations) {
                final Property<?> p = findProperty(v);
                if (p != null) {
                    final Error config = p.field.getAnnotation(Error.class);
                    h.handleEvent(p.offset + this.offset, v, config);
                } else {
                    LOGGER.warn("Unknown {}.{} has error: {}", v.getRootBeanClass(), v.getPropertyPath(), v.getMessage());
                }
            }
        }

        private Property<?> findProperty(final ConstraintViolation<?> violation) {
            final String path = violation.getPropertyPath().toString();
            final String[] fieldNames = path.split("\\.");
            Property<?> property = null;
            Property<?>[] properties = this.properties;
            for (String fieldName : fieldNames) {
                property = stream(properties).filter(p -> fieldName.equals(p.field.getName())).findFirst().orElse(null);
                if (property instanceof MapperFactory.FragmentProperty<?> fp) {
                    properties = fp.properties;
                } else {
                    return property;
                }
            }
            return property;
        }

        private <T extends Segment> T map(Constructor<T> constructor, Property<?>[] properties, EventHandler<?, ?, ?> collector, String... columns) throws BeanException {
            final T bean = Beans.newInstance(constructor);
            for (final Property<?> p : properties) {
                if (p instanceof FragmentProperty<?> fp) {
                    final Segment fragment = map(fp.constructor, fp.properties, collector, columns);
                    fp.setValue(bean, fragment, offset, collector);
                } else {
                    final int index = p.offset;
                    final String value = (index < columns.length) ? columns[index] : null;
                    //noinspection unchecked
                    ((PositionProperty<String>) p).setValue(bean, value, offset, collector);
                }
            }
            return bean;
        }

        @Override
        public <R extends InputRow, V extends InputError<R, ?>> S map(EventHandler<R, ?, V> handler, String... columns) throws BeanException {
            if (columns == null) {
                return null;
            }
            requireNonNull(handler, "handler is required");
            return map(this.constructor, this.properties, handler, columns);
        }

        @Override
        public List<Property<?>> toList() {
            return List.of(properties);
        }

    }

    /**
     * Internal Fragment implementation.
     *
     * @param <T> the fragment type
     */
    final static class FragmentProperty<T extends Segment> extends Property<Segment> implements Listable<Property<?>> {

        final Constructor<T> constructor;
        private final Property<?>[] properties;

        FragmentProperty(Class<T> type, Field field, int offset, Property<?>[] properties) throws BeanException {
            super(field, offset);
            this.constructor = Beans.getDefaultConstructor(type);
            this.properties = properties;
        }

        @Override
        void setValue(Object bean, Segment value, int offset, EventHandler<?, ?, ?> ignore) throws BeanException {
            Beans.setValue(bean, value, setter);
        }

        @Override
        public List<Property<?>> toList() {
            return List.of(properties);
        }

    }

}
