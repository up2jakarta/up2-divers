package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessBuilder;
import io.github.up2jakarta.csv.api.ext.BeanContext;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

/**
 * Up2 Configurable Factory for {@link Up2Mapper} and {@link Up2Format}.
 *
 * @param <D> The input data type
 */
@Named
@Singleton
public final class Up2Factory<D extends DataType<D>> {

    final BeanContext context;
    final Validator validator;
    final DataTypeResolver<D> resolver;

    /**
     * Constructor with empty resolver.
     *
     * @param context the bean context
     * @param type    the data type
     */
    public Up2Factory(BeanContext context, Class<D> type) {
        this(context, context.getBean(Validator.class), DataTypeResolver.empty(type));
    }

    /**
     * Constructor for dependencies injection.
     *
     * @param context   the bean context
     * @param validator the JS-303 validator
     * @param resolver  the default business data-type resolver
     */
    @Inject
    public Up2Factory(BeanContext context, Validator validator, DataTypeResolver<D> resolver) {
        this.validator = validator;
        this.context = context;
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
     * Build a preconfigured CSV Mapper that is able to map flat-data to bean-segment with default resolver.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     * @see Up2Factory#format(Class, DataTypeResolver)
     */
    public <S extends Segment> Up2Mapper<S, D> build(final Class<S> type) throws BeanException {
        return new Up2Mapper<>(type, BSContext.build(type, this, resolver));
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map flat-data to bean-segment.
     * <p>
     * If the bean is already scanned for mapping, {@link Up2Format#toMapper()} is much faster
     *
     * @param type the type of segment that is being mapped
     * @param dtr  the {@link DataType} resolver
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    <S extends Segment, B extends DataType<B>> Up2Mapper<S, B> build(Class<S> type, DataTypeResolver<B> dtr) throws BeanException {
        return new Up2Mapper<>(type, BSContext.build(type, this, dtr));
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data with default resolver.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     * @see Up2Factory#format(Class, DataTypeResolver)
     */
    public <S extends Segment> Up2Format<S, D> format(final Class<S> type) throws BeanException {
        return new Up2Format<>(type, BSContext.format(type, this, resolver));
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data.
     * <p>
     * If the bean is already scanned for mapping, {@link Up2Mapper#toFormat()} is much faster
     *
     * @param type the type of segment that is being mapped
     * @param dtr  the {@link DataType} resolver
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment, B extends DataType<B>> Up2Format<S, B> format(Class<S> type, DataTypeResolver<B> dtr) throws BeanException {
        return new Up2Format<>(type, BSContext.format(type, this, dtr));
    }

    /**
     * Creates and returns new business builder that's able to build multi-segments format processors.
     *
     * @return new instance of business-builder
     * @see BusinessExporter
     * @see BusinessImporter
     */
    public BusinessBuilder<D> builder() {
        return new BusinessBuilder<>(this);
    }

}
