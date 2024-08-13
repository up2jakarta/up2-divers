package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.annotation.Processor;
import io.github.up2jakarta.csv.exception.ProcessorException;

/**
 * Up2 configurable {@link Processor} that transforms the input data before setting the destination property.
 */
@FunctionalInterface
public interface ConfigurableTransformer {

    /**
     * Transform {@link String} to the proper value of the destination field.
     *
     * @param value the input data.
     * @param args  The processor arguments.
     * @return the transformed {@link String} for the destination field
     * @throws ProcessorException If any error during the transformation
     */
    String transform(String value, String... args) throws ProcessorException;

}
