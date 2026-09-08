package io.github.up2jakarta.job;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.flux.FluxEntry;
import io.github.up2jakarta.job.flux.FluxOperator;
import io.github.up2jakarta.job.flux.FluxPart;
import io.github.up2jakarta.job.flux.FluxResource;
import io.github.up2jakarta.job.zip.ArchiveWriter;
import io.github.up2jakarta.job.zip.ZipArchive;
import org.slf4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public abstract class MultipleWriter<T, B extends BusinessType<B>, C extends BusinessContext> implements ItemWriter<T>, StepExecutionListener {

    protected final C context;
    private final FluxOperator<B, ? super C> ops;
    private final ArchiveWriter<? super T, FluxResource<ZipArchive<C>>> delegate;
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger index = new AtomicInteger(0);
    private final int limitPerArchive;

    private StepExecution execution;

    protected MultipleWriter(C ctx, int lmt, ArchiveWriter<T, FluxResource<ZipArchive<C>>> fuw, FluxOperator<B, ? super C> ops) {
        this.ops = ops;
        this.delegate = fuw;
        this.context = ctx;
        this.limitPerArchive = lmt;
    }

    private void doClose() {
        final FluxResource<ZipArchive<C>> flux = delegate.close(execution);
        if (ops.accept(flux)) {
            this.getLogger().debug("{} : Closing output archive {} with ({}) files", context, flux, count);
            final FluxPart firstPart = ops.save(getExecution(), getLogger(), ops.getFirstType(), flux.getFirstArchive());
            final FluxPart otherPart = ops.save(getExecution(), getLogger(), ops.getOtherType(), flux.getOtherArchive());
            this.close(new FluxEntry(firstPart, otherPart));
        } else {
            this.getLogger().debug("{} : Ignoring empty archive {}", context, flux);
        }
    }

    private void doOpen(int index) {
        final ZipArchive<C> firstArchive = this.open(ops.getFirstType(), index);
        final ZipArchive<C> otherArchive = this.open(ops.getOtherType(), index);
        final FluxResource<ZipArchive<C>> resource = new FluxResource<>(firstArchive, otherArchive);
        this.getLogger().debug("{} : Opening output archive {}", context, resource);
        delegate.open(resource);
        count.set(0);
    }

    protected abstract Logger getLogger();

    protected abstract ZipArchive<C> open(B type, int index);

    protected abstract void close(FluxEntry entry);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.execution = stepExecution;
        index.set(0);
        doOpen(index.incrementAndGet());
    }

    @Override
    public final void write(Chunk<? extends T> items) throws Exception {
        delegate.write(items);
        final int c = count.addAndGet(items.size());
        if (c >= limitPerArchive) {
            this.doClose();
            this.doOpen(index.incrementAndGet());
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        this.doClose();
        return null;
    }

    protected final StepExecution getExecution() {
        return execution;
    }

}
