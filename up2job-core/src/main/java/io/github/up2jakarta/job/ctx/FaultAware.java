package io.github.up2jakarta.job.ctx;

public interface FaultAware {

    void addFailures(long count);

    long getFailures();

}
