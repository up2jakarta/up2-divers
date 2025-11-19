package io.github.up2jakarta.lov.core;

import io.github.up2jakarta.lov.TypeAdapter;

/**
 * Simple {@link TypeAdapter} used for {@link String} types.
 */
public final class StringAdapter implements TypeAdapter<String> {

    public static final StringAdapter INSTANCE = new StringAdapter();

    private StringAdapter() {
    }

    @Override
    public String parse(String value) {
        return value;
    }

    @Override
    public String format(String value) {
        return value;
    }

    @Override
    public Class<String> getSupportedType() {
        return String.class;
    }

}
