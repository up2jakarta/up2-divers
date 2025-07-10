package io.github.up2jakarta.job.core;

@SuppressWarnings("unused")
public enum JobStatus {

    // Batch Status
    COMPLETED(false, false),
    @Deprecated(forRemoval = true)
    STARTING(true, false),
    STARTED(true, false),
    @Deprecated(forRemoval = true)
    STOPPING(true, false),
    STOPPED(false, false),
    FAILED(false, true),
    ABANDONED(false, true),
    UNKNOWN(false, true),
    // Exit Code
    @Deprecated(forRemoval = true)
    EXECUTING(true, false),
    @Deprecated(forRemoval = true)
    NOOP(false, false),
    // Extended Code
    REJECTED(false, true),
    CONTINUED(false, false),
    @Deprecated(forRemoval = true)
    RESTARTED(false, false);

    private final boolean running;
    private final boolean failure;

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
