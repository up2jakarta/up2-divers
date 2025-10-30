package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.SimpleFastImporter;
import io.github.up2jakarta.csv.fmt.SimpleUnitImporter;
import io.github.up2jakarta.csv.fmt.hdl.MiniError;
import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#UNIT} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleFastImporter
 */
public final class SimpleUnitReader<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends UnitFileReader<T, B, I, MiniRecord<I>, MiniError<B, MiniRecord<I>>> {

    public SimpleUnitReader(SimpleUnitImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected MiniRecord<I> create(I type, String[] data) {
        return new MiniRecord<>(type, null, data);
    }

}
