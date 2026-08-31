package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.MessExporter;
import io.github.up2jakarta.csv.core.ModeType;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#MESS} writer implementation.
 *
 * @param <T> the business object type
 */
public class MessFileWriter<T extends Segment> extends AbstractWriter<T> {

    public MessFileWriter(MessExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter, format);
        assert exporter.mode() == ModeType.MESS : "issue has been detected";
    }

    @Override
    protected final void reset() {
    }

    @Override
    protected final String get(int index) {
        throw new UnsupportedOperationException();
    }

}
