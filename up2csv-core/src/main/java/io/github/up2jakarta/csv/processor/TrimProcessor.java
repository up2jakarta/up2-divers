package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.annotation.Up2Trim;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Configurable {@link ConfigurableProcessor} used by the annotation {@link Up2Trim}
 * to trim {@link String} in order to avoid the parsing of data looks like <code>null</code>.
 */
@Named
@Singleton
public final class TrimProcessor extends ConfigurableProcessor<Up2Trim> {

    /**
     * Trim all the given values with {@link TrimProcessor#trim(String, String...)}.
     *
     * @param values     input data
     * @param nullValues values looks like <code>null</code>
     * @return data that has been trimmed
     */
    public static String[] trim(String[] values, String... nullValues) {
        if (values == null || values.length == 0) {
            return values;
        }
        final String[] result = new String[values.length];
        for (var i = 0; i < values.length; i++) {
            result[i] = trim(values[i], nullValues);
        }
        return result;
    }

    /**
     * Trim given input value according to the given values looks like <code>null</code>.
     *
     * @param value      input data
     * @param nullValues values looks like <code>null</code>
     * @return data that has been trimmed
     */
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
    public String process(String value, Up2Trim config) {
        return TrimProcessor.trim(value, config.value());
    }

}
