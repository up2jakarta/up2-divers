package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.cfg.Error;

import java.util.Optional;

/**
 * Property converter that wraps the parsing and formatting functions.
 *
 * @param <R> the target type
 */
public final class Conversion<R> {

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

    public PropertyConverter<R> converter() {
        return converter;
    }

    public PropertyFormatter<R> formatter() {
        return formatter;
    }

}
