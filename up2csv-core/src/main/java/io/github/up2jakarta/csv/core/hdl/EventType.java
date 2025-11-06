package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.xml.api.IException;
import io.github.up2jakarta.xml.api.SeverityType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Handler Event type, that's responsible to creating the events.
 *
 * @param <E> the event type
 */
public abstract sealed class EventType<E extends IException> permits ECMode, ETMode {

    public final Class<E> type;

    public EventType(Class<E> type) {
        this.type = type;
    }

    /**
     * Returns the stack trace of an error.
     *
     * @param error the source
     * @return the stack-trace if exists
     */
    public static Optional<String> trace(IException error) {
        while (error.getCause() instanceof IException cause) {
            error = cause;
        }
        return Optional.ofNullable(error.getCause()).map(c -> {
            final StringWriter writer = new StringWriter();
            ETMode.stackTrace(c, new PrintWriter(writer));
            return writer.toString().trim();
        });
    }

    /**
     * Creates and returns an event without cause from the specified arguments.
     *
     * @param level   the error severity
     * @param code    the error code
     * @param message the error message
     * @return non-nullable error
     */
    public abstract E of(SeverityType level, String code, String message);

    /**
     * Creates and returns an event within cause from the specified arguments.
     *
     * @param level the error severity
     * @param code  the error code
     * @param cause the error cause
     * @return non-nullable error
     */
    public abstract E of(SeverityType level, String code, Throwable cause);

}
