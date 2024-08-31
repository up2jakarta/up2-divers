package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.annotation.Up2Default;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Configurable {@link ConfigurableProcessor} used by the annotation {@link Up2Default}
 * to set the default value when input data is <code>null</code>.
 */
@Named
@Singleton
public final class DefaultProcessor extends ConfigurableProcessor<Up2Default> {

    @Override
    public String process(String value, Up2Default config) {
        if (value == null) {
            return config.value();
        }
        return value;
    }
}
