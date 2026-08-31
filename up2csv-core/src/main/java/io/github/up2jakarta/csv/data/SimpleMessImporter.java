package io.github.up2jakarta.csv.data;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.MessImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.hdl.PropertyCollector.Builder;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple {@link MessImporter} that's able to parse and aggregate java-bean from {@link MessRecord}.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see SimpleMessExporter
 * @see PropertyEvent
 * @see MessRecord
 */
public final class SimpleMessImporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>>
        extends MessImporter<B, I, T, MessRecord<I>, PropertyEvent<B, MessRecord<I>>>
        implements RecordTransformer<MessRecord<I>> {

    /**
     * Constructor for simple {@link io.github.up2jakarta.csv.core.ModeType#MESS} importer.
     *
     * @param factory the segment factory
     * @param st      the business object type
     * @param it      the business input type
     * @throws BeanException for any missing or wrong bean configuration
     */
    public SimpleMessImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    SimpleMessImporter(SimpleMessExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected Builder<B, MessRecord<I>, PropertyEvent<B, MessRecord<I>>> newBuilder(int size) {
        final IPropertyCreator<B, MessRecord<I>, PropertyEvent<B, MessRecord<I>>> creator = PropertyEvent::new;
        return new Builder<>(size, creator);
    }

    @Override
    public MessRecord<I> transform(String... source) throws CodeListException {
        final String bid = source[1];
        return this.transform((t, d) -> new MessRecord<>(t, bid, d), source);
    }

    @Override
    public Up2Result<T, PropertyEvent<B, MessRecord<I>>> transform(List<String[]> records) throws AccessException {
        final List<MessRecord<I>> result = new ArrayList<>(records.size());
        for (final String[] record : records) {
            if (record == null || record.length < mode.getOffset()) {
                continue;
            }
            result.add(this.transform(record));
        }
        return this.parse(result);
    }

    @Override
    public SimpleMessExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleMessExporter<>(this);
    }

}
