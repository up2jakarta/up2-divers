package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.LoggerAware;
import org.springframework.batch.infrastructure.item.ItemReader;

public interface ArchiveReader<T, R extends ArchiveResource> extends ItemReader<T>, LoggerAware {

    void open(R resource);

    void close();

}
