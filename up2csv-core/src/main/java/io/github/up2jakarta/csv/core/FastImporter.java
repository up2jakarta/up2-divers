package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.List;
import java.util.Objects;

import static io.github.up2jakarta.lov.ConstantProvider.values;

/**
 * Up2J {@link ModeType#FAST} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 */
public abstract non-sealed class FastImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IFastRecord<I, ?>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected FastImporter(Up2Factory<B> factory, Class<T> type, I root) throws BeanException {
        this(factory, type, root, values(root));
    }

    protected FastImporter(Up2Factory<B> factory, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, ModeType.FAST, type, root, nodes);
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
