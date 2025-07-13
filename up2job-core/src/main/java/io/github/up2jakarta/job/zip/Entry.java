package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.*;
import io.github.up2jakarta.job.flux.FluxClassifier;

public interface Entry<B extends BusinessType<B>, C extends BusinessContext> extends FluxClassifier<B>, ReferenceAware<C>, Cleanable {

    @SuppressWarnings("unused")
    default long getBusinessId() {
        return this.getId().id();
    }

    default String reference(BusinessContext context) {
        return this.getId().reference();
    }

    BusinessId getId();

    String getName();

}
