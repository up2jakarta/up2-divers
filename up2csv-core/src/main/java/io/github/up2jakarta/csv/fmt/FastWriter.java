package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.BusinessWriter;
import io.github.up2jakarta.csv.data.Referencable;

import java.io.IOException;

/**
 * Base fast-writer for multi-segments format, that's able to write business objects to output stream.
 *
 * @param <T> the business object type
 */
public abstract class FastWriter<T extends Referencable> extends BusinessWriter<T> {

    protected final FastExporter<T, ?, ?> exporter;

    protected FastWriter(FastExporter<T, ?, ?> exporter) {
        this.exporter = exporter;
    }

    @Override
    public final void write(T bean) throws IOException, BeanException {
        exporter.format(bean, this::write);
        this.flush();
    }

}
