package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.MessExporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * Simple {@link MessExporter} that's able to format and segregate java-bean to flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see SimpleMessImporter
 */
public final class SimpleMessExporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends MessExporter<B, I, T> {

    /**
     * Constructor for simple {@link io.github.up2jakarta.csv.core.ModeType#MESS} exporter.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public SimpleMessExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    SimpleMessExporter(SimpleMessImporter<T, B, I> source) throws BeanException {
        super(source);
    }

    /**
     * Creates and returns a simple mess-importer for the same configuration without scan beans again.
     *
     * @return new simple mess-importer
     * @throws BeanException if any property is not accessible for write or cannot create segment instances
     */
    public SimpleMessImporter<T, B, I> toImporter() throws BeanException {
        return new SimpleMessImporter<>(this);
    }

}
