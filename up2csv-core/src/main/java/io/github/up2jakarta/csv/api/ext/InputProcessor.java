package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.cfg.Processor;

import java.lang.annotation.Annotation;

/**
 * Up2J configurable {@link Processor} that processes the input data before setting the destination property.
 *
 * @param <C> the annotation type
 */
public interface InputProcessor<C extends Annotation> {

    /**
     * Processes {@link String} to the proper value of the destination field.
     *
     * @param value  the input data.
     * @param config the annotation that activate the resolution
     * @return the processed {@link String} for the destination field
     * @throws RuntimeException If any exception occurred during the processing
     */
    String process(String value, C config);

}
