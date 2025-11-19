package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.api.hdl.IPropertyCreator;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitImporter;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.core.hdl.PropertyCollector.Builder;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.RecordTransformer;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.data.Up2Result;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.lov.core.BeanException;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ModeType#UNIT} Processor that's able to aggregate and import java-bean from flat-data.
 *
 * @param <T> the business object type
 * @param <B> the business data type
 * @param <I> the input segment type
 * @see FastRecord
 * @see PropertyEvent
 */
public final class SimpleUnitImporter<T extends Segment, B extends DataType<B>, I extends IType<B, I>>
        extends UnitImporter<B, I, T, UnitRecord<I>, PropertyEvent<B, UnitRecord<I>>>
        implements RecordTransformer<UnitRecord<I>> {

    public SimpleUnitImporter(Up2Factory<B> factory, Class<T> type, I root) throws BeanException {
        super(factory, type, root);
    }

    public SimpleUnitImporter(Up2Factory<B> factory, Class<T> type, I root, List<I> nodes) throws BeanException {
        super(factory, type, root, nodes);
    }

    public SimpleUnitImporter(SimpleUnitExporter<T, B, I> source) throws BeanException {
        super(source);
    }

    @Override
    protected Builder<B, UnitRecord<I>, PropertyEvent<B, UnitRecord<I>>> newBuilder(int size) {
        final IPropertyCreator<B, UnitRecord<I>, PropertyEvent<B, UnitRecord<I>>> creator = PropertyEvent::new;
        return new Builder<>(size, creator);
    }

    @Override
    public UnitRecord<I> transform(String... source) throws CodeListException {
        final I type = typing.type(source);
        final String[] data = typing.truncate(type, source);
        return new UnitRecord<>(type, data);
    }

    @Override
    public Up2Result<T, PropertyEvent<B, UnitRecord<I>>> parse(List<String[]> records) throws AccessException {
        final List<UnitRecord<I>> result = new ArrayList<>(records.size());
        for (final String[] record : records) {
            if (record == null || record.length <= mode.getTypeIdIndex()) {
                continue;
            }
            result.add(this.transform(record));
        }
        return this.parse(result);
    }

    @Override
    public SimpleUnitExporter<T, B, I> toExporter() throws BeanException {
        return new SimpleUnitExporter<>(this);
    }

}
