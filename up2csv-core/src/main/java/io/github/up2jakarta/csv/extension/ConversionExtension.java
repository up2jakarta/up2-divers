package io.github.up2jakarta.csv.extension;

import io.github.up2jakarta.csv.exception.BeanException;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Up2 configurable {@link io.github.up2jakarta.csv.annotation.Extension}
 * that converts the input data before setting the destination property.
 *
 * @param <A> the annotation type that activates the extension (on class)
 * @param <C> the annotation configuration type
 */
public abstract class ConversionExtension<A extends Annotation, C extends Annotation> {

    private final Class<A> activation;

    /**
     * Constructor with activation annotation on class.
     *
     * @param activation the annotation type
     */
    protected ConversionExtension(Class<A> activation) {
        this.activation = activation;
    }

    /**
     * Returns <code>true</code> if the given segment type is annotated with activation.
     *
     * @param segmentType the segment type that is being checked
     * @return <code>true</code>the extension is activated
     */
    public final boolean isActivated(Class<? extends Segment> segmentType) {
        return segmentType.getAnnotation(activation) != null;
    }

    /**
     * Returns the optional configuration annotation, if the given field is annotated with.
     *
     * @param segmentType the segment type
     * @param property    the property that is being converted automatically
     * @return the configuration annotation if found
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<C> get(Class<? extends Segment> segmentType, Field property) throws BeanException;

    /**
     * Configures and returns the Conversion {@link java.util.function.Function}.
     *
     * @param property the property that is being converted automatically
     * @param config   the annotation configuration
     * @return the right conversion
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Conversion<?> resolve(@NotNull Field property, @NotNull C config) throws BeanException;

}
