package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.fmt.hdl.UnitCollector;

import java.util.List;

/**
 * Contact interface for an input record, useful when each row have its related errors.
 *
 * @param <I> the input type
 * @param <R> the self-record implementation
 * @param <D> the input data type
 * @param <E> the input error type
 * @see UnitCollector
 */
public interface IUnitRecord<D extends DataType<D>, I extends IType<D, I>, E extends IUnitError<D, R, E>, R extends IUnitRecord<D, I, E, R>> extends IRecord<I> {

    /**
     * @return the list of related errors
     */
    List<E> getErrors();

}
