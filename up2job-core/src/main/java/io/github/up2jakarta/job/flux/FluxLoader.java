package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.zip.ArchiveWalker;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.util.List;

public interface FluxLoader<B extends BusinessType<B>, C extends BusinessContext, R extends ArchiveWalker<B, C>> {

    List<R> load(StepExecution step, Logger logger);

}
