package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.DataType;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.fmt.ECause;
import io.github.up2jakarta.csv.fmt.FastRecord;
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
public final class SimpleFastReader<T extends Referencable, B extends DataType<B>, I extends IType<B, I>> extends FastFileReader<T, B, I, FastRecord<I>, ECause<B, FastRecord<I>>> {

    public SimpleFastReader(SimpleFastImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected FastRecord<I> create(I type, String beanId, String[] data) {
        return new FastRecord<>(type, beanId, data);
    }

}
