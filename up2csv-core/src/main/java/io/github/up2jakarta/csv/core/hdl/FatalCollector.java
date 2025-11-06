package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.ICauseCreator;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static io.github.up2jakarta.csv.BusinessBuilder.DEFAULT_LEVEL;

/**
 * Input events collector that is responsible for create the final Event to be collected during the mapping/parsing.
 * It's fails at the first error having severity equals or greater than {@link FatalCollector#failLevel}
 * <p>
 * It's similar to {@link io.github.up2jakarta.csv.core.hdl.FastHandler} but it collects the older events to be
 * attached to {@link FatalException#getCauses()} or returned after processing done.
 * <p>
 * this collector is the default mode for {@link io.github.up2jakarta.csv.BusinessBuilder} when working with
 * {@link ICauseCreator}, it's compatible for all modes.
 *
 * @param <R> the row type
 * @param <D> the data type
 * @param <E> the error type
 */
public class FatalCollector<R extends IRecord<?>, D extends DataType<D>, E extends IEvent<D>> extends EventCollector<R, D, E, PropertyException> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final ICauseCreator<R, D, E> creator;
    private final int failLevel;

    /**
     * Public constructor fo instance creation.
     *
     * @param row     the input segment
     * @param creator the error creator
     */
    public FatalCollector(R row, ICauseCreator<R, D, E> creator) {
        this(row, creator, DEFAULT_LEVEL);
    }

    /**
     * Public constructor fo instance creation with fail-fast severity level.
     *
     * @param row     the input segment
     * @param creator the error creator
     * @param fatal   the severity level for failure
     */
    public FatalCollector(R row, ICauseCreator<R, D, E> creator, SeverityType fatal) {
        super(row, CAUSE_TYPE);
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
