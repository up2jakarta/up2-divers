package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.annotation.Error;

import java.util.Optional;

/**
 * Property converter that wraps the parsing and formatting functions.
 *
 * @param <R> the target type
 */
public final class Conversion<R> {

    private final PropertyParser<R> parser;
    private final PropertyFormatter<R> formatter;

    /**
     * @param parser    the parser function
     * @param formatter the formatting function
     * @param config    the error configuration
     */
    public Conversion(PropertyParser<R> parser, PropertyFormatter<R> formatter, Optional<Error> config) {
        this(parser, formatter, config.orElse(null));
    }

    /**
     * @param parser    the parser function
     * @param formatter the formatting function
     * @param config    the error configuration
     */
    public Conversion(PropertyParser<R> parser, PropertyFormatter<R> formatter, Error config) {
        this(PropertyParser.of(parser, config), PropertyFormatter.of(formatter, config));
    }

    /**
     * @param parser    the parser function
     * @param formatter the formatting function
     */
    public Conversion(PropertyParser<R> parser, PropertyFormatter<R> formatter) {
        this.parser = parser;
        this.formatter = formatter;
    }

    public PropertyParser<R> parser() {
        return parser;
    }

    public PropertyFormatter<R> formatter() {
        return formatter;
    }

}
