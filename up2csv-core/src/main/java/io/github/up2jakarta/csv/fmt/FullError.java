package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.xml.api.IError;

/**
 * Simple implementation of {@link ETrace}, it's well configured as segment to be exportable.
 *
 * @param <D> the business data type
 * @param <R> the record type
 * @see SimpleFullImporter
 */
@FragmentOverride(path = {"key"}, value = @Fragment(0))
@FragmentOverride(path = {"key", "record"}, value = @Fragment(0))
@PositionOverride(path = {"key", "record", "key"}, value = @Position(0))
@PositionOverride(path = {"key", "record", "type"}, value = @Position(1))
@PositionOverride(path = {"key", "record", "pivot"}, value = @Position(2))
@PositionOverride(path = "type", value = @Position(3))
@PositionOverride(path = "offset", value = @Position(4))
@PositionOverride(path = "severity", value = @Position(5))
@PositionOverride(path = "code", value = @Position(6))
@PositionOverride(path = "message", value = @Position(7))
@PositionOverride(path = "trace", value = @Position(8))
public class FullError<D extends DataType<D>, R extends FullRecord<?>> extends ETrace<D, R> {

    public FullError(R row, int order, D type, int offset, IError cause, String trace) {
        super(row, order, type, offset, cause, trace);
    }

}
