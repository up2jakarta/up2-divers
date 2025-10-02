package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.extension.BeanContext;
import io.github.up2jakarta.csv.extension.Nullable;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.misc.BeanException;
import io.github.up2jakarta.csv.misc.Beans;
import io.github.up2jakarta.csv.misc.Listable;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import static java.util.Objects.requireNonNull;

/**
 * Up2 Configurable Factory for {@link Mapper}.
 *
 * @param <D> The business data-type
 */
@Named
@Singleton
public final class MapperFactory<D extends DataType<D>> {

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
        final Configuration<?> cfg = Validation.byDefaultProvider()
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
     * Build a preconfigured CSV Mapper that is able to map input data to bean properties annotated by {@link Position}.
     * Note that only {@link String} property are supported.
     *
     * @param type the type of segment that is being mapped
     * @param data the data type
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Mapper<S, D> build(final Class<S> type, D data) throws BeanException {
        //noinspection unchecked
        return new DefaultMapper<>(type, context, validator, new DataTypeResolver<>((Class<D>) data.getClass()) {
            @Override
            public Optional<D> get(Stack<Class<? extends Segment>> stack, Field[] path, Field field) {
                return Optional.of(data);
            }
        });
    }

    /**
     * Internal Mapper implementation.
     *
     * @param <S> the segment type
     */
    private static final class DefaultMapper<S extends Segment, D extends DataType<D>> extends Mapper<S, D> {

        private final Constructor<S> constructor;
        private final Validator validator;
        private final int length;

        private DefaultMapper(Class<S> type, BeanContext context, Validator validator, DataTypeResolver<D> resolver) throws BeanException {
            super(type, context, resolver);
            this.validator = validator;
            this.constructor = Beans.getDefaultConstructor(type);
            this.length = this.offset(this.toList()) + 1;
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

        private int offset(List<Property<?, D>> properties) {
            int length = -1;
            int index;
            for (final Property<?, D> p : properties) {
                if (p instanceof MapperFactory.FragmentProperty<?, ?>) {
                    //noinspection unchecked
                    final FragmentProperty<?, D> fp = (FragmentProperty<?, D>) p;
                    index = offset(fp.properties);
                } else {
                    index = p.offset;
                }
                if (index > length) {
                    length = index;
                }
            }
            return length;
        }

        @Override
        protected <R extends InputSegment<?>, V extends InputError<R, ?, D>> void validate(
                Object bean, int offset, List<Property<?, D>> ps, Class<?>[] g, EventHandler<R, ?, D, V> h
        ) {
            final Set<ConstraintViolation<Object>> violations = validator.validate(bean, g);
            for (final ConstraintViolation<?> v : violations) {
                final Property<?, D> p = findProperty(v, ps);
                if (p != null) {
                    final Error config = p.field.getAnnotation(Error.class);
                    h.handleEvent(p.type, p.offset + offset, v, config);
                } else {
                    h.handleEvent(null, Integer.MAX_VALUE, v, null);
                }
            }
        }

        private <T extends Segment, R extends InputSegment<?>, V extends InputError<R, ?, D>> T parse(
                Constructor<T> constructor, List<Property<?, D>> properties, EventHandler<R, ?, D, V> collector,
                int offset, String... columns
        ) throws BeanException {
            final T bean = Beans.newInstance(constructor);
            for (final Property<?, D> p : properties) {
                if (p instanceof FragmentProperty<?, ?>) {
                    //noinspection unchecked
                    final FragmentProperty<?, D> fp = (FragmentProperty<?, D>) p;
                    final Segment fragment = parse(fp.constructor, fp.properties, collector, offset, columns);
                    if (fp.validation.isEnabled()) {
                        this.validate(fragment, offset, fp.properties, fp.validation.getGroups(), collector);
                    }
                    if (!(fragment instanceof Nullable nv) || nv.isNotNull()) {
                        Beans.setValue(bean, fragment, fp.setter);
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

        @SuppressWarnings("unchecked")
        private void format(
                String[] result, int offset, List<Property<?, D>> properties, Segment bean
        ) throws BeanException {
            for (final Property<?, D> p : properties) {
                if (p instanceof FragmentProperty<?, ?> fp) {
                    final FragmentProperty<?, D> fragment = (FragmentProperty<?, D>) fp;
                    var value = fragment.getValue(bean);
                    this.format(result, offset, fragment.properties, value);
                } else {
                    final PositionProperty<Object, D> pp = (PositionProperty<Object, D>) p;
                    var value = pp.getValue(bean);
                    result[offset + p.offset] = pp.format(value);
                }
            }
        }

        @Override
        public <R extends InputSegment<?>, V extends InputError<R, ?, D>> S map(
                EventHandler<R, ?, D, V> handler, int offset, String... columns
        ) throws BeanException {
            if (columns == null) {
                return null;
            }
            requireNonNull(handler, "handler is required");
            return parse(this.constructor, this.toList(), handler, offset, columns);
        }

        @Override
        public String[] unmap(S bean, int offset) throws BeanException {
            if (bean == null) {
                return null;
            }
            final String[] result = new String[offset + length];
            if (length != 0) {
                format(result, offset, this.toList(), bean);
            }
            return result;
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

        FragmentProperty(
                Class<T> sType, D dType, Field field, int offset, ValidationContext v, List<Property<?, D>> ps
        ) throws BeanException {
            super(field, dType, offset);
            this.constructor = Beans.getDefaultConstructor(sType);
            this.properties = ps;
            this.validation = v;
        }

        @Override
        public List<Property<?, D>> toList() {
            return this.properties;
        }

        @Override
        T defaultValue() throws BeanException {
            return Beans.newInstance(constructor);
        }
    }

}
