package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;

/**
 * {@link ModeType#FULL} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 */
public final class SimpleFullExporter<T extends Segment, B extends DataType<B>, I extends IType<B, I>> extends FullExporter<B, I, T> {

    public SimpleFullExporter(Up2Factory<B> factory, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(factory, type, rootNode, nodes);
    }

    public SimpleFullExporter(FullImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple full-importer for the same configuration without scan bean annotations again.
     *
     * @return new simple full-importer
     * @throws BeanException if any property is not accessible for write or cannot create segment instances
     */
    public SimpleFullImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleFullImporter<>(this);
    }

}
