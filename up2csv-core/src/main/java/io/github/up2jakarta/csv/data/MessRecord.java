package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.api.IMessRecord;
import io.github.up2jakarta.lov.CodeList;

import java.util.Arrays;

import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Simple implementation of input record, basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#MESS} mode.
 *
 * @param <T> the input segment type
 */
public class MessRecord<T extends CodeList<T>> extends NeatRecord<T> implements IMessRecord<T> {

    @Up2Header(code = "PID", name = "Pivot")
    protected final String pivot;

    public MessRecord(T type, String pivot, String... data) {
        super(type, data);
        this.pivot = token(pivot);
    }

    @Override
    public final String getPivot() {
        return pivot;
    }

    @Override
    public String toString() {
        return "#" + pivot + "[" + type.getCode() + "] -> " + Arrays.toString(data);
    }

}
