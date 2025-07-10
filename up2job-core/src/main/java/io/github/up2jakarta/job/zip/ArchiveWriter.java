package io.github.up2jakarta.job.zip;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;

@SuppressWarnings("unused")
public interface ArchiveWriter<T, R extends ArchiveResource> extends ItemWriter<T> {

    boolean isTransient(T item);

    void open(R resource);

    void write(T item) throws Exception;

    R close(StepExecution execution);

}
