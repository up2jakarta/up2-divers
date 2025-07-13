package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessArchive;
import io.github.up2jakarta.job.core.BusinessException;

import java.io.File;

public interface ArchiveTranslator {

    BusinessException validateFailed(BusinessArchive<?> source);

    BusinessException cleanFailed(ArchiveResource source);

    BusinessException cleanFailed(ArchiveResource source, Exception cause);

    BusinessException countFailed(ArchiveResource source, Exception cause);

    BusinessException validateFailed(ArchiveResource source, Exception cause);

    BusinessException openWriteFailed(File source, Exception cause);

    BusinessException closeWriteFailed(ArchiveResource source, Exception cause);

    BusinessException putEntryFailed(ArchiveResource source, Exception cause);

    BusinessException writeEntryFailed(ArchiveResource source, Exception cause);

}
