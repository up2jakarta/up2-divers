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
 * {@link ModeType#UNIT} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleUnitImporter
 */
public final class UnitExporter<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends BusinessExporter<B, I, T> {

    public UnitExporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, ModeType.UNIT, type, rootNode, nodes);
    }

    public UnitExporter(UnitImporter<T, B, I, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple unit-importer for the same configuration without scan bean annotations again.
     *
     * @return new simple unit-importer
     * @throws BeanException for any problem setting business-object properties or creating segments
     */
    public SimpleUnitImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleUnitImporter<>(this);
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
