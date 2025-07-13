package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.ConditionalWriter;
import io.github.up2jakarta.job.core.*;
import org.springframework.batch.core.StepExecution;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.zip.ZipEntry;

import static io.github.up2jakarta.job.zip.EntryDecoder.encodeEntry;

@SuppressWarnings("unused")
public class ZipWriter<B extends BusinessType<B>, C extends BusinessContext, T extends BusinessObject<B, ? extends C>>
        extends ConditionalWriter<T> implements ArchiveWriter<T, ZipArchive<C>> {

    private final BusinessGenerator<B, T> generator;
    private final BusinessFilter<B, T> filter;
    private final B type;

    private ZipArchive<C> part;

    public ZipWriter(BusinessFilter<B, T> filter, BusinessGenerator<B, T> generator, B type) {
        this.generator = generator;
        this.filter = filter;
        this.type = type;
    }

    @Override
    public void open(ZipArchive<C> resource) {
        this.part = resource;
    }

    @Override
    public boolean isTransient(T item) {
        return !filter.test(item) && item.getKey() != null;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void write(T item) {
        item.register(type, part);
        final ZipEntry entry = encodeEntry(item);
        this.part.write(entry, generator.apply(item));
    }

    @Override
    public ZipArchive<C> close(StepExecution execution) {
        try {
            part.clean(execution);
            return part;
        } finally {
            part = null;
        }
    }

}
