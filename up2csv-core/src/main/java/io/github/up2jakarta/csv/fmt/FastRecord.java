package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.lov.CodeList;

import java.util.Arrays;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FAST} mode .
 *
 * @param <T> the input segment type
 * @param <P> the input pivot type
 */
public class FastRecord<T extends CodeList<T>, P extends Comparable<P>> extends UnitRecord<T> implements IFastRecord<T, P> {

    @Definition(code = "PID", value = "Pivot")
    protected final P pivot;

    public FastRecord(T type, P pivot, String... data) {
        super(type, data);
        this.pivot = pivot;
    }

    @Override
    public final P getPivot() {
        return pivot;
    }

    @Override
    public String toString() {
        return "#" + pivot + "[" + type.getCode() + "] -> " + Arrays.toString(data);
    }

}
