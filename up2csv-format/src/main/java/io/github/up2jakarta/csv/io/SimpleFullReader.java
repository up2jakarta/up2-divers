package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.InputError;
import io.github.up2jakarta.csv.fmt.InputRecord;
import io.github.up2jakarta.csv.fmt.SimpleFullImporter;
import io.github.up2jakarta.xml.adapters.KeyCoder;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#FULL} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleFullImporter
 */
public final class SimpleFullReader<T extends Referencable, B extends DataType<B>, I extends IType<B, I>> extends FullFileReader<T, B, I, InputRecord<I>, InputError<B, InputRecord<I>>> {

    public SimpleFullReader(SimpleFullImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected InputRecord<I> create(long lineId, String recordKey, I type, String beanKey, String[] data) {
        if (recordKey == null) {
            recordKey = KeyCoder.fixed(lineId);
        }
        return new InputRecord<>(recordKey, type, beanKey, data);
    }

}
