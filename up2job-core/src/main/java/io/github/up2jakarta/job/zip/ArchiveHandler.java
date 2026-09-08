package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessArchive;
import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.ctx.ContextHolder;
import org.slf4j.Logger;
import org.springframework.batch.core.step.StepExecution;

public interface ArchiveHandler<B extends BusinessType<B>, C extends BusinessContext> extends ContextHolder<C> {

    EntryDecoder getDecoder();

    ArchiveTranslator getTranslator();

    void onEmpty(BusinessArchive<B> archive, StepExecution step, Logger logger);

    void onSuccess(BusinessArchive<B> archive, long count, StepExecution step, Logger logger);

    void onUpdate(BusinessArchive<B> archive, long count, StepExecution step, Logger logger);

}
