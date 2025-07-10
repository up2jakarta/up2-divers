package io.github.up2jakarta.job;

import org.springframework.batch.item.ItemProcessor;

@SuppressWarnings("unused")
public abstract class ConditionalProcessor<I, O> implements ItemProcessor<I, O> {

    public abstract boolean canLoad(I item);

    public abstract O load(I item) throws Exception;

    public abstract O parse(I item) throws Exception;

    public final O process(I item) throws Exception {
        if (this.canLoad(item)) {
            return this.load(item);
        }
        return this.parse(item);
    }

}
