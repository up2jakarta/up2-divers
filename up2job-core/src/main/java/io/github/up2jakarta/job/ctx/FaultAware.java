package io.github.up2jakarta.job.ctx;

interface FaultAware {

    void addFailures(long count);

    long getFailures();

}
