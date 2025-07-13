package io.github.up2jakarta.job.core;

import io.github.up2jakarta.job.zip.EntryAware;
import io.github.up2jakarta.job.zip.EntrySkipper;

public interface BusinessObject<B extends BusinessType<B>, C extends BusinessContext>
        extends ReferenceAware<C>, EntryAware<B, EntrySkipper> {

    Long getKey();

    String getReference();

}
