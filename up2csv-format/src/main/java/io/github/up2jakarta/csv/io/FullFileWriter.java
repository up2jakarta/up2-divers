package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.FullExporter;
import io.github.up2jakarta.csv.core.ModeType;
import org.apache.commons.csv.CSVFormat;

import java.util.function.Supplier;

/**
 * Base CSV file {@link ModeType#FULL} writer implementation.
 *
 * @param <T> the business object type
 */
public class FullFileWriter<T extends Segment> extends AbstractWriter<T> {

    public FullFileWriter(FullExporter<?, ?, T> exporter, CSVFormat format, Supplier<String> generator) {
        super(exporter, generator, format);
    }

}
