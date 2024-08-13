package io.github.up2jakarta.csv.processor;

import io.github.up2jakarta.csv.extension.ConfigurableTransformer;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;

/**
 * Up2 Configurable {@link ConfigurableTransformer} used by the annotation
 * {@link io.github.up2jakarta.csv.annotation.Token} to clean up XML <code>xs:token</code>.
 */
public class TokenTransformer extends NoArgumentTransformer {

    private final CollapsedStringAdapter tokenAdapter;

    public TokenTransformer(CollapsedStringAdapter tokenAdapter) {
        this.tokenAdapter = tokenAdapter;
    }

    @Override
    public String transform(String value) {
        return tokenAdapter.unmarshal(value);
    }

}
