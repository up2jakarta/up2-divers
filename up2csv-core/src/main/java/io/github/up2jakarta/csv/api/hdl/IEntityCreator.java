package io.github.up2jakarta.csv.api.hdl;

import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static io.github.up2jakarta.csv.core.EventHandler.stackTrace;

/**
 * Input Event {@link IErrorEntity} that is responsible for create the final Event to be collected during the mapping/parsing,
 * useful for error persistence.
 *
 * @param <R> the record type
 * @param <D> the data type
 * @param <E> the error type
 */
public interface IEntityCreator<R extends IRecordEntity<?, ?, ?>, D extends DataType<D>, E extends IErrorEntity<R, ?, D>> {

    static Optional<String> trace(PropertyException cause) {
        return Optional.ofNullable(cause.getCause()).map(c -> {
            final StringWriter writer = new StringWriter();
            stackTrace(Up2Mapper.class, c, new PrintWriter(writer));
            return writer.toString().trim();
        });
    }

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param row   the input row source
     * @param order the error order
     * @param type  the input data type
     * @param index the input data index
     * @param level the error severity
     * @param code  the error code
     * @param msg   the error message
     * @param trace the stack trace of the cause exception
     * @return the full-filled input error
     */
    E create(R row, int order, D type, int index, SeverityType level, String code, String msg, Optional<String> trace);

}
