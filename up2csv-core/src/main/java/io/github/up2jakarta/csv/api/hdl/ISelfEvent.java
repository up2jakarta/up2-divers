package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.data.DataType;

/**
 * Contact interface for an input error collected by each row, useful when each row have its related errors.
 *
 * @param <R> the input record type
 * @param <D> the input data type
 * @param <E> the self-record implementation
 * @see ISelfRecord
 * @see io.github.up2jakarta.csv.core.hdl.SelfPropertyCollector
 * @see io.github.up2jakarta.csv.core.hdl.SelfBusinessCollector
 */
public interface ISelfEvent<D extends DataType<D>, R extends ISelfRecord<D, ?, E, R>, E extends ISelfEvent<D, R, E>> extends IEvent<D> {

}
