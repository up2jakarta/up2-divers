package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.data.*;
import io.github.up2jakarta.lov.core.AccessException;

/**
 * Base business reader for multi-segments format, that's able to read business-objects from input stream.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @param <R> the input record type
 * @param <E> the event type
 */
public abstract class BusinessReader<B extends DataType<B>, I extends IType<B, I>, T extends Segment, R extends IRecord<I>, E extends IEvent<B>> extends Up2Aggregator<R> {

    protected final I root;
    protected final ModeType mode;
    protected final BusinessImporter<B, I, T, ?, ?>.BusinessTyping typing;
    private final BusinessImporter<B, I, T, R, E> exporter;

    protected BusinessReader(BusinessImporter<B, I, T, R, E> exporter) {
        this.typing = exporter.typing;
        this.mode = exporter.mode;
        this.root = exporter.root;
        this.exporter = exporter;
    }

    /**
     * Parses, validates and aggregates the next business-object.
     *
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code> argument
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    @SuppressWarnings("unused")
    public final <C> C read(BusinessCreator<C, T, E> creator) throws AccessException {
        return exporter.parse(super.next(), creator);
    }

    /**
     * Parses, validates and aggregates the next business-object.
     *
     * @return the business-object with collected events
     * @throws AccessException for any problem when setting properties of java-beans from input record
     */
    public final Up2Result<T, E> read() throws AccessException {
        return exporter.parse(super.next());
    }

}
