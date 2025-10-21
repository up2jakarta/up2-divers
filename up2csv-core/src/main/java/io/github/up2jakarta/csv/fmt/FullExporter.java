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
import java.util.function.Supplier;

/**
 * {@link ModeType#FULL} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 */
public class FullExporter<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends BusinessExporter<B, I, T> {

    public FullExporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.FULL, type, rootNode, nodes);
    }

    public FullExporter(FullImporter<T, B, I, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple full-importer for the same configuration without scan bean annotations again.
     *
     * @return new simple full-importer
     * @throws BeanException for any problem setting business-object properties or creating segments
     */
    public final SimpleFullImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleFullImporter<>(this);
    }

    /**
     * Segregates the given business-object to many records and notifies the callback for each one.
     *
     * @param bean           the business object to segregate
     * @param rowIdGenerator the record-reference supplier
     * @param callback       the segment listener
     * @throws BeanException for any problem when getting fields from business-object
     * @throws IOException   for some reason cannot be opened for writing by the callback.
     * @see FullExporter#format(Referencable, Supplier, SegmentWriter)
     */
    public final void format(T bean, Supplier<String> rowIdGenerator, SegmentWriter callback) throws BeanException, IOException {
        super.format(bean, rowIdGenerator, callback);
    }

}
