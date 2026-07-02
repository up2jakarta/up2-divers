package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitExporter;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * {@link ModeType#UNIT} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see SimpleUnitImporter
 */
public final class SimpleUnitExporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends UnitExporter<B, I, T> {

    public SimpleUnitExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    public SimpleUnitExporter(UnitImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple unit-importer for the same configuration without scan bean annotations again.
     *
     * @return new simple unit-importer
     * @throws BeanException if any property is not accessible for write or cannot create segment instances
     */
    public SimpleUnitImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleUnitImporter<>(this);
    }

}
