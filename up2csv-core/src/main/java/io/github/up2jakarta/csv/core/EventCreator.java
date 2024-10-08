package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.input.InputError;
import io.github.up2jakarta.csv.input.InputRow;

/**
 * Input Event creator that is responsible for create the final Event to be collected during the mapping/parsing.
 *
 * @param <R> the row type
 * @param <K> the error key type
 * @param <E> the error type
 */
public abstract class EventCreator<R extends InputRow, K extends InputError.Key<R>, E extends InputError<R, K>> {

    protected EventCreator() {
    }

    protected abstract E newInstance();

    /**
     * Create and return the input error that is being full-filled from the given arguments.
     *
     * @param severity the error severity
     * @param row      the input row source
     * @param offset   the input index
     * @param code     the error code
     * @param message  the error message
     * @return the full-filled input error
     */
    protected E create(SeverityType severity, R row, int offset, String code, String message) {
        final E error = this.newInstance();
        error.getKey().setRow(row);
        error.setSeverity(severity);
        error.setOffset(offset);
        error.setCode(code);
        error.setMessage(message);
        return error;
    }

}
