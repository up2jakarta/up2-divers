package io.github.up2jakarta.csv.ops;

import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.data.Resettable;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Base full-writer for multi-segments format for business objects.
 */
public abstract class FullWriter<T extends BusinessObject> extends BusinessWriter<T> {

    private final Supplier<String> generator;

    protected FullWriter(FullAggregator<T, ?, ?, ?, ?> processor, Supplier<String> generator) {
        super(processor);
        this.generator = generator;
    }

    protected FullWriter(FullSeparator<T, ?, ?> processor, Supplier<String> generator) {
        super(processor);
        this.generator = generator;
    }

    @Override
    public final void write(T bean) throws IOException, BeanException {
        processor.format(bean, generator, this::write);
        this.flush();
    }

    /**
     * Resets the record-reference generator if implements {@link Resettable}, useful for reusing the writer for many streams.
     */
    protected void reset() {
        if (generator instanceof Resettable r) {
            r.reset();
        }
    }

}
