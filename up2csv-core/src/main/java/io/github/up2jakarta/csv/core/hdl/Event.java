package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Header;
import io.github.up2jakarta.csv.data.ITerm;

/**
 * Base implementation of input event.
 *
 * @param <D> the business term type
 */
public abstract class Event<D extends ITerm<D>> implements IEvent<D>, Segment {

    @Up2CodeList
    @Header(code = "DID", name = "Data")
    private final D type;

    @Up2Number
    @Header(code = "CID", name = "Offset")
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
