package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.fmt.hdl.UnitCollector;

/**
 * Contact interface for an input error collected by each row, useful when each row have its related errors.
 *
 * @param <R> the input record type
 * @param <D> the input data type
 * @param <E> the self-record implementation
 * @see IUnitRecord
 * @see UnitCollector
 */
public interface IUnitError<D extends DataType<D>, R extends IUnitRecord<D, ?, E, R>, E extends IUnitError<D, R, E>> extends IEvent<D> {

}
