package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Up2Trim;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Configurable {@link InputProcessor} used by the annotation {@link Up2Trim}
 * to trim {@link String} in order to avoid the parsing of data looks like <code>null</code>.
 */
@Named
@Singleton
public final class TrimProcessor extends InputProcessor<Up2Trim> {

    /**
     * Trim all the given values with {@link TrimProcessor#trim(String, String...)}.
     *
     * @param values     input data
     * @param nullValues values looks like <code>null</code>
     */
    public static void trim(String[] values, String... nullValues) {
        if (values == null || values.length == 0) {
            return;
        }
        for (var i = 0; i < values.length; i++) {
            values[i] = trim(values[i], nullValues);
        }
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
