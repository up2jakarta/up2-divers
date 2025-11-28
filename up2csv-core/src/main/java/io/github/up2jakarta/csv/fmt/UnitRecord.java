package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.lov.CodeList;

import java.util.Arrays;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#UNIT} mode.
 *
 * @param <T> the input segment type
 */
public class UnitRecord<T extends CodeList<T>> implements IRecord<T> {

    @Up2CodeList
    @Definition(code = "TID", value = "Type")
    protected final T type;
    protected final String[] data;

    public UnitRecord(T type, String... data) {
        this.type = notNull(type, IRecord.class, "type");
        this.data = data;
    }

    @Override
    public final T getType() {
        return type;
    }

    @Override
    public final String[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "[" + type.getCode() + "] -> " + Arrays.toString(data);
    }

}
