package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.extension.ConfigurableTransformer;

/**
 * Configurable {@link ConfigurableTransformer} used by the annotation {@link io.github.up2jakarta.csv.annotation.Trim}
 * to trim {@link String} in order to avoid the parsing of data looks like <code>null</code>.
 */
public class TrimTransformer implements ConfigurableTransformer {

    public static String trim(String value, String... nullValues) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        for (final String nullValue : nullValues) {
            if (nullValue.equals(value)) {
                return null;
            }
        }
        return value;
    }

    @Override
    public String transform(String value, String... nullValues) {
        return TrimTransformer.trim(value, nullValues);
    }

}
