package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.Header;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.SeverityType;

/**
 * Simple implementation of input event without neither cause nor trace.
 *
 * @param <D> the business term type
 */
public class SimpleEvent<D extends ITerm<D>> extends Event<D> {

    @Header(code = "ERC", name = "Code")
    private final String code;

    @Up2CodeList
    @Header(code = "ERL", name = "Level")
    private final SeverityType level;

    @Header(code = "ERM", name = "Message")
    private final String message;

    public SimpleEvent(D type, Integer offset, SeverityType level, String code, String message) {
        super(type, offset);
        this.code = code;
        this.level = level;
        this.message = message;
    }

    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public final SeverityType getLevel() {
        return level;
    }

    @Override
    public final String getMessage() {
        return message;
    }
}
