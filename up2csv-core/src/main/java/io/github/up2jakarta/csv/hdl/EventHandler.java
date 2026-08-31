package io.github.up2jakarta.csv.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.hdl.EventCode;
import io.github.up2jakarta.csv.api.hdl.EventLevel;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.IError;
import io.github.up2jakarta.lov.SeverityType;

import static io.github.up2jakarta.csv.api.IEvent.EC_CONVERTER;

/**
 * Input events handler that accepts both compliance and mapping events.
 *
 * @param <D> the business term type
 * @see io.github.up2jakarta.csv.core.Up2Mapper#map(IRecord, EventHandler)
 * @see io.github.up2jakarta.csv.core.Up2Mapper#map(EventHandler, String...)
 * @see io.github.up2jakarta.csv.core.Up2Mapper#map(IRecord, int, EventHandler)
 * @see io.github.up2jakarta.csv.core.Up2Mapper#map(EventHandler, int, String...)
 */
public abstract class EventHandler<D extends ITerm<D>> extends ComplianceHandler<D> {

    private static SeverityType level(Exception exception, Error config) {
        if (config != null) {
            return config.level();
        }
        if (exception instanceof IError ie) {
            return ie.getLevel();
        }
        return SeverityType.ERROR;
    }

    private static String code(Exception exception, Error config) {
        if (config != null) {
            return config.value();
        }
        if (exception instanceof IError ie) {
            return ie.getCode();
        }
        return EC_CONVERTER;
    }

    /**
     * Handles any exception caused by the input at the given offset.
     *
     * @param config the config annotation defined at property level
     * @param type   the business term
     * @param offset the input index in the related record
     * @param cause  the cause exception
     */
    public final void handle(Error config, D type, int offset, Exception cause) {
        this.handle(() -> level(cause, config), () -> code(cause, config), type, offset, cause);
    }

    /**
     * Handles any event caused by the input at the given offset.
     *
     * @param type   the business term
     * @param offset the input index in the related record
     * @param level  the event level supplier
     * @param code   the event code supplier
     * @param cause  the cause exception
     */
    public abstract void handle(EventLevel level, EventCode code, D type, int offset, Throwable cause);

}
