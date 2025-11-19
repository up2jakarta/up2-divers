package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.lov.SeverityType;

/**
 * Simple implementation of input event without neither cause nor trace.
 *
 * @param <D> the business data type
 */
public class SimpleEvent<D extends DataType<D>> extends Event<D> {

    @Definition(code = "ERC", value = "Code")
    private final String code;

    @Up2CodeList
    @Definition(code = "ERL", value = "Level")
    private final SeverityType level;

    @Definition(code = "ERM", value = "Message")
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
