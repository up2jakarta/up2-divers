package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * Simple {@link MessImporter} with an extra-data {@link IFullRecord#getReference()} at offset {@code 0}.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 * @see FullExporter
 */
public abstract class FullImporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment, R extends IFullRecord<I>, E extends IEvent<B>> extends MessImporter<B, I, T, R, E> {

    /**
     * Constructor for {@link io.github.up2jakarta.csv.core.IMode#FULL} importer.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    protected FullImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it, 1);
    }

    protected FullImporter(FullExporter<B, I, T> source) throws BeanException {
        super(source);
    }

    @Override
    public FullExporter<B, I, T> toExporter() throws BeanException {
        return new FullExporter<>(this);
    }

}
