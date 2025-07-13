package io.github.up2jakarta.job;

import io.github.up2jakarta.job.flux.FluxLoader;
import io.github.up2jakarta.job.zip.ArchiveReader;
import io.github.up2jakarta.job.zip.ArchiveWalker;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("unused")
public abstract class MultipleReader<T, R extends ArchiveWalker<?, ?>> implements ItemReader<T> {

    private final List<R> resources = new LinkedList<>();
    private final ArchiveReader<? extends T, R> delegate;
    private final FluxLoader<?, ?, ? extends R> loader;
    private final Queue<R> queue = new ConcurrentLinkedQueue<>();

    private boolean empty = false;

    protected MultipleReader(FluxLoader<?, ?, ? extends R> loader, ArchiveReader<? extends T, R> delegate) {
        this.delegate = delegate;
        this.loader = loader;
    }

    public final void open(StepExecution execution) throws ItemStreamException {
        final List<? extends R> resources = loader.load(execution, delegate.getLogger());
        if (resources.isEmpty()) {
            empty = true;
        } else {
            this.queue.addAll(resources);
            this.resources.addAll(resources);
            this.delegate.open(queue.remove());
        }
    }

    @Override
    public T read() throws Exception {
        if (empty) {
            return null;
        }
        T item = delegate.read();
        while (item == null) {
            delegate.close();
            if (queue.isEmpty()) {
                empty = true;
                return null;
            }
            delegate.open(queue.remove());
            item = delegate.read();
        }
        return item;
    }

    public final void close(StepExecution execution) {
        for (final R resource : resources) {
            resource.clean(execution);
        }
    }

    public final int size() {
        return resources.size();
    }

}
