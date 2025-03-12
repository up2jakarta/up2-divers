package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.annotation.Up2Anonymise;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Up2 Configurable {@link ConfigurableProcessor} used by the annotation {@link Up2Anonymise}
 * to anonymise confidential data.
 */
@Named
@Singleton
public final class AnonymiseProcessor extends ConfigurableProcessor<Up2Anonymise> {

    @Override
    public String process(String value, Up2Anonymise config) {
        if (value == null) {
            return null;
        }
        final char[] sequence = value.toCharArray();
        for (var i = config.from(); i < sequence.length - config.until(); i++) {
            sequence[i] = config.value();
        }
        return new String(sequence);
    }

}
