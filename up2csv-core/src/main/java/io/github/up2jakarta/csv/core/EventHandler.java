package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.exception.MapperException;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.misc.Listable;
import io.github.up2jakarta.csv.misc.SimpleKeyCreator;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.persistence.InputRow;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.up2jakarta.csv.misc.Errors.ERROR_CONVERTER;
import static io.github.up2jakarta.csv.misc.Errors.ERROR_VALIDATOR;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

/**
 * Internal handler that handles events during the mapping, validation and parsing phases.
 *
 * @param <A> the input row type
 * @param <B> the error key type
 * @param <C> the error type
 */
public abstract class EventHandler<A extends InputRow, B extends InputError.Key<A>, C extends InputError<A, B>> implements Listable<C> {

    private static Optional<Error> getError(ConstraintViolation<?> violation) {
        final ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        final Class<? extends Annotation> annotationType = descriptor.getAnnotation().annotationType();
        return descriptor.getPayload().stream()
                .filter(Error.Payload.class::isAssignableFrom)
                .map(c -> c.getAnnotation(Error.class))
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(e -> e.severity().getLevel()))
                .or(() -> ofNullable(annotationType.getAnnotation(Error.class)));
    }

    private static SeverityType getSeverity(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.severity();
        }
        return getError(violation).map(Error::severity).orElse(SeverityType.ERROR);
    }

    private static String getErrorCode(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.value();
        }
        return getError(violation).map(Error::value).orElse(ERROR_VALIDATOR);
    }

    private static SeverityType getSeverity(Throwable exception, Error config) {
        if (config != null) {
            return config.severity();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getSeverityType();
        }
        return SeverityType.ERROR;
    }

    private static String getErrorCode(Throwable exception, Error config) {
        if (exception instanceof CodeListException clException) {
            return clException.getErrorCode();
        }
        if (config != null) {
            return config.value();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getErrorCode();
        }
        return ERROR_CONVERTER;
    }

    /**
     * @return an instance that fails at the first throw error.
     */
    public static EventHandler<?, ?, ?> failFast() {
        return FastHandler.INSTANCE;
    }

    /**
     * Creates and returns an error handler that handles that support {@link InputError} with composite key.
     *
     * @param row        the input data that is being parsed
     * @param creator    the implementation of error creator
     * @param repository the implementation of input repository
     * @param <R>        the input row type
     * @param <K>        the error key type
     * @param <E>        the error type
     * @return pre-configured event handler
     * @see io.github.up2jakarta.csv.misc.CompositeKeyCreator
     */
    public static <R extends InputRow, K extends InputError.Key<R>, E extends InputError<R, K>> EventHandler<R, K, E> of(R row, EventCreator<R, K, E> creator, InputRepository<R> repository) {
        return new DefaultHandler<>(row, creator, repository);
    }

    /**
     * Creates and returns an error handler that handles that support {@link InputError} with simple key.
     *
     * @param row        the input data that is being parsed
     * @param creator    the implementation of error creator
     * @param repository the implementation of input repository
     * @param <R>        the input row type
     * @param <E>        the error type with simple key
     * @return pre-configured event handler
     */
    public static <R extends InputRow, E extends InputError.Key<R> & InputError<R, E>> SimpleHandler<R, E> of(R row, SimpleKeyCreator<R, E> creator, InputRepository<R> repository) {
        return new SimpleHandler<>(row, creator, repository);
    }

    /**
     * @return the source input row
     */
    abstract A getSource();

    /**
     * Handle the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param offset    the input index
     * @param violation the JSR-303 constraint violation
     * @param config    the error annotation defined at property level
     */
    abstract void handleEvent(int offset, ConstraintViolation<?> violation, Error config);

    /**
     * Handle any exception caused by the input at the given offset.
     *
     * @param offset    the input index
     * @param exception thr thrown exception
     * @param config    the error annotation defined at property level
     * @param trace     forces the stack trace
     */
    abstract void handleEvent(int offset, Throwable exception, Error config, boolean trace);

    /**
     * Fail-fast implementation.
     */
    private static class FastHandler extends EventHandler<InputRow, InputError.Key<InputRow>, InputError<InputRow, InputError.Key<InputRow>>> {

        private static final EventHandler<?, ?, ?> INSTANCE = new FastHandler();

        private FastHandler() {
        }

        @Override
        InputRow getSource() {
            return null;
        }

        @Override
        public List<InputError<InputRow, InputError.Key<InputRow>>> toList() {
            return emptyList();
        }

        @Override
        void handleEvent(int offset, ConstraintViolation<?> violation, Error config) {
            final SeverityType type = getSeverity(violation, config);
            final String code = getErrorCode(violation, config);
            throw new MapperException(offset, type, code, new PropertyException(type, code, violation.getMessage()));
        }

        @Override
        void handleEvent(int offset, Throwable exception, Error config, boolean trace) {
            final SeverityType type = getSeverity(exception, config);
            final String code = getErrorCode(exception, config);
            throw new MapperException(offset, type, code, PropertyException.of(type, code, exception));
        }

    }

    /**
     * Default internal implementation.
     */
    private static class DefaultHandler<R extends InputRow, K extends InputError.Key<R>, E extends InputError<R, K>> extends EventHandler<R, K, E> {

        private static final List<String> CLASS_NAMES = List.of(
                ConvertedProperty.class.getName(),
                EventHandler.class.getName(),
                MapperFactory.FragmentProperty.class.getName(),
                MapperFactory.class.getName() + "$DefaultMapper",
                PositionProperty.class.getName(),
                StringProperty.class.getName(),
                ProcessorWrapper.class.getName()
        );

        private final R row;
        private final LazyList<R, E> collector;
        private final EventCreator<R, K, E> creator;

        private DefaultHandler(R row, EventCreator<R, K, E> creator, InputRepository<R> repository) {
            this.row = row;
            this.creator = creator;
            this.collector = new LazyList<>(() -> repository.countErrorsBy(row));
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
        void handleEvent(int offset, ConstraintViolation<?> violation, Error config) {
            final SeverityType type = getSeverity(violation, config);
            final String code = getErrorCode(violation, config);
            final E error = creator.create(type, row, offset, code, violation.getMessage());
            this.collector.addWithOrder(error);
        }

        @Override
        void handleEvent(int offset, Throwable exception, Error config, boolean trace) {
            final String code = getErrorCode(exception, config);
            final SeverityType type = getSeverity(exception, config);
            final E error = creator.create(type, row, offset, code, exception.getMessage());
            if (trace || !(exception instanceof PropertyException)) {
                final StringWriter writer = new StringWriter();
                stackTrace(exception, new PrintWriter(writer));
                error.setTrace(writer.toString());
            }
            this.collector.addWithOrder(error);
        }

        @Override
        R getSource() {
            return row;
        }

        @Override
        public List<E> toList() {
            return collector.toList();
        }

    }

    /**
     * Simple implementation handler that uses {@link SimpleKeyCreator}.
     */
    public static class SimpleHandler<R extends InputRow, E extends InputError.Key<R> & InputError<R, E>> extends DefaultHandler<R, E, E> {
        private SimpleHandler(R row, SimpleKeyCreator<R, E> creator, InputRepository<R> repository) {
            super(row, creator, repository);
        }
    }

}
