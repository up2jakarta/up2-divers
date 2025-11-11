package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.*;
import io.github.up2jakarta.csv.core.hdl.PropertyCollector;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.xml.clv.CodeListException;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ModeType#FAST} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see FastRecord
 * @see PropertyEvent
 */
public final class SimpleFastImporter<T extends Segment, B extends DataType<B>, I extends IType<B, I>>
        extends FastImporter<B, I, T, FastRecord<I, String>, PropertyEvent<B, FastRecord<I, String>>>
        implements RecordTransformer<FastRecord<I, String>> {

    @SuppressWarnings("unchecked")
    public <E extends Enum<E> & IType<B, I>> SimpleFastImporter(Up2Factory<B> mf, Class<T> type, E rootNode) throws BeanException {
        this(mf, type, (I) rootNode, ((Class<I>) rootNode.getClass()).getEnumConstants());
    }

    public SimpleFastImporter(Up2Factory<B> mf, Class<T> type, I rootNode, I[] nodes) throws BeanException {
        super(mf, type, rootNode, nodes);
    }

    public SimpleFastImporter(SimpleFastExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected PropertyCollector<FastRecord<I, String>, B, PropertyEvent<B, FastRecord<I, String>>> create(FastRecord<I, String> row) {
        final IPropertyCreator<FastRecord<I, String>, B, PropertyEvent<B, FastRecord<I, String>>> creator = PropertyEvent::new;
        return new PropertyCollector<>(row, creator);
    }

    @Override
    public FastRecord<I, String> transform(String... source) throws CodeListException {
        final I type = typing.type(source);
        final String[] data = typing.truncate(type, source);
        final String businessKey = source[mode.getBeanIdIndex()];
        return new FastRecord<>(type, businessKey, data);
    }

    /**
     * Parses and returns the business-object created from the given records source.
     *
     * @param rows the records source
     * @return the parsed business-object with all collected errors
     * @throws AccessException   for any problem when setting properties of java-beans from input record
     * @throws CodeListException if type of one record is unknown
     */
    public Up2Result<T, PropertyEvent<B, FastRecord<I, String>>> parse(List<String[]> rows) throws AccessException {
        final List<FastRecord<I, String>> records = new ArrayList<>(rows.size());
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
