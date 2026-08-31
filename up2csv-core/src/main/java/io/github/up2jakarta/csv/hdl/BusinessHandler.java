package io.github.up2jakarta.csv.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.SeverityType;

/**
 * Input events handler that accepts all events, used for multi-segment format.
 *
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.api.hdl.IEventBuilder
 */
public abstract class BusinessHandler<D extends ITerm<D>> extends EventHandler<D> {

    /**
     * Handles any event caused by the input type, used for cardinality checking.
     *
     * @param config  the error configuration
     * @param term    the business term
     * @param message the event message
     */
    public abstract void handle(Error config, D term, String message);

    /**
     * Handles any event caused by the input type, used for linking problems.
     *
     * @param config  the error configuration
     * @param term    the business term
     * @param message the event message
     * @param cause   the cause exception
     */
    public abstract void handle(Error config, D term, String message, RuntimeException cause);

    /**
     * Handles any event caused by the input record, used for meta-data checking like {@link IRecord#getType()}.
     *
     * @param type    the business term
     * @param level   the event level
     * @param code    the event code
     * @param message the event message
     */
    public abstract void handle(SeverityType level, String code, D type, int offset, String message);

}
