package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.hdl.ITraceEvent;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.IError;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.fmt.InputError.PKey;

/**
 * Simple implementation of input error based on traces,
 * basically it's compatible with {@link io.github.up2jakarta.csv.core.ModeType#FULL} mode.
 *
 * @param <D> the business data type
 * @param <R> the record type
 */
public class InputError<D extends DataType<D>, R extends InputRecord<?>> implements ITraceEvent<D, R, PKey<R>>, Segment {

    @Fragment(0)
    private final PKey<R> key;

    @Position(3)
    @Up2CodeList
    @Definition(code = "DID", value = "Data")
    private final D type;

    @Position(4)
    @Up2Number
    @Definition(code = "CID", value = "Offset")
    private final int offset;

    @Position(5)
    @Up2CodeList
    @Definition(code = "ERS", value = "Severity")
    private final SeverityType severity;

    @Position(6)
    @Definition(code = "ERC", value = "Code")
    private final String code;

    @Position(7)
    @Definition(code = "ERM", value = "Message")
    private final String message;

    @Position(8)
    @Definition(code = "ERT", value = "Stack")
    private final String trace;

    public InputError(R row, int order, D type, int offset, IError cause, String trace) {
        this.key = new PKey<>(row, order);
        this.severity = cause.getSeverity();
        this.message = cause.getMessage();
        this.code = cause.getCode();
        this.offset = offset;
        this.trace = trace;
        this.type = type;
    }

    @Override
    public PKey<R> getKey() {
        return key;
    }

    @Override
    public D getType() {
        return type;
    }

    @Override
    public final SeverityType getSeverity() {
        return severity;
    }

    @Override
    public final int getOffset() {
        return offset;
    }

    @Override
    public final String getCode() {
        return code;
    }

    @Override
    public final String getMessage() {
        return message;
    }

    @Override
    public String getTrace() {
        return trace;
    }

    public static class PKey<R extends InputRecord<?>> implements IKey<R>, Segment {

        @Fragment(0)
        private final R record;
        private final int order;

        private PKey(R record, int order) {
            this.record = record;
            this.order = order;
        }

        @Override
        public R getRecord() {
            return record;
        }

        @Override
        public int getOrder() {
            return order;
        }

    }

}
