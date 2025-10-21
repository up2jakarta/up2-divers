package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.hdl.IEntityCreator;
import io.github.up2jakarta.csv.api.hdl.IErrorCause;
import io.github.up2jakarta.csv.api.hdl.IErrorEntity;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

public class FatalException extends FastException {

    private static final String ERROR = "%s) the data #[%s] has %s: %s - %s";

    private final List<? extends IError<?>> causes;

    public FatalException(DataType<?> type, int offset, SeverityType level, String code, PropertyException cause, List<? extends IError<?>> causes) {
        super(type, offset, level, code, cause);
        this.causes = List.copyOf(causes);
    }

    private void print(Consumer<String> println) {
        println.accept("Multiple events have been occurred:");
        var i = 0;
        for (final IError<?> event : causes) {
            final String type = (event.getSeverity() == SeverityType.WARNING) ? "warning" : "error";
            println.accept(String.format(ERROR, ++i, event.getOffset(), type, event.getCode(), event.getMessage()));
            if (event instanceof IErrorEntity<?, ?, ?> t && t.getTrace() != null) {
                println.accept(t.getTrace());
            } else if (event instanceof IErrorCause<?, ?> c) {
                IEntityCreator.trace(c.getCause()).ifPresent(println);
            }
        }
    }

    public final List<? extends IError<?>> getCauses() {
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
