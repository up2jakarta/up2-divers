package io.github.up2jakarta.job.zip;

import org.springframework.batch.item.ItemReader;

@SuppressWarnings("unused")
public interface ArchiveReader<T, R extends ArchiveResource> extends ItemReader<T> {

    void open(R resource);

    void close();

}
