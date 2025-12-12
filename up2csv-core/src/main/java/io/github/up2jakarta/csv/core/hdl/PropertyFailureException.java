package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.api.hdl.IPropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Listable;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.lov.TypeException;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static io.github.up2jakarta.csv.core.Up2Factory.trace;

/**
 * Exception implementation for fail-fast events handling within collecting.
 *
 * @see PropertyCollector
 */
public final class PropertyFailureException extends FailureException implements Listable<IEvent<?>> {

    private static final String ERROR = "%s) #[%s] has %s #[%s] %s";

    private final List<IEvent<?>> events;

    PropertyFailureException(DataType<?> type, Integer offset, TypeException cause, List<? extends IEvent<?>> events) {
        super(type, offset, cause.getLevel(), cause.getCode(), cause);
        this.events = List.copyOf(events);
    }

    private void print(Consumer<String> println) {
        println.accept("Multiple events have been occurred:");
        var i = 0;
        for (final IEvent<?> event : events) {
            final String name = (event.getLevel() == SeverityType.WARNING) ? "warning" : "error";
            println.accept(String.format(ERROR, ++i, event.getOffset(), name, event.getCode(), event.getMessage()));
            if (event instanceof IBusinessEvent<?, ?, ?> t && t.getTrace() != null) {
                println.accept(t.getTrace());
            } else if (event instanceof IPropertyEvent<?, ?> c) {
                trace(c.getCause()).ifPresent(println);
            }
        }
    }

    @Override
    public List<IEvent<?>> toList() {
        return events;
    }

    @Override
    public TypeException getCause() {
        return (TypeException) super.getCause();
    }

    @Override
    public String getLocalizedMessage() {
        final String offset = Optional.of(this.offset).map(String::valueOf).orElse("?");
        return String.format(FORMAT, offset, this.getCause().getLocalizedMessage());
    }

    @Override
    public void printStackTrace(PrintStream stream) {
        super.printStackTrace(stream);
        if (!events.isEmpty()) {
            print(stream::println);
            stream.flush();
        }
    }

    @Override
    public void printStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
        if (!events.isEmpty()) {
            print(writer::println);
            writer.flush();
        }
    }

}
