package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.hdl.InputCollector;
import io.github.up2jakarta.csv.fmt.hdl.InputError;
import io.github.up2jakarta.csv.fmt.hdl.InputRecord;
import io.github.up2jakarta.xml.clv.CodeListException;

/**
 * {@link ModeType#FULL} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see InputRecord
 * @see InputError
 */
public class SimpleFullImporter<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends FullImporter<T, B, I, InputRecord<I, ?>, InputError<B, InputRecord<I, ?>>> {

    public <E extends Enum<E> & IType<B, I>> SimpleFullImporter(Up2Factory<B> mf, Class<T> type, E rootNode) throws BeanException {
        //noinspection unchecked
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants());
    }

    public SimpleFullImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(mf, type, rootNode, nodes);
    }

    public SimpleFullImporter(FullExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected InputCollector<B, InputRecord<I, ?>> create(InputRecord<I, ?> row) {
        return new InputCollector<>(row);
    }

    /**
     * Creates and returns new record from the given record source.
     *
     * @param row    the record source data
     * @param source the record source
     * @param lineId the record number
     * @return new record
     * @throws CodeListException if type is unknown
     */
    public final <S extends Comparable<S>> InputRecord<I, S> record(S source, long lineId, String... row) throws CodeListException {
        final I type = typing.type(row);
        final String[] data = typing.truncate(type, row);
        final String recordId = row[mode.getRowKeyIndex()];
        final String businessKey = row[mode.getBeanIdIndex()];
        return new InputRecord<>(source, lineId, recordId, type, businessKey, data);
    }

}
