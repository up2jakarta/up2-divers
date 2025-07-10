package io.github.up2jakarta.job;

import org.springframework.batch.item.ItemProcessor;

public class CompositeProcessor<I, O> implements ItemProcessor<I, O> {

    private final ItemProcessor<?, ?>[] delegates;

    CompositeProcessor(ItemProcessor<?, ?>[] delegates) {
        this.delegates = delegates;
    }

    @Override
    @SuppressWarnings("ALL")
    public O process(I item) throws Exception {
        Object result = item;
        for (final ItemProcessor processor : delegates) {
            result = processor.process(result);
            if (result == null) {
                return null;
            }
        }
        return (O) result;
    }

}
