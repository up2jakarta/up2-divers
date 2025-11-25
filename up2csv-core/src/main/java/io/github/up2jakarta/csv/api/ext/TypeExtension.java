package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.TypeAdapter;
import io.github.up2jakarta.lov.core.BeanAware;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Up2J configurable {@link io.github.up2jakarta.csv.cfg.Extension}
 * that converts the input data before setting the destination property.
 *
 * @param <A> the annotation type that activates the extension (on class)
 * @param <C> the annotation configuration type
 */
public abstract class TypeExtension<A extends Annotation, C extends Annotation> extends BeanAware {

    private final Class<A> activation;

    /**
     * Constructor with activation annotation on class.
     *
     * @param activation the annotation type
     */
    protected TypeExtension(Class<A> activation) {
        this.activation = activation;
    }

    /**
     * Returns <code>true</code> if the given segment type is annotated with activation.
     *
     * @param type the segment type that is being checked
     * @return <code>true</code>the extension is activated
     */
    public final boolean isActivated(Class<? extends Segment> type) {
        return type.getAnnotation(activation) != null;
    }

    /**
     * Returns the optional configuration annotation, if the given field is annotated with.
     *
     * @param type      the segment type declaring the property
     * @param last      the property that is being converted automatically
     * @param fieldType the type of the property
     * @param paths     the path of the property from the root segment
     * @return the configuration annotation if found
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<C> get(Class<? extends Segment> type, Field last, Class<?> fieldType, Field... paths) throws BeanException;

    /**
     * Configures and returns the {@link TypeAdapter} related to the specified type.
     *
     * @param property the property that is being converted automatically
     * @param type     the type of the property
     * @param config   the annotation configuration
     * @param <V>      the property type
     * @return the right conversion
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract <V> TypeAdapter<V> resolve(Field property, Class<V> type, C config) throws BeanException;

}
