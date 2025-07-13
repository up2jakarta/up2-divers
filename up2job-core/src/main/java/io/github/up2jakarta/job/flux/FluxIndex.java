package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessType;

public record FluxIndex<B extends BusinessType<B>>(long archiveId, B type) {

}
