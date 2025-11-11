package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Objects;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract non-sealed class FullImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IFullRecord<I, ?>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected FullImporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FULL, type, rootNode, nodes);
    }

    protected FullImporter(FullExporter<B, I, T> source) throws BeanException {
        super(source);
    }

    @Override
    final Object nullPivot(T bean, R record) {
        return bid.set(bean, record.getPivot());
    }

    @Override
    final boolean testPivot(R root, R record) {
        return Objects.equals(root.getPivot(), record.getPivot());
    }

    /**
     * Creates and returns a full-exporter for the same configuration without scan bean annotations again.
     *
     * @return new full-exporter
     * @throws BeanException if any property is not accessible for read
     */
    public FullExporter<B, I, T> toExporter() throws BeanException {
        return new FullExporter<>(this);
    }

}
