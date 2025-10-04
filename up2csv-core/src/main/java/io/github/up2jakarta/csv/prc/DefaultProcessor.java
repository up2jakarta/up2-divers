package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Up2Default;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Configurable {@link InputProcessor} used by the annotation {@link Up2Default}
 * to set the default value when input data is <code>null</code>.
 */
@Named
@Singleton
public final class DefaultProcessor extends InputProcessor<Up2Default> {

    @Override
    public String process(String value, Up2Default config) {
        if (value == null) {
            return config.value();
        }
        return value;
    }
}
