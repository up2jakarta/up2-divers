package io.github.up2jakarta.csv.io;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Resettable;
import io.github.up2jakarta.csv.core.IMode;
import io.github.up2jakarta.csv.data.FullExporter;
import org.apache.commons.csv.CSVFormat;

import java.util.function.Supplier;

import static io.github.up2jakarta.lov.core.AccessException.notNull;

/**
 * Base CSV file {@link io.github.up2jakarta.csv.core.IMode#FULL} writer implementation.
 *
 * @param <T> the business object type
 */
public class FullFileWriter<T extends Segment> extends AbstractWriter<T> {

    private final Supplier<String> generator;

    public FullFileWriter(FullExporter<?, ?, T> exporter, CSVFormat format, Supplier<String> generator) {
        super(exporter, format);
        this.generator = notNull(generator, this.getClass(), "generator");
        assert exporter.mode() == IMode.FULL : "issue has been detected";
    }

    @Override
    protected final String get(int index) {
        return generator.get();
    }

    @Override
    protected final void reset() {
        if (generator instanceof Resettable rg) {
            rg.reset();
        }
    }

}
