package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.ctx.ContextHolder;

import java.io.File;
import java.util.zip.ZipOutputStream;

public interface ArchiveManager<C extends BusinessContext> extends ContextHolder<C> {

    @SuppressWarnings("unused")
    ZipArchive<C> open(File resource);

    void close(ZipArchive<C> zip, ZipOutputStream stream);
}
