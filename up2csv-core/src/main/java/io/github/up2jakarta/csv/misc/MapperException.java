package io.github.up2jakarta.csv.misc;

import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.codelist.PropertyException;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

public class MapperException extends PropertyException {

    private static final String FORMAT = "#[%s] throws %s";
    private static final String ERROR = "%s) the data #[%s] has %s: %s - %s";

    private final List<? extends InputError<?, ?, ?>> causes;
    private final DataType<?> dataType;
    private final int offset;

    public MapperException(DataType<?> type, int offset, SeverityType severity, String errorCode, PropertyException cause) {
        super(severity, errorCode, cause);
        this.offset = offset;
        this.dataType = type;
        this.causes = List.of();
    }

    public MapperException(DataType<?> type, int offset, SeverityType severity, String errorCode, PropertyException cause, List<? extends InputError<?, ?, ?>> causes) {
        super(severity, errorCode, cause);
        this.offset = offset;
        this.dataType = type;
        this.causes = causes;
    }

    private void print(Consumer<String> println) {
        println.accept("Multiple events have been occurred:");
        var i = 0;
        for (final InputError<?, ?, ?> event : causes) {
            final String type = (event.getSeverity() == SeverityType.WARNING) ? "warning" : "error";
            println.accept(String.format(ERROR, ++i, event.getOffset(), type, event.getCode(), event.getMessage()));
            if (event.getTrace() != null) {
                println.accept(event.getTrace());
            }
        }
    }

    public List<? extends InputError<?, ?, ?>> getCauses() {
        return causes;
    }

    public DataType<?> getDataType() {
        return dataType;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public PropertyException getCause() {
        return (PropertyException) super.getCause();
    }

    @Override
    public String getFormattedMessage() {
        return String.format(FORMAT, offset, getCause().getFormattedMessage());
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
