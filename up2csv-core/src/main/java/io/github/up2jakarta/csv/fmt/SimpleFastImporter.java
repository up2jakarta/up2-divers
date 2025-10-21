package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.csv.fmt.hdl.MiniCollector;
import io.github.up2jakarta.csv.fmt.hdl.MiniError;
import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import io.github.up2jakarta.xml.api.SeverityType;
import io.github.up2jakarta.xml.clv.CodeListException;
import io.github.up2jakarta.xml.clv.PropertyException;

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
public class SimpleFastImporter<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends FastImporter<T, B, I, MiniRecord<I>, MiniError<B, MiniRecord<I>>> {

    protected final SeverityType level;

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

    public SimpleFastImporter(FastExporter<T, B, I> source) throws BeanException {
        super(source);
        this.level = DEFAULT_LEVEL;
    }

    @Override
    protected MiniCollector<B, MiniRecord<I>> create(MiniRecord<I> row) {
        return new MiniCollector<>(row, level);
    }

    /**
     * Creates and returns new record from the given record source.
     *
     * @param row the record source
     * @return new record
     * @throws CodeListException if type is unknown
     */
    public final MiniRecord<I> record(String... row) throws CodeListException {
        final I type = typing.type(row);
        final String[] data = typing.truncate(type, row);
        return new MiniRecord<>(type, row[mode.getBeanIdIndex()], data);
    }

    /**
     * Parses and returns the business-object created from the given records source.
     *
     * @param rows the records source
     * @return the parsed business-object with all collected errors
     * @throws BeanException     for any problem when setting fields from input record
     * @throws PropertyException if type of one record is unknown
     */
    public final Up2Result<T, MiniError<B, MiniRecord<I>>> parse(List<String[]> rows) throws BeanException, PropertyException {
        final List<MiniRecord<I>> records = new ArrayList<>(rows.size());
        for (final String[] row : rows) {
            if (row == null || row.length <= mode.getTypeIdIndex()) {
                continue;
            }
            records.add(this.record(row));
        }
        return this.parse(records);
    }

}
