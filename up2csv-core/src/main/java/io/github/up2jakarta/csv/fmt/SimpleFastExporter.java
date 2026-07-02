package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.FastExporter;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * {@link ModeType#FAST} Processor that's able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see SimpleFastImporter
 */
public final class SimpleFastExporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends FastExporter<B, I, T> {

    public SimpleFastExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    public SimpleFastExporter(FastImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple fast-importer for the same configuration without scan bean annotations again.
     *
     * @return new simple fast-importer
     * @throws BeanException if any property is not accessible for write or cannot create segment instances
     */
    public SimpleFastImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleFastImporter<>(this);
    }

}
