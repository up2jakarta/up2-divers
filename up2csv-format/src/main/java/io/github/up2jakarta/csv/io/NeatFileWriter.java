package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.NeatExporter;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#NEAT} writer implementation.
 *
 * @param <T> the business object type
 */
public class NeatFileWriter<T extends Segment> extends AbstractWriter<T> {

    public NeatFileWriter(NeatExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter, format);
    }

    @Override
    protected final void reset() {
    }

    @Override
    protected final String get(int index) {
        throw new UnsupportedOperationException();
    }

}
