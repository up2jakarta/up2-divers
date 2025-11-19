package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.csv.fmt.SimpleFullImporter;
import io.github.up2jakarta.lov.core.Codes;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#FULL} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleFullImporter
 */
public final class SimpleFullReader<T extends Segment, B extends DataType<B>, I extends IType<B, I>> extends FullFileReader<T, B, I, FullRecord<I, String>, FullError<B, String, FullRecord<I, String>>> {

    public SimpleFullReader(SimpleFullImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected FullRecord<I, String> create(long lineId, String recordKey, I type, String beanId, String[] data) {
        if (recordKey == null) {
            recordKey = Codes.fixed(lineId);
        }
        return new FullRecord<>(recordKey, type, Codes.token(beanId), data);
    }

}
