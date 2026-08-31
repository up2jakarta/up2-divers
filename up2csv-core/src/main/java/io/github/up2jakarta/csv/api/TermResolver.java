package io.github.up2jakarta.csv.api;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * The business resolver of term types.
 *
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.api.IEvent#getType()
 */
public abstract class TermResolver<D extends ITerm<?>> {

    private final Class<D> type;

    protected TermResolver(Class<D> type) {
        this.type = type;
    }

    /**
     * @return the class of the business term
     */
    public final Class<D> getType() {
        return type;
    }

    /**
     * Returns the optional business term, if the given field is annotated with.
     *
     * @param stack the stack of segment-types, useful for override
     * @param path  the path of the property from the root segment, useful for override
     * @param field the property that is being scanned automatically
     * @return the optional business term
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<? extends D> get(List<Class<? extends Segment>> stack, Field[] path, Field field) throws BeanException;

    /**
     * Returns the optional business term, if the given field or type is annotated with.
     *
     * @param field the field annotated with {@link io.github.up2jakarta.csv.BusinessLink} or empty for root business-objects
     * @param type  the segment type
     * @return the optional business term
     * @throws BeanException for any missing or wrong bean configuration
     */
    public abstract Optional<? extends D> get(Optional<Field> field, Class<? extends Segment> type) throws BeanException;

    @Override
    public final int hashCode() {
        return type.hashCode();
    }

}
