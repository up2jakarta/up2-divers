package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Objects;

/**
 * {@link ModeType#FAST} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract non-sealed class FastImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IFastRecord<I, ?>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected FastImporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FAST, type, rootNode, nodes);
    }

    protected FastImporter(FastExporter<B, I, T> source) throws BeanException {
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
     * Creates and returns a fast-exporter for the same configuration without scan bean annotations again.
     *
     * @return new fast-exporter
     * @throws BeanException if any property is not accessible for read.
     */
    public FastExporter<B, I, T> toExporter() throws BeanException {
        return new FastExporter<>(this);
    }

}
