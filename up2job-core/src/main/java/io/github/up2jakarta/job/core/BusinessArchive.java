package io.github.up2jakarta.job.core;

import io.github.up2jakarta.job.flux.FluxClassifier;

public interface BusinessArchive<B extends BusinessType<B>> extends ResourceAware, FluxClassifier<B> {

}
