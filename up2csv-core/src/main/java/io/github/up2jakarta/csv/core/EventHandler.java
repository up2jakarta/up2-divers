package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.exception.MapperException;
import io.github.up2jakarta.csv.exception.PropertyException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputSegment;
import io.github.up2jakarta.csv.misc.Listable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;

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
 * @param <D> the business data type
 * @param <C> the error type
 */
public abstract class EventHandler<A extends InputSegment<?>, B extends InputError.Key<A>, D extends DataType<D>, C extends InputError<A, B, ?>> implements Listable<C> {

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

    protected static SeverityType getSeverity(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.severity();
        }
        return getError(violation).map(Error::severity).orElse(SeverityType.ERROR);
    }

    protected static String getErrorCode(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.value();
        }
        return getError(violation).map(Error::value).orElse(ERROR_VALIDATOR);
    }

    protected static SeverityType getSeverity(Throwable exception, Error config) {
        if (config != null) {
            return config.severity();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getSeverityType();
        }
        return SeverityType.ERROR;
    }

    protected static String getErrorCode(Throwable exception, Error config) {
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
     * @param <R> the input record-segment
     * @param <D> the business data-type
     * @param <V> the error type
     * @return an instance that fails at the first throw error.
     */
    public static <R extends InputSegment<?>, D extends DataType<D>, V extends InputError<R, ?, D>> EventHandler<R, ?, D, V> failFast() {
        //noinspection unchecked
        return (EventHandler<R, ?, D, V>) FastHandler.INSTANCE;
    }

    /**
     * @return the source input row
     */
    abstract A getSource();

    /**
     * Handle the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param type      the data type
     * @param offset    the input index
     * @param violation the JSR-303 constraint violation
     * @param config    the error annotation defined at property level
     */
    public abstract void handleEvent(D type, int offset, ConstraintViolation<?> violation, Error config);

    /**
     * Handle any exception caused by the input at the given offset.
     *
     * @param type      the data type
     * @param offset    the input index
     * @param exception thr thrown exception
     * @param config    the error annotation defined at property level
     * @param trace     forces the stack trace
     */
    public abstract void handleEvent(D type, int offset, Throwable exception, Error config, boolean trace);

    /**
     * Fail-fast implementation.
     */
    private static class FastHandler<D extends DataType<D>> extends EventHandler<InputSegment<?>, InputError.Key<InputSegment<?>>, D, InputError<InputSegment<?>, InputError.Key<InputSegment<?>>, ?>> {

        private static final EventHandler<?, ?, ?, ?> INSTANCE = new FastHandler<>();

        private FastHandler() {
        }

        @Override
        InputSegment<?> getSource() {
            return null;
        }

        @Override
        public List<InputError<InputSegment<?>, InputError.Key<InputSegment<?>>, ?>> toList() {
            return emptyList();
        }

        @Override
        public void handleEvent(D data, int offset, ConstraintViolation<?> violation, Error config) {
            final SeverityType type = getSeverity(violation, config);
            final String code = getErrorCode(violation, config);
            throw new MapperException(data, offset, type, code, new PropertyException(type, code, violation.getMessage()));
        }

        @Override
        public void handleEvent(D data, int offset, Throwable exception, Error config, boolean trace) {
            final SeverityType type = getSeverity(exception, config);
            final String code = getErrorCode(exception, config);
            throw new MapperException(data, offset, type, code, PropertyException.of(type, code, exception));
        }

    }

}
