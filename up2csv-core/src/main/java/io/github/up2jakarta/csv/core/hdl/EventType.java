package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.Up2Mapper;
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
            ETMode.stackTrace(Up2Mapper.class, c, new PrintWriter(writer));
            return writer.toString().trim();
        });
    }

    /**
     * Prints this throwable and its backtrace to the specified <code>printer</code>.
     * Notes that the stack elements will be truncated from the given <code>entry</code> point class
     *
     * @param error   the exception to print
     * @param entry   the entry point
     * @param printer the printer stream
     */
    public static void stackTrace(Class<?> entry, Throwable error, PrintWriter printer) {
        final String cn = entry.getName();
        printer.println(error);
        final StackTraceElement[] traces = error.getStackTrace();
        for (final StackTraceElement element : traces) {
            if (cn.equals(element.getClassName())) {
                break;
            } else if (!ETMode.EXCLUSIONS.contains(element.getClassName())) {
                printer.println("\t" + element);
            }
        }
        final Throwable cause = error.getCause();
        if (cause != null) {
            printer.println(cause);
        }
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
