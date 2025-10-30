package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IEvent;
import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IRecord;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.BusinessImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @param <R> the record type
 * @param <E> the error type
 */
public abstract class FullImporter<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>, R extends IRecord<I>, E extends IEvent<B>> extends BusinessImporter<B, I, T, R, E> {

    protected FullImporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FULL, type, rootNode, nodes);
    }

    protected FullImporter(FullExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a full-exporter for the same configuration without scan bean annotations again.
     *
     * @return new full-exporter
     * @throws BeanException for any problem when getting business-object properties.
     */
    public final FullExporter<T, B, I> toExporter() throws BeanException {
        return new FullExporter<>(this);
    }

}
