package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Up2Token;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import static io.github.up2jakarta.csv.prc.TrimProcessor.trim;
import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Up2J Configurable {@link InputProcessor} used by the annotation {@link Up2Token}
 * to clean up XML <code>xs:token</code>.
 */
@Named
@Singleton
public final class TokenProcessor implements InputProcessor<Up2Token> {

    @Override
    public String process(String value, Up2Token config) {
        return trim(token(value), config.value());
    }

}
