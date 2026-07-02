package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.UnitExporter;
import org.apache.commons.csv.CSVFormat;

/**
 * Base CSV file {@link ModeType#UNIT} writer implementation.
 *
 * @param <T> the business object type
 */
public class UnitFileWriter<T extends Segment> extends AbstractWriter<T> {

    public UnitFileWriter(UnitExporter<?, ?, T> exporter, CSVFormat format) {
        super(exporter, format);
    }

}
