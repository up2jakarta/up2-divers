package io.github.up2jakarta.job.zip;

import org.springframework.batch.core.StepExecution;

@SuppressWarnings("ALL")
public final class FluxResource<R extends ArchiveResource> implements ArchiveResource {

    private final R f1Archive;
    private final R f2Archive;

    public FluxResource(R f1Archive, R f2Archive) {
        this.f1Archive = f1Archive;
        this.f2Archive = f2Archive;
    }

    public R getF1Archive() {
        return f1Archive;
    }

    public R getF2Archive() {
        return f2Archive;
    }

    @Override
    public void close() {
        boolean done = false;
        try {
            f1Archive.close();
            done = true;
            f2Archive.close();
        } catch (RuntimeException cause) {
            if (done) {
                throw cause;
            } else {
                f2Archive.close();
            }
        }
    }

    @Override
    public void clean(StepExecution execution) {
        f1Archive.clean(execution);
        f2Archive.clean(execution);
    }

    @Override
    public long count() {
        final long f1Count = f1Archive.count();
        final long f2Count = f2Archive.count();
        return Math.min(f1Count, f2Count);
    }

    @Override
    public boolean isValid() {
        return f1Archive.isValid() && f2Archive.isValid();
    }
}
