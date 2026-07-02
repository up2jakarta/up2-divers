package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.hdl.PropertyEvent;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.fmt.FastRecord;
import io.github.up2jakarta.csv.fmt.SimpleFastImporter;
import org.apache.commons.csv.CSVFormat;

import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Base CSV file {@link ModeType#FAST} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @see SimpleFastImporter
 */
public final class SimpleFastReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends FastFileReader<T, B, I, FastRecord<I>, PropertyEvent<B, FastRecord<I>>> {

    public SimpleFastReader(SimpleFastImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected FastRecord<I> create(I type, String beanId, String[] data) {
        return new FastRecord<>(type, token(beanId), data);
    }

}
