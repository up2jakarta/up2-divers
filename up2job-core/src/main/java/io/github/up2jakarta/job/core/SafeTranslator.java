package io.github.up2jakarta.job.core;

import java.util.function.Function;

public final class SafeTranslator<T extends RuntimeException> extends SafeWrapper<T> {

    private final Function<Exception, T> translator;

    public SafeTranslator(Function<Exception, T> translator) {
        this.translator = translator;
    }

    public void accept(Exception value) {
        if (this.value == null) {
            this.value = translator.apply(value);
        }
    }

}
