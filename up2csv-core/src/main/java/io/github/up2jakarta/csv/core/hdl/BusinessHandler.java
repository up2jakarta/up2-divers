package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.SeverityType;

/**
 * Input events handler that accepts all events, used for multi-segment format.
 *
 * @param <D> the business data type
 * @see io.github.up2jakarta.csv.api.hdl.IEventBuilder
 */
public abstract class BusinessHandler<D extends DataType<D>> extends EventHandler<D> {

    /**
     * Handles any event caused by the input type, used for cardinality checking.
     *
     * @param type    the input segment type
     * @param message the event message
     */
    public abstract void handle(IType<D, ?> type, String message);

    /**
     * Handles any event caused by the input type, used for linking problems.
     *
     * @param type    the input segment type
     * @param message the event message
     * @param cause   the cause exception
     */
    public abstract void handle(IType<D, ?> type, String message, RuntimeException cause);

    /**
     * Handles any event caused by the input record, used for meta-data checking like {@link IRecord#getType()}.
     *
     * @param type    the business data type
     * @param level   the event level
     * @param code    the event code
     * @param message the event message
     */
    public abstract void handle(SeverityType level, String code, D type, int offset, String message);

}
