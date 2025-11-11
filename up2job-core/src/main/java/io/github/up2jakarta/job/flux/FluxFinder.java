package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.*;
import io.github.up2jakarta.job.ctx.ContextHolder;
import io.github.up2jakarta.job.zip.ArchiveHandler;
import io.github.up2jakarta.job.zip.ArchiveWalker;
import io.github.up2jakarta.job.zip.ZipWalker;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class FluxFinder
        <B extends BusinessType<B>, I extends BusinessContext, C extends I, T extends ArchiveWalker<B, C>>
        implements FluxLoader<B, C, T>, FluxIndexer<B, C>, FluxOperator<B, I>, ContextHolder<C> {

    protected final B firstType;
    protected final B otherType;

    private final List<T> resources = new LinkedList<>();

    protected FluxFinder(B firstType, B otherType) {
        this.firstType = firstType;
        this.otherType = otherType;
    }

    protected final void register(T resource) {
        resources.add(resource);
    }

    protected final void clean(StepExecution step) {
        resources.forEach(r -> r.clean(step));
    }

    protected final ZipWalker<B, C> newWalker(B type, long id, LocalFile file, Logger logger) throws IOException {
        return new ZipWalker<>(getHandler(), new FluxIndex<>(id, type), file, logger);
    }

    protected final FluxIterator<B, C> newIterator(B type, long id, LocalFile file, Logger logger) throws IOException {
        return new FluxIterator<>(getHandler(), new FluxIndex<>(id, type), file, logger);
    }

    protected final FluxValidator<B, C> newValidator(B type, long id, LocalFile file, Logger logger) throws IOException {
        return new FluxValidator<>(getHandler(), new FluxIndex<>(id, type), file, logger);
    }

    protected final void warning(Logger logger, String action, Archive output, String source) {
        logger.warn("{} : {} flux entry {} from {}", this.getContext(), action, FluxPart.format(output), source);
    }

    @Override
    public final C getContext() {
        return getHandler().getContext();
    }

    @Override
    public final B getFirstType() {
        return firstType;
    }

    @Override
    public final B getOtherType() {
        return otherType;
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public final List<T> load(StepExecution step, Logger log) {
        final Map<Long, ? extends Archive> firstArchives = loadArchives(firstType);
        final Map<Long, ? extends Archive> othersArchives = loadArchives(otherType);
        this.loadContext(step, log, (t, d) -> (t == firstType) ? firstArchives.remove(d) : othersArchives.remove(d));
        firstArchives.forEach((firstId, firstArchive) -> {
            final Archive otherArchive = othersArchives.entrySet().stream()
                    .filter(e -> matches(firstArchive, firstType, e.getValue(), otherType))
                    .findAny()
                    .stream()
                    .peek(othersArchives.entrySet()::remove)
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(null);
            this.loadFirst(step, log, firstId, firstArchive, otherArchive);
        });
        othersArchives.forEach((f1Id, f1Output) -> this.loadOther(step, log, f1Id, f1Output));
        return List.copyOf(resources);
    }

    protected abstract ArchiveHandler<B, C> getHandler();

    protected abstract Map<Long, ? extends Archive> loadArchives(B type);

    protected abstract void loadOther(StepExecution step, Logger log, long id, Archive archive);

    protected abstract void loadContext(StepExecution step, Logger log, ArchiveFinder<B> finder);

    protected abstract boolean matches(Archive firstArchive, B firstType, Archive otherArchive, B otherType);

    protected abstract void loadFirst(StepExecution step, Logger log, long id, Archive archive, Archive other);

}
