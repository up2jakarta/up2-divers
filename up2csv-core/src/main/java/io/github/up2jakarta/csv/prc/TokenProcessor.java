package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Up2Token;
import io.github.up2jakarta.lov.core.Codes;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

/**
 * Up2 Configurable {@link InputProcessor} used by the annotation {@link Up2Token}
 * to clean up XML <code>xs:token</code>.
 */
@Named
@Singleton
public final class TokenProcessor extends InputProcessor<Up2Token> {

    @Override
    public String process(String value, Up2Token ignore) {
        return Codes.token(value);
    }

}
