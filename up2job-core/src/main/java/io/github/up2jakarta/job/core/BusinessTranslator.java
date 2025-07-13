package io.github.up2jakarta.job.core;

import io.github.up2jakarta.job.zip.ArchiveResource;

@FunctionalInterface
public interface BusinessTranslator {

    BusinessException translate(ArchiveResource source, Exception error);

}
