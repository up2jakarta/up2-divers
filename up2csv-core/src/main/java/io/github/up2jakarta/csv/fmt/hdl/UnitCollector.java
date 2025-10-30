package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.hdl.ICreator;
import io.github.up2jakarta.csv.api.hdl.IUnitError;
import io.github.up2jakarta.csv.api.hdl.IUnitRecord;
import io.github.up2jakarta.csv.core.hdl.EventCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;

import java.util.List;

/**
 * Input Event creator that is responsible for create the final Event to be collected during the mapping/parsing.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public class UnitCollector<D extends DataType<D>, E extends IUnitError<D, R, E>, R extends IUnitRecord<D, ?, E, R>> extends EventCollector<R, D, E, PropertyException> {

    private final ICreator<R, D, E> creator;

    /**
     * Public constructor fo instance creation.
     *
     * @param row     the input segment
     * @param creator the error creator
     */
    public UnitCollector(R row, ICreator<R, D, E> creator) {
        super(row, EXCEPTION_TYPE);
        this.creator = creator;
    }

    @Override
    protected void accept(D data, int offset, PropertyException cause) {
        final E error = creator.create(row, offset, data, cause);
        row.getErrors().add(error);
    }

    @Override
    public final List<E> toCollection() {
        return row.getErrors();
    }

}
