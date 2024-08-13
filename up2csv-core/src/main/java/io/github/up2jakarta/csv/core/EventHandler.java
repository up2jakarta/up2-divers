package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.annotation.Severity;
import io.github.up2jakarta.csv.exception.BeanException;
import io.github.up2jakarta.csv.misc.SeverityType;
import io.github.up2jakarta.csv.persistence.InputError;
import io.github.up2jakarta.csv.persistence.InputRepository;
import io.github.up2jakarta.csv.persistence.InputRow;
import jakarta.validation.ConstraintViolation;

import java.util.List;

/**
 * Internal handler that handles events during the mapping, validation and parsing phases.
 *
 * @param <R> the input row type
 * @param <K> the error key type
 * @param <E> the error type
 */
public class EventHandler<R extends InputRow, K extends InputError.Key<R>, E extends InputError<R, K>> {

    private final R row;
    private final LazyList<R, E> collector;
    private final EventCreator<R, K, E> creator;

    public EventHandler(R row, EventCreator<R, K, E> creator, InputRepository<R> repository) {
        this.row = row;
        this.creator = creator;
        this.collector = new LazyList<>(() -> repository.countErrorsBy(row));
    }

    /**
     * Compute and return the error severity.
     *
     * @param violation the JSR-303 constraint violation
     * @param severity  severity annotation on property cause the violation
     * @return the error severity
     */
    private static SeverityType getSeverity(ConstraintViolation<?> violation, Severity severity) {
        if (severity != null) {
            return severity.value();
        }
        return violation.getConstraintDescriptor().getPayload().stream()
                .filter(SeverityType.Level.class::isAssignableFrom)
                .map(SeverityType::of)
                .max(SeverityType::compare)
                .orElse(SeverityType.WARNING);
    }

    /**
     * Handle the JSR-303 constraint violation caused by the input at the given offset.
     *
     * @param offset           the input index
     * @param violation        the JSR-303 constraint violation
     * @param propertySeverity the severity defined at property level
     */
    final void handleEvent(int offset, ConstraintViolation<?> violation, Severity propertySeverity) {
        final SeverityType severity = getSeverity(violation, propertySeverity);
        final E error = creator.create(severity, row, offset, violation.getMessage());
        this.collector.addWithOrder(error);
    }

    /**
     * Check if the given row is exactly the same as current one.
     *
     * @param row the target row
     * @throws BeanException if the given row is not the same of this one.
     */
    final void check(R row) throws BeanException {
        if (this.row != row) {
            throw new BeanException(EventHandler.class, "row", "invalid argument");
        }
    }

    /**
     * @return the collected error
     */
    public final List<E> toList() {
        return collector.toList();
    }

}
