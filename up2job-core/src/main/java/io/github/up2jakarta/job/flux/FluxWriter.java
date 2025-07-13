package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.zip.ArchiveResource;
import io.github.up2jakarta.job.zip.ArchiveWriter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.Chunk;

@SuppressWarnings("unused")
public class FluxWriter<T, R extends ArchiveResource> implements ArchiveWriter<T, FluxResource<R>> {

    private final ArchiveWriter<T, R> firstWriter;
    private final ArchiveWriter<T, R> otherWriter;

    private FluxResource<R> flux;

    public FluxWriter(ArchiveWriter<T, R> firstWriter, ArchiveWriter<T, R> otherWriter) {
        this.firstWriter = firstWriter;
        this.otherWriter = otherWriter;
    }

    @Override
    public boolean isTransient(T item) {
        return firstWriter.isTransient(item) && otherWriter.isTransient(item);
    }

    @Override
    public void open(FluxResource<R> flux) {
        this.flux = flux;
        firstWriter.open(flux.getFirstArchive());
        otherWriter.open(flux.getOtherArchive());
    }

    @Override
    public void write(T item) throws Exception {
        if (firstWriter.isTransient(item)) {
            firstWriter.write(item);
        }
        if (otherWriter.isTransient(item)) {
            otherWriter.write(item);
        }
    }

    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {
        for (final T item : chunk) {
            this.write(item);
        }
    }

    @Override
    public FluxResource<R> close(StepExecution execution) {
        try {
            flux.clean(execution);
            return flux;
        } finally {
            flux = null;
        }
    }

}
