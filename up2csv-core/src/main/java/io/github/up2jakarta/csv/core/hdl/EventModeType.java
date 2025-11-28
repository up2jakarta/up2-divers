package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.lov.IException;
import io.github.up2jakarta.lov.SeverityType;

/**
 * Base Event type, that's responsible to creating events.
 *
 * @param <E> the event type
 */
public abstract class EventModeType<E extends IException> {

    public final Class<? extends E> type;

    public EventModeType(Class<? extends E> type) {
        this.type = type;
    }

    /**
     * Creates and returns an event without message argument only.
     *
     * @param level   the event level
     * @param code    the event code
     * @param message the event message
     * @return non-nullable event
     */
    public abstract E of(SeverityType level, String code, String message);

    /**
     * Creates and returns an event within cause argument only.
     *
     * @param level the event level
     * @param code  the event code
     * @param cause the event cause
     * @return non-nullable event
     */
    public abstract E of(SeverityType level, String code, Throwable cause);

    /**
     * Creates and returns an event within both cause and message arguments.
     *
     * @param level   the event level
     * @param code    the event code
     * @param message the event message
     * @param cause   the event cause
     * @return non-nullable event
     */
    public abstract E of(SeverityType level, String code, String message, Throwable cause);

}
