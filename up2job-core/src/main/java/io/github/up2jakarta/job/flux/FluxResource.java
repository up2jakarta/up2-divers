package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.zip.ArchiveResource;
import org.springframework.batch.core.step.StepExecution;

public final class FluxResource<R extends ArchiveResource> implements ArchiveResource {

    private final R firstArchive;
    private final R otherArchive;

    public FluxResource(R firstArchive, R otherArchive) {
        this.firstArchive = firstArchive;
        this.otherArchive = otherArchive;
    }

    public R getFirstArchive() {
        return firstArchive;
    }

    public R getOtherArchive() {
        return otherArchive;
    }

    @Override
    public void close() {
        boolean done = false;
        try {
            firstArchive.close();
            done = true;
            otherArchive.close();
        } catch (RuntimeException cause) {
            if (done) {
                throw cause;
            } else {
                otherArchive.close();
            }
        }
    }

    @Override
    public void clean(StepExecution execution) {
        firstArchive.clean(execution);
        otherArchive.clean(execution);
    }

    @Override
    public long count() {
        final long firstCount = firstArchive.count();
        final long otherCount = otherArchive.count();
        return Math.min(firstCount, otherCount);
    }

    @Override
    public boolean isValid() {
        return firstArchive.isValid() && otherArchive.isValid();
    }
}
