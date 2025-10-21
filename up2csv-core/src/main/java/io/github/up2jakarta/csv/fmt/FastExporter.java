package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.BusinessExporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.data.SegmentWriter;

import java.io.IOException;

/**
 * {@link ModeType#FAST} Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleFastImporter
 */
public final class FastExporter<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends BusinessExporter<B, I, T> {

    public FastExporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FAST, type, rootNode, nodes);
    }

    public FastExporter(FastImporter<T, B, I, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple fast-importer for the same configuration without scan bean annotations again.
     *
     * @return new simple fast-importer
     * @throws BeanException for any problem setting business-object properties or creating segments
     */
    public SimpleFastImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleFastImporter<>(this);
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean     the business object to segregate
     * @param callback the segment listener
     * @throws BeanException for any problem when getting fields from business-object
     * @throws IOException   for some reason cannot be opened for writing by the callback.
     */
    public void format(T bean, SegmentWriter callback) throws BeanException, IOException {
        this.format(bean, () -> null, callback);
    }

}
