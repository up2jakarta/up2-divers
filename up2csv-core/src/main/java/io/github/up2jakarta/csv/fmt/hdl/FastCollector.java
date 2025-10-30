package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.ICreator;
import io.github.up2jakarta.csv.core.hdl.EventCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static io.github.up2jakarta.csv.BusinessBuilder.DEFAULT_LEVEL;

/**
 * Input Event creator that is responsible for create the final Event to be collected during the mapping/parsing.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public class FastCollector<R extends IRecord<?>, D extends DataType<D>, E extends IEvent<D>> extends EventCollector<R, D, E, PropertyException> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final ICreator<R, D, E> creator;
    private final int failLevel;

    /**
     * Public constructor fo instance creation.
     *
     * @param row     the input segment
     * @param creator the error creator
     */
    public FastCollector(R row, ICreator<R, D, E> creator) {
        this(row, creator, DEFAULT_LEVEL);
    }

    /**
     * Public constructor fo instance creation with fail-fast severity level.
     *
     * @param row     the input segment
     * @param creator the error creator
     * @param fatal   the severity level for failure
     */
    public FastCollector(R row, ICreator<R, D, E> creator, SeverityType fatal) {
        super(row, EXCEPTION_TYPE);
        this.creator = creator;
        this.failLevel = fatal.getLevel();
    }

    @Override
    protected void accept(D type, int offset, PropertyException cause) {
        if (cause.getSeverity().getLevel() < failLevel) {
            errors.add(creator.create(row, offset, type, cause));
        } else {
            throw new FatalException(type, offset, cause, new ArrayList<>(errors));
        }
    }

    @Override
    public final Set<E> toCollection() {
        return errors;
    }

}
