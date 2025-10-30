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

import java.util.List;

/**
 * Up2 Configurable Factory for {@link Up2Mapper} and {@link Up2Format}.
 *
 * @param <D> The business data-type
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

    private <S extends Segment, B extends DataType<B>> Up2Format.Node<S, B> fmt(Class<S> t, DataTypeResolver<B> r) throws BeanException {
        final io.github.up2jakarta.csv.core.BeanContext vc = io.github.up2jakarta.csv.core.BeanContext.from(t);
        final BSContext<B> mc = new BSContext<>(context, t, true, r, vc, validator);
        final List<Property<?, B>> properties = new BeanScanner<>(mc).build(t);
        return new Up2Format.Node<>(validator, vc, false, properties);
    }

    private <S extends Segment, B extends DataType<B>> Up2Mapper.Node<S, B> mpr(Class<S> t, DataTypeResolver<B> r) throws BeanException {
        final io.github.up2jakarta.csv.core.BeanContext vc = io.github.up2jakarta.csv.core.BeanContext.from(t);
        final BSContext<B> mc = new BSContext<>(context, t, false, r, vc, validator);
        final List<Property<?, B>> properties = new BeanScanner<>(mc).build(t);
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
        return this.build(type, resolver);
    }

    /**
     * Build a preconfigured CSV Mapper that is able to map flat-data to bean-segment.
     *
     * @param type the type of segment that is being mapped
     * @param dtr  the {@link DataType} resolver
     * @param <S>  The class of segment
     * @return the CSV mapper
     * @throws BeanException for any missing or wrong bean configuration
     */
    <S extends Segment, B extends DataType<B>> Up2Mapper<S, B> build(Class<S> type, DataTypeResolver<B> dtr) throws BeanException {
        return new Up2Mapper<>(type, this.mpr(type, dtr));
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
        return this.format(type, resolver);
    }

    /**
     * Build a preconfigured CSV Format that is able to map bean-segment to flat-data.
     *
     * @param type the type of segment that is being mapped
     * @param dtr  the {@link DataType} resolver
     * @param <S>  The class of segment
     * @return the CSV Format
     * @throws BeanException for any missing or wrong bean configuration
     */
    public <S extends Segment, B extends DataType<B>> Up2Format<S, B> format(Class<S> type, DataTypeResolver<B> dtr) throws BeanException {
        return new Up2Format<>(type, this.fmt(type, dtr));
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
