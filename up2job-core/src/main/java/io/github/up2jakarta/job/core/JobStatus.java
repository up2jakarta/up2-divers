package io.github.up2jakarta.job.core;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;

@SuppressWarnings("unused")
public enum JobStatus {

    // Batch Status
    COMPLETED(BatchStatus.COMPLETED),
    @Deprecated(forRemoval = true)
    STARTING(BatchStatus.STARTING),
    STARTED(BatchStatus.STARTED),
    @Deprecated(forRemoval = true)
    STOPPING(BatchStatus.STOPPING),
    STOPPED(BatchStatus.STOPPED),
    FAILED(BatchStatus.FAILED),
    ABANDONED(BatchStatus.ABANDONED),
    UNKNOWN(BatchStatus.UNKNOWN),
    // Exit Status
    @Deprecated(forRemoval = true)
    EXECUTING(ExitStatus.EXECUTING),
    @Deprecated(forRemoval = true)
    NOOP(ExitStatus.NOOP),
    // Extended Status
    REJECTED(false, true),
    CONTINUED(false, false),
    @Deprecated(forRemoval = true)
    RESTARTED(false, false);

    private final boolean running;
    private final boolean failure;

    JobStatus(BatchStatus status) {
        this(status.isRunning(), status.isUnsuccessful());
    }

    JobStatus(ExitStatus status) {
        this(status.isRunning(), false);
    }

    JobStatus(boolean running, boolean failure) {
        this.running = running;
        this.failure = failure;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isFailure() {
        return failure;
    }

}
