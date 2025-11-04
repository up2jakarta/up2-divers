package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;

/**
 * {@link ModeType#UNIT} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleUnitImporter
 */
public final class SimpleUnitExporter<T extends Referencable, B extends DataType<B>, I extends IType<B, I>> extends UnitExporter<B, I, T> {

    public SimpleUnitExporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, type, rootNode, nodes);
    }

    public SimpleUnitExporter(UnitImporter<B, I, T, ?, ?> source) throws BeanException {
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

}
