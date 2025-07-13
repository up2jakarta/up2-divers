package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.core.LocalFile;
import io.github.up2jakarta.job.zip.ArchiveHandler;
import io.github.up2jakarta.job.zip.ZipWalker;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.IOException;

public final class FluxIterator<B extends BusinessType<B>, C extends BusinessContext> extends ZipWalker<B, C> {

    public FluxIterator(ArchiveHandler<B, C> handler, FluxIndex<B> index, LocalFile file, Logger logger) throws IOException {
        super(handler, index, file, logger);
    }

    boolean exists(String name) {
        return !(super.isSkipped(name) || super.get(name) == null);
    }

    @Override
    public void clean(StepExecution execution) {
        this.handle(execution);
    }

}
