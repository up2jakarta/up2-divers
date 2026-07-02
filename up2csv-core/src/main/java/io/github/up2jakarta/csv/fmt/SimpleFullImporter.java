package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IBusinessCreator;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.BusinessCollector.Builder;
import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the input segment type
 * @see FullRecord
 * @see BusinessEvent
 */
public final class SimpleFullImporter<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>>
        extends FullImporter<B, I, T, FullRecord<I>, FullError<B, FullRecord<I>>>
        implements RecordTransformer<FullRecord<I>> {

    public SimpleFullImporter(Up2Factory<B> factory, Class<T> st, Class<I> it) throws BeanException {
        super(factory, st, it);
    }

    public SimpleFullImporter(SimpleFullExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected Builder<B, FullRecord<I>, FullError<B, FullRecord<I>>> newBuilder(int size) {
        final IBusinessCreator<B, FullRecord<I>, FullError<B, FullRecord<I>>> creator = FullError::new;
        return new Builder<>(size, creator, r -> 0);
    }

    @Override
    public FullRecord<I> transform(String... source) throws CodeListException {
        final String rid = source[0];
        final String bid = source[2];
        return this.transform((t, d) -> new FullRecord<>(rid, t, bid, d), source);
    }

    @Override
    public Up2Result<T, FullError<B, FullRecord<I>>> parse(List<String[]> records) throws AccessException {
        final List<FullRecord<I>> result = new ArrayList<>(records.size());
        for (final String[] record : records) {
            if (record == null || record.length <= mode.getTypeIdIndex()) {
                continue;
            }
            result.add(this.transform(record));
        }
        return this.parse(result);
    }

    @Override
    public SimpleFullExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleFullExporter<>(this);
    }

}
