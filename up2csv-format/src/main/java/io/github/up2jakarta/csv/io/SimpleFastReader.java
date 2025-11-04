package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.MiniError;
import io.github.up2jakarta.csv.fmt.MiniRecord;
import io.github.up2jakarta.csv.fmt.SimpleFastImporter;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the data type
 * @param <I> the segment type
 * @see SimpleFastImporter
 */
public final class SimpleFastReader<T extends Referencable, B extends DataType<B>, I extends IType<B, I>> extends FastFileReader<T, B, I, MiniRecord<I>, MiniError<B, MiniRecord<I>>> {

    public SimpleFastReader(SimpleFastImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected MiniRecord<I> create(I type, String beanId, String[] data) {
        return new MiniRecord<>(type, beanId, data);
    }

}
