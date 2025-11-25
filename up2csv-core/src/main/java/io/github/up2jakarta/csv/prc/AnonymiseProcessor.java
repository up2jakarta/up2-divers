package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Up2Anonymise;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Up2J Configurable {@link InputProcessor} used by the annotation {@link Up2Anonymise}
 * to anonymise confidential data.
 */
@Named
@Singleton
public final class AnonymiseProcessor extends InputProcessor<Up2Anonymise> {

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
