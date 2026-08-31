package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.NeatImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.hdl.PropertyCollector.Builder;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link NeatImporter} that's able to parse and aggregate java-bean from {@link NeatRecord}.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see SimpleNeatExporter
 * @see PropertyEvent
 * @see MessRecord
 */
public final class SimpleNeatImporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>>
        extends NeatImporter<B, I, T, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>>
        implements RecordTransformer<NeatRecord<I>> {

    /**
     * Constructor for simple {@link io.github.up2jakarta.csv.core.ModeType#NEAT} importer.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public SimpleNeatImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    SimpleNeatImporter(SimpleNeatExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected Builder<B, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>> newBuilder(int size) {
        final IPropertyCreator<B, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>> creator = PropertyEvent::new;
        return new Builder<>(size, creator);
    }

    @Override
    public NeatRecord<I> transform(String... source) throws CodeListException {
        return this.transform(NeatRecord::new, source);
    }

    @Override
    public Up2Result<T, PropertyEvent<B, NeatRecord<I>>> transform(List<String[]> records) throws AccessException {
        final List<NeatRecord<I>> result = new ArrayList<>(records.size());
        for (final String[] record : records) {
            if (record == null || record.length < mode.getOffset()) {
                continue;
            }
            result.add(this.transform(record));
        }
        return this.parse(result);
    }

    @Override
    public SimpleNeatExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleNeatExporter<>(this);
    }

}
