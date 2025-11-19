package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.lov.IError;

/**
 * Simple implementation of {@link BusinessEvent}, it's well configured as segment to be exportable.
 * <p>
 * If the {@link R#pivot} is not of type <code>String</code>, the related {@link Position#converter()} must be
 * overridden when the segment is used as exportable segment.
 *
 * @param <D> the business data type
 * @param <P> the input pivot type
 * @param <R> the input record type
 * @see SimpleFullImporter
 */
@FragmentOverride(path = {"key"}, value = @Fragment(0))
@FragmentOverride(path = {"key", "record"}, value = @Fragment(0))
@PositionOverride(path = {"key", "record", "key"}, value = @Position(0))
@PositionOverride(path = {"key", "record", "type"}, value = @Position(1))
@PositionOverride(path = {"key", "record", "pivot"}, value = @Position(2))
@PositionOverride(path = "type", value = @Position(3))
@PositionOverride(path = "offset", value = @Position(4))
@PositionOverride(path = "level", value = @Position(5))
@PositionOverride(path = "code", value = @Position(6))
@PositionOverride(path = "message", value = @Position(7))
@PositionOverride(path = "trace", value = @Position(8))
public class FullError<D extends DataType<D>, P extends Comparable<P>, R extends FullRecord<?, P>> extends BusinessEvent<D, R> {

    public FullError(R source, int order, D type, Integer offset, IError cause, String trace) {
        super(source, order, type, offset, cause, trace);
    }

}
