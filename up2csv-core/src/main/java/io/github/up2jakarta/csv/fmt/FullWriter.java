package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.BusinessWriter;
import io.github.up2jakarta.csv.data.Referencable;
import io.github.up2jakarta.csv.data.Resettable;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Base full-writer for multi-segments format, that's able to write business objects to output stream.
 */
public abstract class FullWriter<T extends Referencable> extends BusinessWriter<T> {

    private final Supplier<String> generator;
    private final FullExporter<T, ?, ?> exporter;

    protected FullWriter(FullExporter<T, ?, ?> exporter, Supplier<String> generator) {
        this.generator = generator;
        this.exporter = exporter;
    }

    @Override
    public final void write(T bean) throws IOException, BeanException {
        exporter.format(bean, generator, this::write);
        this.flush();
    }

    /**
     * Resets the record-reference generator if implements {@link Resettable},
     * useful for reusing the writer for many streams.
     */
    protected void reset() {
        if (generator instanceof Resettable r) {
            r.reset();
        }
    }

}
