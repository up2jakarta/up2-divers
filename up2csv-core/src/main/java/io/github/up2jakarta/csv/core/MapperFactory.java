package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessBuilder;
import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

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
        return new DefaultMapper<>(type, resolver);
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
        return new DefaultMapper<>(type, resolver.or(data));
    }

    /**
     * Creates and returns new business builder.
     *
     * @return new instance of business-builder
     */
    public BusinessBuilder<D> builder() {
        return new BusinessBuilder<>(this);
    }

    /**
     * Internal Mapper implementation.
     */
    final class DefaultMapper<S extends Segment> extends Mapper<S, D> {

        private final int length;

        private DefaultMapper(Class<S> type, DataTypeResolver<D> resolver) throws BeanException {
            super(type, context, resolver);
            this.length = node.offset() + 1;
        }

        @Override
        protected <R extends IRecord<?>, E extends IError<D>> void validate(Object bean, int offset, BeanNode<?, D> node, EventHandler<R, D, E> handler) {
            node.validate(bean, offset, validator, handler);
        }

        @Override
        public <R extends IRecord<?>, E extends IError<D>> S map(EventHandler<R, D, E> handler, int offset, String... columns) throws BeanException {
            if (columns == null) {
                return null;
            }
            requireNonNull(handler, "handler is required");
            return node.parse(validator, handler, offset, columns);
        }

        @Override
        public String[] unmap(S bean, int offset) throws BeanException {
            if (bean == null) {
                return null;
            }
            final String[] result = new String[offset + length];
            if (length != 0) {
                node.format(result, offset, bean);
            }
            return result;
        }

    }

}
