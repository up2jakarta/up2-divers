package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.ctx.ContextHolder;

public interface ArchiveManager<C extends BusinessContext> extends ContextHolder<C> {

    ZipArchive<C> open(final String path);

}
