package io.github.up2jakarta.job;

import org.springframework.batch.item.ItemProcessor;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class CompositeProcessorBuilder<I, O> {

    private final List<ItemProcessor<?, ?>> delegates = new LinkedList<>();

    public CompositeProcessorBuilder(ItemProcessor<? super I, ? extends O> first) {
        this.delegates.add(first);
    }

    @SuppressWarnings("unchecked")
    public <N> CompositeProcessorBuilder<I, N> next(ItemProcessor<O, N> other) {
        delegates.add(other);
        return (CompositeProcessorBuilder<I, N>) this;
    }

    public <N> CompositeProcessor<I, N> end(ItemProcessor<O, N> other) {
        return this.next(other).build();
    }

    private CompositeProcessor<I, O> build() {
        final ItemProcessor<?, ?>[] d = delegates.toArray(ItemProcessor<?, ?>[]::new);
        return new CompositeProcessor<>(d);
    }

}
