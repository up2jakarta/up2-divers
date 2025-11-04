package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.IException;
import io.github.up2jakarta.xml.api.MessageFormatter;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

/**
 * Contact interface for an input error with exception property instead of trace, useful for error logging.
 *
 * @param <R> the input record type
 * @param <D> the input data type
 * @see ICauseCreator
 * @see io.github.up2jakarta.csv.core.hdl.EventCollector#EXCEPTION_TYPE
 */
public interface ICauseEvent<R extends IRecord<?>, D extends DataType<D>> extends MessageFormatter, IException, IEvent<D> {

    /**
     * @return the related input record
     */
    R getRecord();

    /**
     * @return the cause exception
     */
    @Override
    PropertyException getCause();

    @Override
    default SeverityType getSeverity() {
        return this.getCause().getSeverity();
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
    default String getFormattedMessage() {
        return this.getCause().getFormattedMessage();
    }

}
