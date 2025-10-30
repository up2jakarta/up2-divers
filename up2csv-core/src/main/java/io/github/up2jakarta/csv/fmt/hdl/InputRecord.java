package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.hdl.IFullRecord;
import io.github.up2jakarta.csv.cfg.Definition;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;

import static io.github.up2jakarta.csv.fmt.hdl.InputRecord.PKey;
import static io.github.up2jakarta.xml.adapters.KeyCoder.token;

/**
 * Simple implementation of record for {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 *
 * @param <T> the segment type
 */
public class InputRecord<T extends IFullType<?, T>, S extends Comparable<S>> implements IFullRecord<T, S, PKey<S>> {

    private final PKey<S> key;

    @Position(0)
    @Definition(code = "RID", value = "Record")
    private final String reference;

    @Position(1)
    @Up2CodeList
    @Definition(code = "TID", value = "Segment")
    private final T type;

    @Position(2)
    @Definition(code = "BID", value = "Object")
    private final String businessReference;

    private final String[] data;

    protected InputRecord(PKey<S> key, String reference, T type, String beanId, String[] data) {
        this.key = key;
        this.businessReference = token(beanId);
        this.reference = reference;
        this.data = data;
        this.type = type;
    }

    public InputRecord(S source, long lineId, String reference, T type, String beanId, String[] data) {
        this(new PKey<>(source, lineId), reference, type, beanId, data);
    }

    @Override
    public PKey<S> getKey() {
        return key;
    }

    @Override
    public String getBusinessReference() {
        return businessReference;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public T getType() {
        return type;
    }

    @Override
    public String[] getColumns() {
        return data;
    }

    public static class PKey<F extends Comparable<F>> implements IKey<F> {

        private final F source;

        private final long order;

        private PKey(F source, Long order) {
            this.source = source;
            this.order = order;
        }

        @Override
        public F getSource() {
            return source;
        }

        @Override
        public Long getRecordNumber() {
            return order;
        }

    }

}
