package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.processor.TrimTransformer;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public final class CSV {

    private CSV() {
    }

    public static Validator validator(MessageInterpolator interpolator) {
        final jakarta.validation.Configuration<?> cfg = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(interpolator);
        try (final ValidatorFactory factory = cfg.buildValidatorFactory()) {
            return factory.getValidator();
        }
    }

    /**
     * Trim all the given values with {@link TrimTransformer#trim(String, String...)}.
     *
     * @param values     CSV Data
     * @param nullValues values looks like <code>null</code>
     * @return CSV data that has been trimmed
     */
    public static String[] trim(String[] values, String... nullValues) {
        if (values == null || values.length == 0) {
            return values;
        }
        final String[] result = new String[values.length];
        for (var i = 0; i < values.length; i++) {
            result[i] = TrimTransformer.trim(values[i], nullValues);
        }
        return result;
    }

}
