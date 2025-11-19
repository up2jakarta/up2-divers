package io.github.up2jakarta.csv.core.hdl;

import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.hdl.IBusinessEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.IError;

import static io.github.up2jakarta.csv.core.hdl.BusinessEvent.PKey;

/**
 * Simple implementation of input event based on exception traces, it's compatible with all modes.
 *
 * @param <D> the business data type
 * @param <R> the input record type
 */
public class BusinessEvent<D extends DataType<D>, R extends IRecord<?>> extends SimpleEvent<D> implements IBusinessEvent<D, R, PKey<R>> {

    private final PKey<R> key;

    @Definition(code = "ERT", value = "Stack")
    private final String trace;

    public BusinessEvent(R source, int order, D type, Integer offset, IError cause, String trace) {
        super(type, offset, cause.getLevel(), cause.getCode(), cause.getMessage());
        this.key = new PKey<>(source, order);
        this.trace = trace;
    }

    @Override
    public final PKey<R> getKey() {
        return key;
    }

    @Override
    public final String getTrace() {
        return trace;
    }

    public static class PKey<R extends IRecord<?>> implements IKey<R>, Segment {
        private final R record;
        private final int order;

        private PKey(R source, int order) {
            this.record = source;
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
