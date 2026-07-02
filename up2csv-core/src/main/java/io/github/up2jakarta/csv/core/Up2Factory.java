package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessBuilder;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.core.BSManager.Factory;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.TermResolver;
import io.github.up2jakarta.csv.data.WrapperValueExtractor;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

/**
 * Up2J Configurable Factory for {@link Up2Mapper} and {@link Up2Flatter}.
 *
 * @param <D> The business term type
 */
@Named
@Singleton
public final class Up2Factory<D extends ITerm<D>> extends Factory<D> {

    /**
     * Constructor with empty resolver.
     *
     * @param container the beans container
     */
    public Up2Factory(Container container) {
        super(container, TermResolver.empty());
    }

    /**
     * Constructor for override the resolver of the specified <code>source</code>.
     *
     * @param source   the source factory
     * @param resolver the term resolver
     */
    public Up2Factory(Up2Factory<?> source, TermResolver<D> resolver) {
        super(source.context, resolver);
    }

    /**
     * Constructor for dependencies injection.
     *
     * @param context  the bean context
     * @param resolver the term resolver
     */
    @Inject
    public Up2Factory(Container context, TermResolver<D> resolver) {
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
     */
    public <S extends Segment> Up2Mapper<S, D> mapper(final Class<S> type) throws BeanException {
        return BSManager.<D, S>of(this).mp(this, type).build(validator, Up2Mapper::new);
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data with default resolver.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Up2Flatter<S, D> flatter(final Class<S> type) throws BeanException {
        return BSManager.<D, S>of(this).ft(this, type).build(validator, Up2Flatter::new);
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
