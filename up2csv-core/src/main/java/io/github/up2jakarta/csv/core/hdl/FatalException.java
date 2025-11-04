package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.hdl.ICauseEvent;
import io.github.up2jakarta.csv.api.hdl.ITraceEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

/**
 * Exception implementation for fail-fast events handling within collecting.
 *
 * @see FastHandler
 */
public class FatalException extends FastException {

    private static final String ERROR = "%s) the data #[%s] has %s: %s - %s";

    private final List<? extends IEvent<?>> causes;

    FatalException(DataType<?> type, int offset, PropertyException cause, List<? extends IEvent<?>> causes) {
        super(type, offset, cause.getSeverity(), cause.getCode(), cause);
        this.causes = List.copyOf(causes);
    }

    private void print(Consumer<String> println) {
        println.accept("Multiple events have been occurred:");
        var i = 0;
        for (final IEvent<?> event : causes) {
            final String type = (event.getSeverity() == SeverityType.WARNING) ? "warning" : "error";
            println.accept(String.format(ERROR, ++i, event.getOffset(), type, event.getCode(), event.getMessage()));
            if (event instanceof ITraceEvent<?, ?, ?> t && t.getTrace() != null) {
                println.accept(t.getTrace());
            } else if (event instanceof ICauseEvent<?, ?> c) {
                EventType.trace(c.getCause()).ifPresent(println);
            }
        }
    }

    public final List<? extends IEvent<?>> getCauses() {
        return causes;
    }

    @Override
    public PropertyException getCause() {
        return (PropertyException) super.getCause();
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, offset, this.getCause().getFormattedMessage());
    }

    @Override
    public final void printStackTrace(PrintStream stream) {
        super.printStackTrace(stream);
        if (!causes.isEmpty()) {
            print(stream::println);
            stream.flush();
        }
    }

    @Override
    public final void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
        if (!causes.isEmpty()) {
            print(writer::println);
            writer.flush();
        }
    }

}
