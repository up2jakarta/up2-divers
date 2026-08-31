package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.ITerm;
import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.data.NeatRecord;
import io.github.up2jakarta.csv.data.SimpleMessImporter;
import io.github.up2jakarta.csv.data.SimpleNeatImporter;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#NEAT} reader implementation.
 *
 * @param <T> the business object type
 * @param <B> the business term type
 * @param <I> the segment type
 * @see SimpleMessImporter
 */
public final class SimpleNeatReader<T extends Segment, B extends ITerm<B>, I extends Enum<I> & IType<I>> extends NeatFileReader<T, B, I, NeatRecord<I>, PropertyEvent<B, NeatRecord<I>>> {

    public SimpleNeatReader(SimpleNeatImporter<T, B, I> importer, CSVFormat format, String... nullValues) {
        super(importer, format, nullValues);
    }

    @Override
    protected NeatRecord<I> create(I type, String[] data) {
        return new NeatRecord<>(type, data);
    }

}
