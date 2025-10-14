package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.core.Errors;
import io.github.up2jakarta.csv.core.Mapper;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Input Event {@link IErrorEntity} that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public interface IEntityCreator<R extends IRecordEntity<?, ?, ?>, D extends DataType<D>, E extends IErrorEntity<R, ?, D>> extends IErrorCreator<R, D, E> {

    static Optional<String> trace(PropertyException cause) {
        return Optional.ofNullable(cause.getCause()).map(c -> {
            final StringWriter writer = new StringWriter();
            Errors.stackTrace(c, Mapper.class, new PrintWriter(writer));
            return writer.toString().trim();
        });
    }

    @Override
    default E create(R row, int offset, D type, PropertyException cause) {
        return create(row, offset, type, cause.getSeverity(), cause.getCode(), cause.getMessage(), trace(cause));
    }

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param row     the input row source
     * @param level   the error severity
     * @param offset  the input index
     * @param code    the error code
     * @param message the error message
     * @param trace   the stack trace of the cause exception
     * @return the full-filled input error
     */
    E create(R row, int offset, D type, SeverityType level, String code, String message, Optional<String> trace);


}
