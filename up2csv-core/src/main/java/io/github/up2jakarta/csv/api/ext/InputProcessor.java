package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.cfg.Processor;
import io.github.up2jakarta.csv.core.ext.BeanAware;

import java.lang.annotation.Annotation;

/**
 * Up2 configurable {@link Processor} that processes the input data before setting the destination property.
 *
 * @param <A> the annotation type
 */
public abstract class InputProcessor<A extends Annotation> extends BeanAware {

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
