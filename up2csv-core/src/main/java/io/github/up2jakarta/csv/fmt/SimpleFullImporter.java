package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IBusinessCreator;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.BusinessCollector;
import io.github.up2jakarta.csv.core.hdl.BusinessEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.CodeListException;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see FullRecord
 * @see BusinessEvent
 */
public final class SimpleFullImporter<T extends Segment, B extends DataType<B>, I extends IType<B, I>>
        extends FullImporter<B, I, T, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>>
        implements RecordTransformer<FullRecord<I, String>> {

    @SuppressWarnings("unchecked")
    public <E extends Enum<E> & IType<B, I>> SimpleFullImporter(Up2Factory<B> mf, Class<T> type, E rootNode) throws BeanException {
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants());
    }

    public SimpleFullImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(mf, type, rootNode, nodes);
    }

    public SimpleFullImporter(SimpleFullExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected BusinessCollector<B, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>> create(FullRecord<I, String> row) {
        final IBusinessCreator<B, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>> creator = FullError::new;
        return new BusinessCollector<>(row, creator, (r) -> 0);
    }

    @Override
    public FullRecord<I, String> transform(String... source) throws CodeListException {
        final I type = typing.type(source);
        final String[] data = typing.truncate(type, source);
        final String recordId = source[0];
        final String businessKey = source[mode.getBeanIdIndex()];
        return new FullRecord<>(recordId, type, businessKey, data);
    }

    @Override
    public SimpleFullExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleFullExporter<>(this);
    }

}
