package io.github.up2jakarta.csv.core;

import io.github.up2jakarta.csv.api.IError;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.data.*;

/**
 * Base business reader for multi-segments format, that's able to read business-objects from input stream.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class BusinessReader<B extends DataType<B>, I extends IFullType<B, I>, T extends Referencable, R extends IRecord<I>, E extends IError<B>> extends Up2Aggregator<R> {

    protected final int beanIdIndex;
    protected final int typeIdIndex;
    protected final BusinessImporter<B, I, T, ?, ?>.BusinessTyping typing;
    private final BusinessImporter<B, I, T, R, E> exporter;

    protected BusinessReader(BusinessImporter<B, I, T, R, E> exporter) {
        this.beanIdIndex = exporter.mode.beanIdIndex;
        this.typeIdIndex = exporter.mode.typeIdIndex;
        this.typing = exporter.typing;
        this.exporter = exporter;
    }

    /**
     * Parses, validates and aggregates the next business-object.
     *
     * @param creator the custom result creator
     * @return the custom result created by the given <code>creator</code> argument
     * @throws BeanException for any problem when setting fields from input record
     */
    @SuppressWarnings("unused")
    public final <C> C read(BusinessCreator<C, T, E> creator) throws BeanException {
        return exporter.parse(super.next(), creator);
    }

    /**
     * Parses, validates and aggregates the next business-object.
     *
     * @return the business-object with collected errors
     * @throws BeanException for any problem when setting fields from input record
     */
    public final Up2Result<T, E> read() throws BeanException {
        return exporter.parse(super.next());
    }

}
