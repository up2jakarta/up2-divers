package io.github.up2jakarta.job.zip;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.Chunk;

@SuppressWarnings("unused")
public class FluxWriter<T, R extends ArchiveResource> implements ArchiveWriter<T, FluxResource<R>> {

    private final ArchiveWriter<T, R> f2Writer;
    private final ArchiveWriter<T, R> f1Writer;

    private FluxResource<R> flux;

    public FluxWriter(ArchiveWriter<T, R> f2Writer, ArchiveWriter<T, R> f1Writer) {
        this.f2Writer = f2Writer;
        this.f1Writer = f1Writer;
    }

    @Override
    public boolean isTransient(T item) {
        return f2Writer.isTransient(item) && f1Writer.isTransient(item);
    }

    @Override
    public void open(FluxResource<R> flux) {
        this.flux = flux;
        f2Writer.open(flux.getF2Archive());
        f1Writer.open(flux.getF1Archive());
    }

    @Override
    public void write(T item) throws Exception {
        if (f2Writer.isTransient(item)) {
            f2Writer.write(item);
        }
        if (f1Writer.isTransient(item)) {
            f1Writer.write(item);
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
