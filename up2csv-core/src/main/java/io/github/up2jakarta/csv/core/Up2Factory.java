package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.BusinessBuilder;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.DataTypeResolver;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.*;

import java.util.List;

/**
 * Up2 Configurable Factory for {@link Up2Mapper} and {@link Up2Format}.
 *
 * @param <D> The business data-type
 */
@Named
@Singleton
public final class Up2Factory<D extends DataType<D>> {

    private final io.github.up2jakarta.csv.api.ext.BeanContext context;
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
    public Up2Factory(io.github.up2jakarta.csv.api.ext.BeanContext context, Validator validator, DataTypeResolver<D> resolver) {
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

    private <S extends Segment> Up2Format.Node<S, D> format(Class<S> t, DataTypeResolver<D> r) throws BeanException {
        final BeanContext vc = BeanContext.from(t);
        final BSContext<D> mc = new BSContext<>(context, t, true, r, vc, validator);
        final List<Property<?, D>> properties = new BeanScanner<>(mc).build(t);
        return new Up2Format.Node<>(validator, vc, false, properties);
    }

    private <S extends Segment> Up2Mapper.Node<S, D> build(Class<S> t, DataTypeResolver<D> r) throws BeanException {
        final BeanContext vc = BeanContext.from(t);
        final BSContext<D> mc = new BSContext<>(context, t, false, r, vc, validator);
        final List<Property<?, D>> properties = new BeanScanner<>(mc).build(t);
        return new Up2Mapper.Node<>(t, validator, vc, false, properties);
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map flat-data to bean-segment.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Up2Mapper<S, D> build(final Class<S> type) throws BeanException {
        return new Up2Mapper<>(type, this.build(type, resolver));
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map flat-data to bean-segment.
     *
     * @param type the type of segment that is being mapped
     * @param data the data type
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Up2Mapper<S, D> build(final Class<S> type, D data) throws BeanException {
        return new Up2Mapper<>(type, this.build(type, resolver.or(data)));
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Up2Format<S, D> format(final Class<S> type) throws BeanException {
        return new Up2Format<>(type, this.format(type, resolver));
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data.
     *
     * @param type the type of segment that is being mapped
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment> Up2Format<S, D> format(final Class<S> type, D data) throws BeanException {
        return new Up2Format<>(type, this.format(type, resolver.or(data)));
    }

    /**
     * Creates and returns new business builder.
     *
     * @return new instance of business-builder
     */
    public BusinessBuilder<D> builder() {
        return new BusinessBuilder<>(this);
    }

}
