package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IBusinessCreator;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.BusinessCollector.Builder;
import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @see FullRecord
 * @see BusinessEvent
 */
public final class SimpleFullImporter<T extends Segment, B extends DataType<B>, I extends IType<B, I>>
        extends FullImporter<B, I, T, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>>
        implements RecordTransformer<FullRecord<I, String>> {

    public SimpleFullImporter(Up2Factory<B> factory, Class<T> type, I root) throws BeanException {
        super(factory, type, root);
    }

    public SimpleFullImporter(Up2Factory<B> factory, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, type, root, nodes);
    }

    public SimpleFullImporter(SimpleFullExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected Builder<B, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>> newBuilder(int size) {
        final IBusinessCreator<B, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>> creator = FullError::new;
        return new Builder<>(size, creator, (r) -> 0);
    }

    @Override
    public FullRecord<I, String> transform(String... source) throws CodeListException {
        final I type = typing.type(source);
        final String[] data = typing.truncate(type, source);
        final String recordId = source[0];
        final String businessKey = token(source[mode.getBeanIdIndex()]);
        return new FullRecord<>(recordId, type, businessKey, data);
    }

    @Override
    public Up2Result<T, FullError<B, String, FullRecord<I, String>>> parse(List<String[]> records) throws AccessException {
        final List<FullRecord<I, String>> result = new ArrayList<>(records.size());
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
