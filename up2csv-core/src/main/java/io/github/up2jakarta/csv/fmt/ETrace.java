package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.hdl.ITraceEvent;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.IError;
import io.github.up2jakarta.xml.api.SeverityType;

import static io.github.up2jakarta.csv.fmt.ETrace.PKey;

/**
 * Simple implementation of input event based on exception traces, it's compatible with all modes.
 *
 * @param <D> the business data type
 * @param <R> the record type
 */
public class ETrace<D extends DataType<D>, R extends UnitRecord<?>> implements Segment, ITraceEvent<D, R, PKey<R>> {

    private final PKey<R> key;

    @Up2CodeList
    @Definition(code = "DID", value = "Data")
    private final D type;

    @Up2Number
    @Definition(code = "CID", value = "Offset")
    private final int offset;

    @Up2CodeList
    @Definition(code = "ERS", value = "Severity")
    private final SeverityType severity;

    @Definition(code = "ERC", value = "Code")
    private final String code;

    @Definition(code = "ERM", value = "Message")
    private final String message;

    @Definition(code = "ERT", value = "Stack")
    private final String trace;

    public ETrace(R row, int order, D type, int offset, IError cause, String trace) {
        this.key = new PKey<>(row, order);
        this.severity = cause.getSeverity();
        this.message = cause.getMessage();
        this.code = cause.getCode();
        this.offset = offset;
        this.trace = trace;
        this.type = type;
    }

    @Override
    public final PKey<R> getKey() {
        return key;
    }

    @Override
    public final D getType() {
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
    public final String getTrace() {
        return trace;
    }

    public static class PKey<R extends UnitRecord<?>> implements IKey<R>, Segment {

        private final R record;
        private final int order;

        private PKey(R record, int order) {
            this.record = record;
            this.order = order;
        }

        @Override
        public final R getRecord() {
            return record;
        }

        @Override
        public final int getOrder() {
            return order;
        }

    }

}
