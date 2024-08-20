package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.annotation.Up2Token;
import io.github.up2jakarta.csv.extension.ConfigurableProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;

/**
 * Up2 Configurable {@link ConfigurableProcessor} used by the annotation {@link Up2Token}
 * to clean up XML <code>xs:token</code>.
 */
@Named
@Singleton
public final class TokenProcessor implements ConfigurableProcessor<Up2Token> {

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
