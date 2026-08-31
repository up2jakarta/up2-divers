package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IFullRecord;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.MessExporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.core.BeanException;

/**
 * Simple {@link MessExporter} with an extra-data {@link IFullRecord#getReference()} at offset {@code 0}.
 * <p>
 * Note that this exporter does not fulfill the offset {@code 0} aka record-key,
 * it's the responsibility of {@link io.github.up2jakarta.csv.data.SegmentWriter}.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see MessExporter#format(Segment, io.github.up2jakarta.csv.data.SegmentWriter)
 * @see FullImporter
 */
public class FullExporter<B extends ITerm<B>, I extends Enum<I> & IType<I>, T extends Segment> extends MessExporter<B, I, T> {

    /**
     * Constructor for {@link io.github.up2jakarta.csv.core.IMode#FULL} exporter.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public FullExporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it, 1);
    }

    protected FullExporter(FullImporter<B, I, T, ?, ?> source) throws BeanException {
        super(source);
    }

    @Override
    protected final String spec(int index) {
        return "#Record";
    }

}
