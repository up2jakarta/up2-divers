package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFastRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * Up2J {@link ModeType#FAST} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 */
public abstract non-sealed class FastImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IFastRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected FastImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(ModeType.FAST, factory, st, it);
    }

    protected FastImporter(FastExporter<B, I, T> source) throws BeanException {
        super(source);
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
