package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitImporter;
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
 * {@link ModeType#UNIT} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see MiniRecord
 * @see MiniError
 */
public final class SimpleUnitImporter<T extends Referencable, B extends DataType<B>, I extends IType<B, I>>
        extends UnitImporter<B, I, T, UnitRecord<I>, MiniError<B, UnitRecord<I>>>
        implements RecordTransformer<UnitRecord<I>> {

    private final SeverityType level;

    public <E extends Enum<E> & IType<B, I>> SimpleUnitImporter(Up2Factory<B> mf, Class<T> type, E rootNode) throws BeanException {
        this(mf, type, rootNode, DEFAULT_LEVEL);
    }

    public <E extends Enum<E> & IType<B, I>> SimpleUnitImporter(Up2Factory<B> mf, Class<T> type, E rootNode, SeverityType level) throws BeanException {
        //noinspection unchecked
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants(), level);
    }

    public SimpleUnitImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        this(mf, type, rootNode, nodes, DEFAULT_LEVEL);
    }

    public SimpleUnitImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes, SeverityType level) throws BeanException {
        super(mf, type, rootNode, nodes);
        this.level = level;
    }

    public SimpleUnitImporter(SimpleUnitExporter<T, B, I> source) throws BeanException {
        super(source);
        this.level = DEFAULT_LEVEL;
    }

    @Override
    protected UnitCollector<B, UnitRecord<I>> create(UnitRecord<I> row) {
        return new UnitCollector<>(row, level);
    }

    @Override
    public UnitRecord<I> transform(String... source) throws CodeListException {
        final I type = typing.type(source);
        final String[] data = typing.truncate(type, source);
        return new UnitRecord<>(type, data);
    }

    /**
     * Parses and returns the business-object created from the given records source.
     *
     * @param rows the records source
     * @return the parsed business-object with all collected errors
     * @throws BeanException     for any problem when setting fields from input record
     * @throws CodeListException if type of one record is unknown
     */
    public Up2Result<T, MiniError<B, UnitRecord<I>>> parse(List<String[]> rows) throws BeanException {
        final List<UnitRecord<I>> records = new ArrayList<>(rows.size());
        for (final String[] row : rows) {
            if (row == null || row.length <= mode.getTypeIdIndex()) {
                continue;
            }
            records.add(this.transform(row));
        }
        return this.parse(records);
    }

    @Override
    public SimpleUnitExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleUnitExporter<>(this);
    }

}
