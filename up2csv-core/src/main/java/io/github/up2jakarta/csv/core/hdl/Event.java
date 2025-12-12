package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Segment;

/**
 * Base implementation of input event.
 *
 * @param <D> the business data type
 */
public abstract class Event<D extends DataType<D>> implements IEvent<D>, Segment {

    @Up2CodeList
    @Definition(code = "DID", value = "Data")
    private final D type;

    @Up2Number
    @Definition(code = "CID", value = "Offset")
    private final Integer offset;

    protected Event(D type, Integer offset) {
        this.type = type;
        this.offset = offset;
    }

    @Override
    public final D getType() {
        return type;
    }

    @Override
    public final Integer getOffset() {
        return offset;
    }

}
