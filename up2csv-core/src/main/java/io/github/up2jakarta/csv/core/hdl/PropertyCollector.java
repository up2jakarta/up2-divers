package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

import java.util.LinkedList;
import java.util.List;

import static io.github.up2jakarta.csv.BusinessBuilder.MAX_LEVEL;
import static java.util.Objects.requireNonNull;

/**
 * Input events collector that is responsible for create the final Event to be collected during the mapping/parsing.
 * It's fails at the first error having severity equals or greater than {@link PropertyCollector#level}
 * <p>
 * It's similar to {@link io.github.up2jakarta.csv.core.hdl.FastHandler} but it collects the older events to be
 * attached to {@link PropertyFailureException#toList()} or returned after processing done.
 * <p>
 * this collector is the default mode for {@link io.github.up2jakarta.csv.BusinessBuilder} when working with
 * {@link IPropertyCreator}, it's compatible for all modes.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public non-sealed class PropertyCollector<R extends IRecord<?>, D extends DataType<D>, E extends IPropertyEvent<R, D>> extends BusinessHandler<R, D, E, PropertyException> {

    protected static final int UNDEFINED_LEVEL = MAX_LEVEL.getLevel() + 1;

    private final int level;
    private final IPropertyCreator<R, D, E> creator;

    /**
     * Public constructor fo instance creation.
     *
     * @param source  the input record
     * @param creator the error creator
     */
    public PropertyCollector(R source, IPropertyCreator<R, D, E> creator) {
        this(source, new LinkedList<>(), creator, UNDEFINED_LEVEL);
    }

    PropertyCollector(R row, List<E> collector, IPropertyCreator<R, D, E> creator, int level) {
        super(row, collector, PROPERTY_MODE);
        this.creator = requireNonNull(creator);
        this.level = level;
    }

    @Override
    protected final E create(D type, Integer offset, PropertyException cause) {
        if (cause.getSeverity().getLevel() < level) {
            return creator.create(source, offset, type, cause);
        } else {
            throw new PropertyFailureException(type, offset, cause, events);
        }
    }

}
