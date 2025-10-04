package io.github.up2jakarta.csv.prc;

import io.github.up2jakarta.csv.api.ext.InputProcessor;
import io.github.up2jakarta.csv.cfg.Up2Token;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;

/**
 * Up2 Configurable {@link InputProcessor} used by the annotation {@link Up2Token}
 * to clean up XML <code>xs:token</code>.
 */
@Named
@Singleton
public final class TokenProcessor extends InputProcessor<Up2Token> {

    private final CollapsedStringAdapter tokenAdapter;

    /**
     * Constructor with dependencies injections.
     *
     * @param tokenAdapter token XML adapter
     */
    @Inject
    public TokenProcessor(CollapsedStringAdapter tokenAdapter) {
        this.tokenAdapter = tokenAdapter;
    }

    @Override
    public String process(String value, Up2Token ignore) {
        return tokenAdapter.unmarshal(value);
    }

}
