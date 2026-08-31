package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.NeatExporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * Simple {@link NeatExporter} that's able to format and segregate java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see SimpleNeatImporter
 */
public final class SimpleNeatExporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends NeatExporter<B, I, T> {

    /**
     * Constructor for simple {@link io.github.up2jakarta.csv.core.ModeType#NEAT} exporter.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public SimpleNeatExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    SimpleNeatExporter(SimpleNeatImporter<T, B, I> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple neat-importer for the same configuration without scan beans again.
     *
     * @return new simple neat-importer
     * @throws BeanException if any property is not accessible for write or cannot create segment instances
     */
    public SimpleNeatImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleNeatImporter<>(this);
    }

}
