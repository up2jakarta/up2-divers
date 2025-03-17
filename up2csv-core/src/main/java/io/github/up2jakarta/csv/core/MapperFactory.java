package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.extension.*;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.misc.Beans;
import io.github.up2jakarta.csv.misc.Listable;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Up2 Configurable Factory for {@link Mapper}.
 *
 * @param <D> The business data-type
 */
@Named
@Singleton
public final class MapperFactory<D extends DataType<D>> {

    /**
     * Mapper Factory Logger.
     */
    final static Logger LOGGER = LoggerFactory.getLogger(MapperFactory.class);

    private final BeanContext context;
    private final Validator validator;
    private final DataTypeResolver<D> resolver;

    /**
     * Constructor with dependencies injections.
     *
     * @param context   the bean context
     * @param validator the JS-303 validator
     * @param resolver  the business data-type resolver
     */
    @Inject
    public MapperFactory(BeanContext context, Validator validator, DataTypeResolver<D> resolver) {
        this.context = context;
        this.validator = validator;
        this.resolver = resolver;
    }

    /**
     * Utility method for create JSR-303 validator.
     *
     * @param interpolator the message interpolator
     * @return the validator provided
     */
    public static Validator validator(MessageInterpolator interpolator) {
        final jakarta.validation.Configuration<?> cfg = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(interpolator);
        try (final ValidatorFactory factory = cfg.buildValidatorFactory()) {
            return factory.getValidator();
        }
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
    public <S extends Segment> Mapper<S, D> build(final Class<S> type) throws BeanException {
        return new DefaultMapper<>(type, context, validator, resolver);
    }

    /**
     * Internal Mapper implementation.
     *
     * @param <S> the segment type
     */
    private static final class DefaultMapper<S extends Segment, D extends DataType<D>> extends Mapper<S, D> {

        private final Constructor<S> constructor;
        private final Validator validator;

        private DefaultMapper(Class<S> type, BeanContext context, Validator validator, DataTypeResolver<D> resolver) throws BeanException {
            super(type, context, resolver);
            this.validator = validator;
            this.constructor = Beans.getDefaultConstructor(type);
        }

        private Property<?, D> findProperty(final ConstraintViolation<?> violation, List<Property<?, D>> origin) {
            final String path = violation.getPropertyPath().toString();
            final String[] fieldNames = path.split("\\.");
            Property<?, D> property = null;
            List<Property<?, D>> properties = origin;
            for (String fieldName : fieldNames) {
                property = properties.stream().filter(p -> fieldName.equals(p.field.getName())).findFirst().orElse(null);
                if (property instanceof FragmentProperty<?, ?> fp) {
                    //noinspection ALL
                    properties = ((FragmentProperty<?, D>) fp).properties;
                } else {
                    return property;
                }
            }
            return property;
        }

        @Override
        <R extends InputSegment<?>, V extends InputError<R, ?, D>> void validate(Object bean, List<Property<?, D>> ps, Class<?>[] g, EventHandler<R, ?, D, V> h) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(bean, g);
            for (final ConstraintViolation<?> v : violations) {
                final Property<?, D> p = findProperty(v, ps);
                if (p != null) {
                    final Error config = p.field.getAnnotation(Error.class);
                    h.handleEvent(p.type, p.offset + this.offset, v, config);
                } else {
                    h.handleEvent(null, Integer.MAX_VALUE, v, null);
                }
            }
        }

        private <T extends Segment, R extends InputSegment<?>, V extends InputError<R, ?, D>> T map(Constructor<T> constructor, List<Property<?, D>> properties, EventHandler<R, ?, D, V> collector, String... columns) throws BeanException {
            final T bean = Beans.newInstance(constructor);
            for (final Property<?, D> p : properties) {
                if (p instanceof FragmentProperty<?, ?>) {
                    //noinspection unchecked
                    final FragmentProperty<?, D> fp = (FragmentProperty<?, D>) p;
                    final Segment fragment = map(fp.constructor, fp.properties, collector, columns);
                    if (fp.validation.isEnabled()) {
                        this.validate(fragment, fp.properties, fp.validation.getGroups(), collector);
                    }
                    if (!(fragment instanceof Nullable nv) || nv.isNotNull()) {
                        fp.setValue(bean, fragment, offset, collector);
                    }
                } else {
                    final int index = p.offset;
                    final String value = (index < columns.length) ? columns[index] : null;
                    //noinspection unchecked
                    ((PositionProperty<String, D>) p).setValue(bean, value, offset, collector);
                }
            }
            return bean;
        }

        @Override
        public <R extends InputSegment<?>, V extends InputError<R, ?, D>> S map(EventHandler<R, ?, D, V> handler, String... columns) throws BeanException {
            if (columns == null) {
                return null;
            }
            requireNonNull(handler, "handler is required");
            return map(this.constructor, this.properties, handler, columns);
        }

    }

    /**
     * Internal Fragment implementation.
     *
     * @param <T> the fragment type
     */
    final static class FragmentProperty<T extends Segment, D extends DataType<D>> extends Property<Segment, D> implements Listable<Property<?, D>> {

        private final List<Property<?, D>> properties;
        private final ValidationContext validation;
        private final Constructor<T> constructor;

        FragmentProperty(Class<T> sType, D dType, Field field, int offset, ValidationContext v, List<Property<?, D>> ps) throws BeanException {
            super(field, dType, offset);
            this.constructor = Beans.getDefaultConstructor(sType);
            this.properties = ps;
            this.validation = v;
        }

        @Override
        void setValue(Object bean, Segment value, int offset, EventHandler<?, ?, D, ?> ignore) throws BeanException {
            Beans.setValue(bean, value, setter);
        }

        @Override
        public List<Property<?, D>> toList() {
            return properties;
        }

    }

}
