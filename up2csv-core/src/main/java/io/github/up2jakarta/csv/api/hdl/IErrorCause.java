package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.MessageFormatter;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

/**
 * Contact interface for an input error with trace property, useful for error logging.
 *
 * @param <R> the input row type
 * @param <D> the input row type
 */
public interface IErrorCause<R extends IRecord<?>, D extends DataType<D>> extends MessageFormatter, IError<D> {

    /**
     * @return the related input record
     */
    R getRecord();

    /**
     * @return the cause exception
     */
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
