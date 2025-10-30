package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Collectable;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.PropertyException;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Internal handler that handles events during the mapping, validation and parsing phases.
 *
 * @param <R> the input row type
 * @param <D> the business data type
 * @param <E> the error type
 * @see EventCollector for custom definition.
 * @see FastHandler  for fail-fast handler
 */
public abstract sealed class EventHandler<R extends IRecord<?>, D extends DataType<D>, E extends IEvent<D>> implements Collectable<E> permits EventCollector, FastHandler {

    public final R row;

    public EventHandler(R row) {
        this.row = row;
    }

    private static Optional<Error> error(ConstraintViolation<?> violation) {
        final ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        final Class<? extends Annotation> annotationType = descriptor.getAnnotation().annotationType();
        return descriptor.getPayload().stream()
                .filter(Error.Payload.class::isAssignableFrom)
                .map(c -> c.getAnnotation(Error.class))
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(e -> e.severity().getLevel()))
                .or(() -> ofNullable(annotationType.getAnnotation(Error.class)));
    }

    static SeverityType level(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.severity();
        }
        return error(violation).map(Error::severity).orElse(SeverityType.ERROR);
    }

    static String code(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.value();
        }
        return error(violation).map(Error::value).orElse(IEvent.ERROR_VALIDATOR);
    }

    static SeverityType level(Exception exception, Error config) {
        if (config != null) {
            return config.severity();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getSeverity();
        }
        return SeverityType.ERROR;
    }

    static String code(Exception exception, Error config) {
        if (exception instanceof CodeListException clException) {
            return clException.getCode();
        }
        if (config != null) {
            return config.value();
        }
        if (exception instanceof PropertyException pException) {
            return pException.getCode();
        }
        return IEvent.ERROR_CONVERTER;
    }

    /**
     * Handles any error caused by the input at the given offset.
     *
     * @param type    the data type
     * @param offset  the input index
     * @param level   the error severity
     * @param code    the error code
     * @param message the error message
     */
    public abstract void handle(D type, int offset, SeverityType level, String code, String message);

    /**
     * Handles any error caused by the input at the given offset.
     *
     * @param type   the data type
     * @param offset the input index
     * @param level  the error severity
     * @param code   the error code
     * @param cause  the cause exception
     */
    public abstract void handle(D type, int offset, SeverityType level, String code, Throwable cause);

    /**
     * Handles the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param type      the data type
     * @param offset    the input index
     * @param violation the JSR-303 constraint violation
     * @param config    the error annotation defined at property level
     */
    public abstract void handle(D type, int offset, ConstraintViolation<?> violation, Error config);

    /**
     * Handles any exception caused by the input at the given offset.
     *
     * @param type   the data type
     * @param offset the input index
     * @param cause  the cause exception
     * @param config the error annotation defined at property level
     */
    public abstract void handle(D type, int offset, Exception cause, Error config);

}
