package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.core.BSNode;
import io.github.up2jakarta.csv.core.Up2Mapper;
import io.github.up2jakarta.csv.fmt.hdl.FastCollector;
import io.github.up2jakarta.csv.fmt.hdl.FullCollector;
import io.github.up2jakarta.xml.api.IException;
import io.github.up2jakarta.xml.api.SeverityType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Handler Event type, that's responsible to creating the events.
 *
 * @param <E> the event type
 */
public abstract sealed class EventType<E extends IException> permits EventException, EventError {

    private static final List<String> EXCLUSIONS;

    static {
        EXCLUSIONS = Stream.of(
                BSNode.exclusions(),
                new String[]{
                        // Handlers
                        FastHandler.class.getName(),
                        EventHandler.class.getName(),
                        FastCollector.class.getName(),
                        FullCollector.class.getName(),
                        EventCollector.class.getName(),
                        // Properties
                        Property.class.getName(),
                        PProperty.class.getName(),
                        PFProperty.class.getName(),
                        PAccessor.class.getName(),
                        PProcessor.class.getName(),
                        PProperty.class.getName() + "$" + PProperty.POProperty.class.getSimpleName(),
                        PProperty.class.getName() + "$" + PProperty.PSProperty.class.getSimpleName(),
                        PFAccessor.class.getName() + "$" + PFAccessor.ROAccess.class.getSimpleName(),
                        PFAccessor.class.getName() + "$" + PFAccessor.WOAccess.class.getSimpleName(),
                        PPAccessor.class.getName() + "$" + PPAccessor.ROAccess.class.getSimpleName(),
                        PPAccessor.class.getName() + "$" + PPAccessor.WOAccess.class.getSimpleName(),
                        PPAccessor.class.getName() + "$" + PPAccessor.NOAccess.class.getSimpleName()
                }
        ).flatMap(Arrays::stream).toList();
    }

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
            stackTrace(Up2Mapper.class, c, new PrintWriter(writer));
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
            } else if (!EXCLUSIONS.contains(element.getClassName())) {
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
