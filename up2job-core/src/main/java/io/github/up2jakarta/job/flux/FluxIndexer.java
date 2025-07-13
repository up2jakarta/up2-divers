package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.zip.Entry;

public interface FluxIndexer<B extends BusinessType<B>, C extends BusinessContext> {

    @SuppressWarnings("unused")
    void index(Entry<B, C> entry);

}
