package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.fmt.SimpleFastImporter;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.UnitRecord;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#UNIT} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @see SimpleFastImporter
 */
public final class SimpleUnitReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends UnitFileReader<T, B, I, UnitRecord<I>, PropertyEvent<B, UnitRecord<I>>> {

    public SimpleUnitReader(SimpleUnitImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected UnitRecord<I> create(I type, String[] data) {
        return new UnitRecord<>(type, data);
    }

}
