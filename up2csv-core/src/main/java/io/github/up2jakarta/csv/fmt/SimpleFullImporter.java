package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.ITraceCreator;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FullImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.ETraceCollector;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.xml.clv.CodeListException;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see FullRecord
 * @see ETrace
 */
public final class SimpleFullImporter<T extends Referencable, B extends DataType<B>, I extends IType<B, I>>
        extends FullImporter<B, I, T, FullRecord<I>, FullError<B, FullRecord<I>>>
        implements RecordTransformer<FullRecord<I>> {

    public <E extends Enum<E> & IType<B, I>> SimpleFullImporter(Up2Factory<B> mf, Class<T> type, E rootNode) throws BeanException {
        //noinspection unchecked
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants());
    }

    public SimpleFullImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(mf, type, rootNode, nodes);
    }

    public SimpleFullImporter(SimpleFullExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected ETraceCollector<B, FullRecord<I>, FullError<B, FullRecord<I>>> create(FullRecord<I> row) {
        final ITraceCreator<B, FullRecord<I>, FullError<B, FullRecord<I>>> creator = FullError::new;
        return new ETraceCollector<>(row, creator, (r) -> 0);
    }

    @Override
    public FullRecord<I> transform(String... source) throws CodeListException {
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
