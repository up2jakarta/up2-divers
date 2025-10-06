package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.data.Collectable;
import io.github.up2jakarta.csv.data.DataType;
import jakarta.validation.ConstraintViolation;

/**
 * Internal handler that handles events during the mapping, validation and parsing phases.
 *
 * @param <R> the input row type
 * @param <D> the business data type
 * @param <E> the error type
 * @see EventCollector for custom definition.
 * @see FastHandler  for fail-fast handler
 */
public abstract class EventHandler<R extends IRecord<?>, D extends DataType<D>, E extends IError<D>> implements Collectable<E> {

    protected final R row;

    EventHandler(R row) {
        this.row = row;
    }

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
     */
    public abstract void handleEvent(D type, int offset, Exception exception, Error config);

}
