package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessType;

import java.util.List;

@FunctionalInterface
public interface FluxClassifier<B extends BusinessType<B>> {

    default FluxIndex<B> getIndex() {
        return this.getIndexes().getFirst();
    }

    List<FluxIndex<B>> getIndexes();

}
