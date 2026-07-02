package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.ITerm;
import io.github.up2jakarta.csv.fmt.FullError;
import io.github.up2jakarta.csv.fmt.FullRecord;
import io.github.up2jakarta.csv.fmt.SimpleFullImporter;
import org.apache.commons.csv.CSVFormat;

import static io.github.up2jakarta.lov.core.Codes.fixed;
import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Base CSV file {@link ModeType#FULL} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @see SimpleFullImporter
 */
public final class SimpleFullReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends FullFileReader<T, B, I, FullRecord<I>, FullError<B, FullRecord<I>>> {

    public SimpleFullReader(SimpleFullImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected FullRecord<I> create(long lineId, String rowId, I type, String beanId, String[] data) {
        if (rowId == null) {
            rowId = fixed(lineId);
        }
        return new FullRecord<>(rowId, type, token(beanId), data);
    }

}
