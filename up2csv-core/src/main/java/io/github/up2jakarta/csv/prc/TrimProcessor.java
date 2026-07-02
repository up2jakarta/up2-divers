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
public final class TrimProcessor implements InputProcessor<Up2Trim> {

    /**
     * Trim all the given values with {@link TrimProcessor#trim(String, String...)}.
     *
     * @param values the   input data
     * @param nulls  the values look like <code>null</code>
     * @return the input data that their values have been trimmed
     */
    public static String[] trim(String[] values, String... nulls) {
        if (nulls.length == 0 || values == null) {
            return values;
        }
        for (var i = 0; i < values.length; i++) {
            final String value = values[i];
            if (value != null) {
                values[i] = trim(value.trim(), nulls);
            }
        }
        return values;
    }

    /**
     * Trim given input value according to the given values looks like <code>null</code>.
     *
     * @param value input data
     * @param nulls the values look like <code>null</code>
     * @return data that has been trimmed
     */
    public static String trim(String value, String... nulls) {
        for (final String nullValue : nulls) {
            if (nullValue.equals(value)) {
                return null;
            }
        }
        return value;
    }

    @Override
    public String process(String value, Up2Trim config) {
        if (value != null) {
            return trim(value.trim(), config.value());
        }
        return null;
    }

}
