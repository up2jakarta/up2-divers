package io.github.up2jakarta.job;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@SuppressWarnings("unused")
public abstract class ConditionalWriter<T> implements ItemWriter<T>, StepExecutionListener {

    public abstract boolean isTransient(T item);

    public abstract void write(T item) throws Exception;

    public final void write(Chunk<? extends T> chunk) throws Exception {
        for (final T item : chunk) {
            if (this.isTransient(item)) {
                this.write(item);
            }
        }
    }

}
