package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.core.LocalFile;
import io.github.up2jakarta.job.flux.FluxIndex;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static io.github.up2jakarta.job.core.ResourceAware.delete;
import static io.github.up2jakarta.job.ctx.Flows.isStopped;

public class ZipWalker<B extends BusinessType<B>, C extends BusinessContext> extends ZipResource<C> implements ArchiveWalker<B, C> {

    private final ZipFile archive;
    private final EntryDecoder decoder;
    private final ArchiveHandler<B, C> handler;
    private final List<FluxIndex<B>> indexes;
    private final Enumeration<? extends ZipEntry> entries;

    public ZipWalker(ArchiveHandler<B, C> handler, FluxIndex<B> index, LocalFile file, Logger logger) throws IOException {
        super(handler.getContext(), logger, file.toString(), file.getResource(), handler.getTranslator());
        this.archive = new ZipFile(resource);
        this.decoder = handler.getDecoder();
        this.entries = archive.entries();
        this.handler = handler;
        this.indexes = List.of(index);
    }

    protected final ZipEntry get(String name) {
        return this.archive.getEntry(name);
    }

    protected final Optional<BusinessId> decode(ZipEntry entry) {
        if (entry == null || entry.getSize() == 0) {
            return Optional.empty();
        }
        return decoder.decode(entry, () -> archive.getInputStream(entry));
    }

    protected void handle(StepExecution execution) {
        if (isStopped(execution)) {
            logger.warn("{} : Ignore archive #[{}] synchronization because of an unexpected error", context, this);
            this.ignore();
        } else if (execution.getWriteCount() == 0) {
            handler.onEmpty(this, execution, logger);
            this.ignore();
        } else if (this.isClean()) {
            handler.onSuccess(this, this.count(), execution, logger);
        } else {
            this.clean(execution, true);
            final long count = this.count();
            if (count == 0) {
                handler.onEmpty(this, execution, logger);
            } else {
                handler.onUpdate(this, count, execution, logger);
            }
        }
    }

    @Override
    public void clean(StepExecution execution) {
        this.handle(execution);
        this.clean();
    }

    @Override
    public void clean() {
        delete(resource, logger, context);
    }

    @Override
    public boolean isCleaned() {
        return !resource.exists();
    }

    @Override
    public void close() {
        try {
            this.archive.close();
        } catch (IOException | RuntimeException exception) {
            logger.warn("{} : Cannot close archive #[{}]", context, this);
        }
    }

    @Override
    public boolean hasNext() {
        return entries.hasMoreElements();
    }

    @Override
    public ArchiveEntry<B, C> next() {
        if (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            return this.decode(entry).map(k -> new ArchiveEntry<>(k, entry, this)).orElseGet(() -> {
                this.skip(entry.getName());
                return this.next();
            });
        }
        return null;
    }

    @Override
    public List<FluxIndex<B>> getIndexes() {
        return indexes;
    }

}
