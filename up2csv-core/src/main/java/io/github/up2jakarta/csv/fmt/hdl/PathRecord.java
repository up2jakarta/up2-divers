package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.hdl.IRecordEntity;
import io.github.up2jakarta.csv.cfg.Definition;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;

import static io.github.up2jakarta.csv.fmt.hdl.PathRecord.PKey;
import static io.github.up2jakarta.xml.adapters.KeyCoder.token;

/**
 * Simple implementation of record for {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 *
 * @param <T> the segment type
 */
public class PathRecord<T extends IFullType<?, T>> implements IRecordEntity<T, PathSource, PKey> {

    private final PKey key;

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

    protected PathRecord(PKey key, String reference, T type, String beanId, String[] data) {
        this.key = key;
        this.businessReference = token(beanId);
        this.reference = reference;
        this.data = data;
        this.type = type;
    }

    public PathRecord(PathSource source, long lineId, String reference, T type, String beanId, String[] data) {
        this(new PKey(source, lineId), reference, type, beanId, data);
    }

    @Override
    public PKey getKey() {
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

    public static class PKey implements IKey<PathSource> {

        private final PathSource source;

        private final long order;

        private PKey(PathSource source, Long order) {
            this.source = source;
            this.order = order;
        }

        @Override
        public PathSource getSource() {
            return source;
        }

        @Override
        public Long getRecordNumber() {
            return order;
        }

    }

}
