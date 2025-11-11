package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.Definition;

import java.util.Arrays;

/**
 * Simple implementation of input record,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FAST} mode .
 *
 * @param <I> the input type definition
 * @param <P> the pivot type
 */
public class FastRecord<I extends IType<?, I>, P extends Comparable<P>> extends UnitRecord<I> implements IFastRecord<I, P> {

    @Definition(code = "PID", value = "Pivot")
    protected final P pivot;

    public FastRecord(I type, P pivot, String... data) {
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
