package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.List;
import java.util.Objects;

import static io.github.up2jakarta.lov.DefaultProvider.values;

/**
 * {@link ModeType#UNIT} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 */
public abstract non-sealed class UnitImporter<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected UnitImporter(Up2Factory<B> factory, Class<T> type, I root) throws BeanException {
        this(factory, type, root, values(root));
    }

    protected UnitImporter(Up2Factory<B> factory, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, ModeType.UNIT, type, root, nodes);
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
