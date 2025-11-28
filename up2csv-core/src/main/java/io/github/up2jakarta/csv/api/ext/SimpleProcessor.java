package io.github.up2jakarta.csv.api.ext;

import java.lang.annotation.Annotation;

/**
 * Simple base implementation of {@link InputProcessor} activated by marker annotation without extra-configuration.
 *
 * @param <C> the annotation type
 */
public abstract class SimpleProcessor<C extends Annotation> implements InputProcessor<C> {

    @Override
    public final String process(String value, C config) throws RuntimeException {
        return this.process(value);
    }

    /**
     * Processes {@link String} to the proper value of the destination field.
     *
     * @param value the input data.
     * @return the processed {@link String} for the destination field
     * @throws RuntimeException If any exception occurred during the processing
     */
    protected abstract String process(String value) throws RuntimeException;

}
