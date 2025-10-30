package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IFullType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.SimpleFullImporter;
import io.github.up2jakarta.csv.fmt.hdl.InputError;
import io.github.up2jakarta.csv.fmt.hdl.InputRecord;
import org.apache.commons.csv.CSVFormat;

import java.io.File;

/**
 * Base CSV file {@link ModeType#FULL} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleFullImporter
 */
public final class SimpleFullReader<T extends Referencable, B extends DataType<B>, I extends IFullType<B, I>> extends FullFileReader<T, B, I, InputRecord<I, ?>, InputError<B, InputRecord<I, ?>>> {

    public SimpleFullReader(SimpleFullImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected InputRecord<I, File> create(long lineId, String recordKey, I type, String beanKey, String[] data) {
        return new InputRecord<>(this.getSource(), lineId, recordKey, type, beanKey, data);
    }

}
