package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputRepository;
import io.github.up2jakarta.csv.input.InputRow;
import jakarta.validation.ConstraintViolation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

/**
 * Default implementation.
 */
public class DefaultHandler<R extends InputRow, K extends InputError.Key<R>, E extends InputError<R, K>> extends EventHandler<R, K, E> {

    private static final List<String> CLASS_NAMES = List.of(
            ConvertedProperty.class.getName(),
            PositionProperty.class.getName(),
            StringProperty.class.getName(),
            EventHandler.class.getName(),
            MapperFactory.FragmentProperty.class.getName(),
            MapperFactory.class.getName() + "$DefaultMapper",
            ProcessorWrapper.class.getName(),
            TechnicalChecker.class.getName(),
            CompositeChecker.class.getName()
    );

    private final R row;
    private final LazyList<R, E> collector;
    private final EventCreator<R, K, E> creator;

    public DefaultHandler(R row, EventCreator<R, K, E> creator, InputRepository<R> repository) {
        this.row = row;
        this.creator = creator;
        this.collector = new LazyList<>(() -> repository.max(row));
    }

    private static void stackTrace(Throwable error, PrintWriter printer) {
        printer.println(error);
        final StackTraceElement[] traces = error.getStackTrace();
        for (StackTraceElement element : traces) {
            if (!CLASS_NAMES.contains(element.getClassName())) {
                printer.println("\t" + element);
            }
        }
        final Throwable cause = error.getCause();
        if (cause != null) {
            printer.println(cause);
        }
    }

    @Override
    public void handleEvent(int offset, ConstraintViolation<?> violation, Error config) {
        if (!collector.contains(offset)) {
            final SeverityType type = EventHandler.getSeverity(violation, config);
            final String code = getErrorCode(violation, config);
            final E error = creator.create(type, row, offset, code, violation.getMessage());
            this.collector.addWithOrder(offset, error, false);
        }
    }

    @Override
    public void handleEvent(int offset, Throwable exception, Error config, boolean trace) {
        final String code = getErrorCode(exception, config);
        final SeverityType type = getSeverity(exception, config);
        final E error = creator.create(type, row, offset, code, exception.getMessage());
        if (trace || !(exception instanceof PropertyException)) {
            final StringWriter writer = new StringWriter();
            stackTrace(exception, new PrintWriter(writer));
            error.setTrace(writer.toString());
        }
        this.collector.addWithOrder(offset, error, true);
    }

    @Override
    R getSource() {
        return row;
    }

    @Override
    public List<E> toList() {
        return collector.toList();
    }

    @SuppressWarnings("unused")
    public void add(E error) {
        collector.addWithOrder(null, error, false);
    }

    @SuppressWarnings("unused")
    public void addTo(Collection<E> target) {
        collector.addTo(target);
    }

}
