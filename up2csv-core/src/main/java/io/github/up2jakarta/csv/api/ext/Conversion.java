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

    public static final Conversion<String> NAN = new Conversion<>(String.class, (v) -> v, (v) -> v);

    private final Class<R> type;
    private final PropertyConverter<R> converter;
    private final PropertyFormatter<R> formatter;

    /**
     * @param type      the supported type
     * @param converter the parsing function
     * @param formatter the formatting function
     * @param config    the error configuration
     */
    public Conversion(Class<R> type, PropertyConverter<R> converter, PropertyFormatter<R> formatter, Optional<Error> config) {
        this(type, converter, formatter, config.orElse(null));
    }

    /**
     * @param type      the supported type
     * @param converter the parsing function
     * @param formatter the formatting function
     * @param config    the error configuration
     */
    public Conversion(Class<R> type, PropertyConverter<R> converter, PropertyFormatter<R> formatter, Error config) {
        this(type, PropertyConverter.of(converter, config), PropertyFormatter.of(formatter, config));
    }

    /**
     * @param type      the supported type
     * @param converter the parsing function
     * @param formatter the formatting function
     */
    public Conversion(Class<R> type, PropertyConverter<R> converter, PropertyFormatter<R> formatter) {
        this.converter = converter;
        this.formatter = formatter;
        this.type = type;
    }

    /**
     * Creates Property conversion from {@link TypeConverter}
     *
     * @param tc    the type converter
     * @param error the configuration of errors
     * @param <T>   the type of property
     * @return new preconfigured conversion
     */
    public static <T> Conversion<T> of(TypeConverter<T> tc, Error error) {
        if (error == null) {
            final PropertyConverter<T> p = PropertyConverter.of(tc::parse, tc.getErrorSeverity(), tc.getErrorCode());
            final PropertyFormatter<T> f = PropertyFormatter.of(tc::format, tc.getErrorSeverity(), tc.getErrorCode());
            return new Conversion<>(tc.getSupportedType(), p, f);
        }
        return new Conversion<>(tc.getSupportedType(), tc::parse, tc::format, error);
    }

    public PropertyConverter<R> converter() {
        return converter;
    }

    public PropertyFormatter<R> formatter() {
        return formatter;
    }

    public Class<R> type() {
        return type;
    }

}
