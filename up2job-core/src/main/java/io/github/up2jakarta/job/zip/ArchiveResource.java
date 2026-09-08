package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.ResourceAware;
import org.springframework.batch.core.step.StepExecution;

import java.io.Closeable;

public interface ArchiveResource extends ResourceAware, Closeable {

    @Override
    void close();

    void clean(StepExecution execution);

    long count();

    boolean isValid();

}
