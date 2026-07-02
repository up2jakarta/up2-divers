package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * Up2J {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 */
public abstract non-sealed class FullImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IFullRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected FullImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.FULL, factory, st, it);
    }

    protected FullImporter(FullExporter<B, I, T> source) throws BeanException {
        super(source);
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
