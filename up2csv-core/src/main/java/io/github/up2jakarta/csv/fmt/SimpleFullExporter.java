package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.FullExporter;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * {@link ModeType#FULL} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 */
public final class SimpleFullExporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends FullExporter<B, I, T> {

    public SimpleFullExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
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
