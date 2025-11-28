package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;

/**
 * Contact interface for an input event with exception property instead of trace, useful for events logging.
 *
 * @param <R> the input record type
 * @param <D> the business data type
 * @see IPropertyCreator
 * @see io.github.up2jakarta.csv.core.hdl.PropertyCollector#MODE
 */
public interface IPropertyEvent<D extends DataType<D>, R extends IRecord<?>> extends IException, IEvent<D> {

    /**
     * @return the related input record
     */
    R getRecord();

    /**
     * @return the cause exception
     */
    @Override
    TypeException getCause();

    @Override
    default SeverityType getLevel() {
        return this.getCause().getLevel();
    }

    @Override
    default String getCode() {
        return this.getCause().getCode();
    }

    @Override
    default String getMessage() {
        return this.getCause().getMessage();
    }

    @Override
    default String getLocalizedMessage() {
        return this.getCause().getLocalizedMessage();
    }

}
