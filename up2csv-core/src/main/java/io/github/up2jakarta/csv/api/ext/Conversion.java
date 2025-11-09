package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.xml.api.TypeConverter;

import java.util.Optional;

/**
 * Property converter that wraps the parsing and formatting functions.
 *
 * @param <R> the target type
 */
public final class Conversion<R> {

    public static final Conversion<String> NAN = new Conversion<>((v) -> v, (v) -> v);

    private final PropertyConverter<R> converter;
    private final PropertyFormatter<R> formatter;

    /**
     * @param converter the parsing function
     * @param formatter the formatting function
     * @param config    the error configuration
     */
    public Conversion(PropertyConverter<R> converter, PropertyFormatter<R> formatter, Optional<Error> config) {
        this(converter, formatter, config.orElse(null));
    }

    /**
     * @param converter the parsing function
     * @param formatter the formatting function
     * @param config    the error configuration
     */
    public Conversion(PropertyConverter<R> converter, PropertyFormatter<R> formatter, Error config) {
        this(PropertyConverter.of(converter, config), PropertyFormatter.of(formatter, config));
    }

    /**
     * @param converter the parsing function
     * @param formatter the formatting function
     */
    public Conversion(PropertyConverter<R> converter, PropertyFormatter<R> formatter) {
        this.converter = converter;
        this.formatter = formatter;
    }

    /**
     * Creates Property conversion from {@link TypeConverter}
     *
     * @param cvr   the type converter
     * @param error the configuration of errors
     * @param <T>   the type of property
     * @return new preconfigured conversion
     */
    public static <T> Conversion<T> of(TypeConverter<T> cvr, Error error) {
        if (error == null) {
            final PropertyConverter<T> p = PropertyConverter.of(cvr::parse, cvr.getErrorSeverity(), cvr.getErrorCode());
            final PropertyFormatter<T> f = PropertyFormatter.of(cvr::format, cvr.getErrorSeverity(), cvr.getErrorCode());
            return new Conversion<>(p, f);
        }
        return new Conversion<>(cvr::parse, cvr::format, error);
    }

    public PropertyConverter<R> converter() {
        return converter;
    }

    public PropertyFormatter<R> formatter() {
        return formatter;
    }

}
