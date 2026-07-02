package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.data.Header;
import io.github.up2jakarta.lov.CodeList;

import java.util.Arrays;

import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FAST} mode .
 *
 * @param <T> the input segment type
 */
public class FastRecord<T extends CodeList<T>> extends UnitRecord<T> implements IFastRecord<T> {

    @Header(code = "PID", name = "Pivot")
    protected final String pivot;

    public FastRecord(T type, String pivot, String... data) {
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
