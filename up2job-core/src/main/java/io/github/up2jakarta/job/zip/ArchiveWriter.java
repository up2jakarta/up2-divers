package io.github.up2jakarta.job.zip;

import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ItemWriter;

public interface ArchiveWriter<T, R extends ArchiveResource> extends ItemWriter<T> {

    boolean isTransient(T item);

    void open(R resource);

    void write(T item) throws Exception;

    R close(StepExecution execution);

}
