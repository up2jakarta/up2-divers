package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessBuilder;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Container;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.TermResolver;
import io.github.up2jakarta.csv.core.BSManager.Factory;
import io.github.up2jakarta.csv.data.WrapperExtractor;
import io.github.up2jakarta.lov.core.BeanException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Up2J Configurable Factory for {@link Up2Mapper} and {@link Up2Flatter}.
 *
 * @param <D> The business term type
 */
@Named
@Singleton
public final class Up2Factory<D extends ITerm<D>> extends Factory<D> {

    /**
     * Constructor for dependencies injection with all parameters.
     *
     * @param context   the beans container, must be not null
     * @param resolver  the term resolver, must be not null
     * @param validator the beans validator
     */
    @Inject
    public Up2Factory(Container context, TermResolver<D> resolver, Validator validator) {
        super(context, resolver, validator);
    }

    /**
     * Convenient factory method for JSR-303 validator.
     *
     * @param interpolator the message interpolator
     * @return the validator provided
     */
    public static Validator validator(MessageInterpolator interpolator) {
        final Configuration<?> cfg = Validation.byDefaultProvider()
                .configure()
                .addValueExtractor(new WrapperExtractor())
                .messageInterpolator(interpolator);
        try (final ValidatorFactory factory = cfg.buildValidatorFactory()) {
            return factory.getValidator();
        }
    }

    /**
     * Convenient factory method to create new factory with {@code empty} resolver and the {@code default} validator:
     * <ul>
     *     <li>Firstly, lookup the validator in the specified {@code context}</li>
     *     <li>Secondly, try to build the validator with JSR-303 API </li>
     *     <li>Else, the validation wil be disabled</li>
     * </ul>
     *
     * @param context the beans container
     * @param <B>     The business term type
     * @return a new factory with the specified {@code context}
     */
    public static <B extends ITerm<B>> Up2Factory<B> of(Container context) {
        //noinspection unchecked
        final TermResolver<B> empty = (TermResolver<B>) EmptyHolder.EMPTY;
        try {
            return new Up2Factory<>(context, empty, context.getBean(Validator.class));
        } catch (Exception ignore) {
        }
        try {
            return new Up2Factory<>(context, empty, validator(null));
        } catch (Exception ignore) {
            return new Up2Factory<>(context, empty, null);
        }
    }

    /**
     * Convenient factory method to create new factory with the new specified <code>resolver</code>.
     *
     * @param resolver the term resolver
     * @param <B>      The business term type
     * @return a new factory with the specified {@code resolver}
     */
    public <B extends ITerm<B>> Up2Factory<B> of(TermResolver<B> resolver) {
        return new Up2Factory<>(this.context, resolver, this.validator);
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

    /**
     * Internal Holder of Empty {@link TermResolver}.
     */
    private static class EmptyHolder {
        private static final TermResolver<?> EMPTY = new TermResolver<>(ITerm.class) {
            @Override
            public Optional<? extends ITerm<?>> get(List<Class<? extends Segment>> stack, Field[] path, Field field) {
                return Optional.empty();
            }

            @Override
            public Optional<? extends ITerm<?>> get(Optional<Field> field, Class<? extends Segment> type) {
                return Optional.empty();
            }
        };
    }

}
