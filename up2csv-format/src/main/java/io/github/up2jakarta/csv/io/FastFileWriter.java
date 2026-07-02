package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.FastExporter;
import io.github.up2jakarta.csv.core.ModeType;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#FAST} writer implementation.
 *
 * @param <T> the business object type
 */
public class FastFileWriter<T extends Segment> extends AbstractWriter<T> {

    public FastFileWriter(FastExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter, format);
    }

}
