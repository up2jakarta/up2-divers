package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.FastImporter;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListException;

import java.util.ArrayList;
import java.util.List;

import static io.github.up2jakarta.csv.BusinessBuilder.DEFAULT_LEVEL;

/**
 * {@link ModeType#FAST} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see MiniRecord
 * @see MiniError
 */
public final class SimpleFastImporter<T extends Referencable, B extends DataType<B>, I extends IType<B, I>>
        extends FastImporter<B, I, T, MiniRecord<I>, MiniError<B, MiniRecord<I>>>
        implements RecordTransformer<MiniRecord<I>> {

    private final SeverityType level;

    public <E extends Enum<E> & IType<B, I>> SimpleFastImporter(Up2Factory<B> mf, Class<T> type, E rootNode) throws BeanException {
        this(mf, type, rootNode, DEFAULT_LEVEL);
    }

    public <E extends Enum<E> & IType<B, I>> SimpleFastImporter(Up2Factory<B> mf, Class<T> type, E rootNode, SeverityType level) throws BeanException {
        //noinspection unchecked
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants(), level);
    }

    public SimpleFastImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        this(mf, type, rootNode, nodes, DEFAULT_LEVEL);
    }

    public SimpleFastImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes, SeverityType level) throws BeanException {
        super(mf, type, rootNode, nodes);
        this.level = level;
    }

    public SimpleFastImporter(SimpleFastExporter<T, B, I> source) throws BeanException {
        super(source);
        this.level = DEFAULT_LEVEL;
    }

    @Override
    protected UnitCollector<B, MiniRecord<I>> create(MiniRecord<I> row) {
        return new UnitCollector<>(row, level);
    }

    @Override
    public MiniRecord<I> transform(String... source) throws CodeListException {
        final I type = typing.type(source);
        final String[] data = typing.truncate(type, source);
        final String businessKey = source[mode.getBeanIdIndex()];
        return new MiniRecord<>(type, businessKey, data);
    }

    /**
     * Parses and returns the business-object created from the given records source.
     *
     * @param rows the records source
     * @return the parsed business-object with all collected errors
     * @throws BeanException     for any problem when setting fields from input record
     * @throws CodeListException if type of one record is unknown
     */
    public Up2Result<T, MiniError<B, MiniRecord<I>>> parse(List<String[]> rows) throws BeanException {
        final List<MiniRecord<I>> records = new ArrayList<>(rows.size());
        for (final String[] row : rows) {
            if (row == null || row.length <= mode.getTypeIdIndex()) {
                continue;
            }
            records.add(this.transform(row));
        }
        return this.parse(records);
    }

    @Override
    public SimpleFastExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleFastExporter<>(this);
    }

}
