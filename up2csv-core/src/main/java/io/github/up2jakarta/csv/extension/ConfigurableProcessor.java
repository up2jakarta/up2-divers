package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.annotation.Processor;

import java.lang.annotation.Annotation;

/**
 * Up2 configurable {@link Processor} that processes the input data before setting the destination property.
 *
 * @param <A> the annotation type
 */
public abstract class ConfigurableProcessor<A extends Annotation> {

    /**
     * Processes {@link String} to the proper value of the destination field.
     *
     * @param value  the input data.
     * @param config the annotation that activate the resolution
     * @return the processed {@link String} for the destination field
     * @throws RuntimeException If any error during the processing
     */
    public abstract String process(String value, A config) throws RuntimeException;

}
