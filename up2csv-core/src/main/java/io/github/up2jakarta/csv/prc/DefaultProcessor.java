package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Configurable {@link InputProcessor} used by the annotation {@link Position#defaultValue()}
 * to set the default value when input data is <code>null</code>.
 */
@Named
@Singleton
public final class DefaultProcessor implements InputProcessor<Position> {

    public static boolean undefined(Position config) {
        return config.defaultValue().isEmpty();
    }

    @Override
    public String process(String value, Position config) {
        if (value == null) {
            return config.defaultValue();
        }
        return value;
    }
}
