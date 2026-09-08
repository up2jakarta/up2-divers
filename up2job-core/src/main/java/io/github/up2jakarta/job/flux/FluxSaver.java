package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.zip.ZipArchive;
import org.slf4j.Logger;
import org.springframework.batch.core.step.StepExecution;

public interface FluxSaver<B extends BusinessType<B>, C extends BusinessContext> {

    FluxPart save(StepExecution step, Logger log, B type, ZipArchive<? extends C> part);

}
