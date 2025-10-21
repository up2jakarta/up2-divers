package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.PropertyException;
import jakarta.validation.ConstraintViolation;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Base implementation of {@link EventHandler} that collects events during the mapping, validation and parsing phases.
 */
public abstract class Up2Collector<R extends IRecord<?>, D extends DataType<D>, E extends IError<D>> extends EventHandler<R, D, E> {

    private final Set<Integer> offsets = new LinkedHashSet<>();

    protected Up2Collector(R row) {
        super(row);
    }

    @Override
    public final void handleEvent(D data, int offset, ConstraintViolation<?> violation, Error config) {
        if (!offsets.contains(offset)) { // avoid collecting violations on property having parsing errors
            final SeverityType type = errorSeverity(violation, config);
            final String code = errorCode(violation, config);
            final PropertyException cause = new PropertyException(type, code, violation.getMessage());
            this.accept(data, offset, type, code, cause);
        }
    }

    @Override
    public final void handleEvent(D data, int offset, Exception cause, Error config) {
        final String code = errorCode(cause, config);
        final SeverityType type = errorSeverity(cause, config);
        final PropertyException pex = PropertyException.of(type, code, cause);
        this.accept(data, offset, type, code, pex);
        offsets.add(offset);
    }

    /**
     * Handles property exception caused by the input at the given offset.
     *
     * @param data   the data type
     * @param offset the input index
     * @param type   the severity level
     * @param code   the error code
     * @param cause  the property exception
     */
    protected abstract void accept(D data, int offset, SeverityType type, String code, PropertyException cause);

}
