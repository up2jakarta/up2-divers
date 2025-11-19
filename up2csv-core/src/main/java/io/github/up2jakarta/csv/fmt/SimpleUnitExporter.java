package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitExporter;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.List;

/**
 * {@link ModeType#UNIT} Processor that able to segregate and export java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @see SimpleUnitImporter
 */
public final class SimpleUnitExporter<T extends Segment, B extends DataType<B>, I extends IType<B, I>> extends UnitExporter<B, I, T> {

    public SimpleUnitExporter(Up2Factory<B> factory, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, type, root, nodes);
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
