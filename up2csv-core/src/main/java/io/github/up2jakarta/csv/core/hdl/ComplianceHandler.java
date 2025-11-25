package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.hdl.EventCode;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Input events handler that accepts compliance events only.
 *
 * @param <D> the business data type
 * @see io.github.up2jakarta.csv.core.Up2Flatter#validate(Segment, ComplianceHandler)
 * @see io.github.up2jakarta.csv.core.Up2Flatter#validate(Segment, int, ComplianceHandler)
 */
public abstract class ComplianceHandler<D extends DataType<D>> {

    private static Optional<Error> error(ConstraintViolation<?> violation) {
        final ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
        final Class<? extends Annotation> annotationType = descriptor.getAnnotation().annotationType();
        return descriptor.getPayload().stream()
                .filter(Error.Payload.class::isAssignableFrom)
                .map(c -> c.getAnnotation(Error.class))
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(e -> e.level().getAsInt()))
                .or(() -> ofNullable(annotationType.getAnnotation(Error.class)));
    }

    private static SeverityType level(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.level();
        }
        return error(violation).map(Error::level).orElse(SeverityType.ERROR);
    }

    private static String code(ConstraintViolation<?> violation, Error config) {
        if (config != null) {
            return config.value();
        }
        return error(violation).map(Error::value).orElse(IEvent.EC_COMPLIANCE);
    }

    /**
     * Handles the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param data      the business data type
     * @param offset    the input offset in the record
     * @param violation the JSR-303 constraint violation
     * @param config    the error annotation defined at property level
     */
    public final void handle(D data, Integer offset, ConstraintViolation<?> violation, Error config) {
        this.handle(() -> level(violation, config), () -> code(violation, config), data, offset, violation);
    }

    /**
     * Handles the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param level  the event level supplier
     * @param code   the event code supplier
     * @param data   the business data type
     * @param offset the input offset in the record
     * @param cause  the JSR-303 constraint violation
     */

    public abstract void handle(EventLevel level, EventCode code, D data, Integer offset, ConstraintViolation<?> cause);

}
