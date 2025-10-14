package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.BusinessObject;

import java.io.IOException;

/**
 * Base fast-writer for multi-segments format for business objects.
 *
 * @param <T> the business object type
 */
public abstract class FastWriter<T extends BusinessObject> extends BusinessWriter<T> {

    protected FastWriter(FastAggregator<T, ?, ?, ?, ?> processor) {
        super(processor);
    }

    protected FastWriter(FastSeparator<T, ?, ?> processor) {
        super(processor);
    }

    @Override
    public final void write(T bean) throws IOException, BeanException {
        processor.format(bean, () -> null, this::write);
        this.flush();
    }

}
