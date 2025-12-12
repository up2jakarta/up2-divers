package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessBuilder;
import io.github.up2jakarta.csv.core.BSOperator.Factory;
import io.github.up2jakarta.csv.data.DataResolver;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.WrapperValueExtractor;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

/**
 * Up2J Configurable Factory for {@link Up2Mapper} and {@link Up2Flatter}.
 *
 * @param <D> The business data type
 */
@Named
@Singleton
public final class Up2Factory<D extends DataType<D>> extends Factory<D> {

    /**
     * Constructor with empty resolver.
     *
     * @param context the bean context
     */
    public Up2Factory(BeanContext context) {
        super(context, DataResolver.empty());
    }

    /**
     * Constructor for dependencies injection.
     *
     * @param context  the bean context
     * @param resolver the default business data-type resolver
     */
    @Inject
    public Up2Factory(BeanContext context, DataResolver<D> resolver) {
        super(context, resolver);
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
                .addValueExtractor(new WrapperValueExtractor())
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
     * @see Up2Factory#build(Class, DataResolver)
     */
    public <S extends Segment> Up2Mapper<S, D> build(final Class<S> type) throws BeanException {
        return BSManager.of(this, type, resolver).build(Up2Mapper::new);
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map flat-data to bean-segment.
     * <p>
     * If the bean is already scanned for mapping, {@link Up2Flatter#toMapper()} is much faster
     *
     * @param st  the type of segment that is being mapped
     * @param dr  the {@link B} resolver
     * @param <S> The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment, B extends DataType<B>> Up2Mapper<S, B> build(Class<S> st, DataResolver<B> dr) throws BeanException {
        return BSManager.of(this, st, dr).build(Up2Mapper::new);
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data with default resolver.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     * @see Up2Factory#format(Class, DataResolver)
     */
    public <S extends Segment> Up2Flatter<S, D> format(final Class<S> type) throws BeanException {
        return BSManager.ft(this, resolver, type).build(Up2Flatter::new);
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data.
     * <p>
     * If the bean is already scanned for mapping, {@link Up2Mapper#toFlatter()} is much faster
     *
     * @param st  the type of segment that is being mapped
     * @param dr  the {@link B} resolver
     * @param <S> The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment, B extends DataType<B>> Up2Flatter<S, B> format(Class<S> st, DataResolver<B> dr) throws BeanException {
        return BSManager.ft(this, dr, st).build(Up2Flatter::new);
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
