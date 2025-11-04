package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.data.Definition;

import static io.github.up2jakarta.xml.adapters.KeyCoder.token;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 *
 * @param <T> the segment type
 */
@PositionOverride(path = "pivot", value = @Position(1))
@PositionOverride(path = "type", value = @Position(2))
public class InputRecord<T extends IType<?, T>> extends MiniRecord<T> implements IFullRecord<T> {

    @Position(0)
    @Definition(code = "RID", value = "Record")
    protected final String reference;

    public InputRecord(String reference, T type, String pivot, String[] data) {
        super(type, pivot, data);
        this.reference = token(reference);
    }

    @Override
    public final String getReference() {
        return reference;
    }

}
