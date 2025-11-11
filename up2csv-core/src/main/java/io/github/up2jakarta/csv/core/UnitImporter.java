package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Objects;

/**
 * {@link ModeType#UNIT} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract non-sealed class UnitImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected UnitImporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.UNIT, type, rootNode, nodes);
    }

    protected UnitImporter(UnitExporter<B, I, T> source) throws BeanException {
        super(source);
    }

    @Override
    final Object nullPivot(T bean, R record) {
        if (record instanceof IFastRecord<?, ?> fr) {
            return bid.set(bean, fr.getPivot());
        }
        return null;
    }

    @Override
    final boolean testPivot(R root, R record) {
        if (root instanceof IFastRecord<?, ?> rt && record instanceof IFastRecord<?, ?> fr) {
            return Objects.equals(rt.getPivot(), fr.getPivot());
        }
        return true;
    }

    /**
     * Creates and returns a unit-exporter for the same configuration without scan bean annotations again.
     *
     * @return new unit-exporter
     * @throws BeanException if any property is not accessible for read
     */
    public UnitExporter<B, I, T> toExporter() throws BeanException {
        return new UnitExporter<>(this);
    }

}
