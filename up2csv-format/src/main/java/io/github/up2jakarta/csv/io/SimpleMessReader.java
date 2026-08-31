package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.MessRecord;
import io.github.up2jakarta.csv.data.SimpleMessImporter;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import org.apache.commons.csv.CSVFormat;

import static io.github.up2jakarta.lov.core.Codes.token;

/**
 * Base CSV file {@link ModeType#MESS} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @see SimpleMessImporter
 */
public final class SimpleMessReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends MessFileReader<T, B, I, MessRecord<I>, PropertyEvent<B, MessRecord<I>>> {

    public SimpleMessReader(SimpleMessImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected MessRecord<I> create(I type, String beanId, String[] data) {
        return new MessRecord<>(type, token(beanId), data);
    }

}
