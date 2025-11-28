package io.github.up2jakarta.csv.api.ext;

import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Up2J configurable {@link io.github.up2jakarta.csv.cfg.Extension}
 * that converts the input data before setting the destination property.
 *
 * @param <C> the annotation configuration type
 * @param <T> the property super-type
 */
public interface TypeExtension<T, C extends Annotation> extends TypeResolver<T, C> {

    /**
     * Returns the optional configuration annotation, if the given field is annotated with.
     *
     * @param st the segment type declaring the property
     * @param pf the property that is being converted automatically
     * @param pt the property type
     * @param ps the path of the property from the root segment
     * @return the configuration annotation if found
     * @throws BeanException for any missing or wrong bean configuration
     */
    Optional<C> resolve(Class<? extends Segment> st, Field pf, Class<?> pt, Field... ps) throws BeanException;

}
