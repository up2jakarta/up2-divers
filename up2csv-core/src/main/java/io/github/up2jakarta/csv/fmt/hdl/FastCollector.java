package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IErrorCreator;
import io.github.up2jakarta.csv.core.Up2Collector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

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
public class FastCollector<R extends IRecord<?>, D extends DataType<D>, E extends IError<D>> extends Up2Collector<R, D, E> {

    private final Set<E> errors = new LinkedHashSet<>();
    private final IErrorCreator<R, D, E> creator;
    private final int failLevel;

    /**
     * Public constructor fo instance creation.
     *
     * @param row     the input segment
     * @param creator the error creator
     */
    public FastCollector(R row, IErrorCreator<R, D, E> creator) {
        this(row, creator, DEFAULT_LEVEL);
    }

    /**
     * Public constructor fo instance creation with fail-fast severity level.
     *
     * @param row     the input segment
     * @param creator the error creator
     * @param fatal   the severity level for failure
     */
    public FastCollector(R row, IErrorCreator<R, D, E> creator, SeverityType fatal) {
        super(row);
        this.creator = creator;
        this.failLevel = fatal.getLevel();
    }

    @Override
    protected void accept(D data, int offset, SeverityType type, String code, PropertyException cause) {
        if (type.getLevel() < failLevel) {
            errors.add(creator.create(row, offset, data, cause));
        } else {
            throw new FatalException(data, offset, type, code, cause, new ArrayList<>(errors));
        }
    }

    @Override
    public final Set<E> toCollection() {
        return errors;
    }

}
