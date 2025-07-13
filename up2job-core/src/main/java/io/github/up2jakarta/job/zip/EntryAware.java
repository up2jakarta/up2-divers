package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessType;

@FunctionalInterface
public interface EntryAware<B extends BusinessType<B>, S extends EntrySkipper> {

    void register(B key, S value);

}
