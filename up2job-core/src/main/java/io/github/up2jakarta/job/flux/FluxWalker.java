package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.zip.ArchiveEntry;
import io.github.up2jakarta.job.zip.ArchiveTranslator;
import io.github.up2jakarta.job.zip.ArchiveWalker;
import io.github.up2jakarta.job.zip.EntrySkipper;
import org.springframework.batch.core.step.StepExecution;

import java.util.List;

import static java.util.function.Predicate.not;

@SuppressWarnings("unused")
public class FluxWalker<B extends BusinessType<B>, C extends BusinessContext> implements ArchiveWalker<B, C>, EntrySkipper {

    private final String name;
    private final FluxIterator<B, C> delegate;
    private final FluxValidator<B, C> validator;
    private final List<FluxIndex<B>> indexes;
    private final ArchiveTranslator translator;

    public FluxWalker(String name, FluxIterator<B, C> delegate, FluxValidator<B, C> validator, ArchiveTranslator translator) {
        this.indexes = List.of(delegate.getIndex(), validator.getIndex());
        this.translator = translator;
        this.validator = validator;
        this.delegate = delegate;
        this.name = name;
    }

    @Override
    public boolean hasNext() {
        return delegate.hasNext();
    }

    @Override
    public ArchiveEntry<B, C> next() {
        final ArchiveEntry<B, C> firstItem = delegate.next();
        if (firstItem != null) {
            final BusinessId otherId = validator.decode(firstItem.getName());
            if (!firstItem.getId().equals(otherId)) {
                firstItem.clean();
                return this.next();
            }
            return new ArchiveEntry<>(firstItem, this);
        }
        return null;
    }

    @Override
    public long count() {
        final long firstCount = delegate.count();
        final long otherCount = validator.count();
        return Math.min(firstCount, otherCount);
    }

    @Override
    public void close() {
        validator.skipIf(not(delegate::exists));
        delegate.close();
        validator.close();
    }

    @Override
    public void clean(StepExecution execution) {
        delegate.clean(execution);
        validator.clean(execution);
        if (!this.isClean()) {
            execution.addFailureException(translator.cleanFailed(this));
        }
        if (validator.count() != delegate.count()) {
            execution.addFailureException(translator.validateFailed(this));
        }
        delegate.clean();
        validator.clean();
    }

    @Override
    public List<FluxIndex<B>> getIndexes() {
        return indexes;
    }

    @Override
    public void skip(String file) {
        delegate.skip(file);
        validator.skip(file);
    }

    @Override
    public boolean isSkipped(String file) {
        return delegate.isSkipped(file) && validator.isSkipped(file);
    }

    @Override
    public boolean isClean() {
        return delegate.isClean() && validator.isClean();
    }

    @Override
    public boolean isValid() {
        return delegate.isValid() && validator.isValid();
    }

    @Override
    public void clean() {
        delegate.clean();
        validator.clean();
    }

    @Override
    public boolean isCleaned() {
        return delegate.isCleaned() && validator.isCleaned();
    }

    @Override
    public final String toString() {
        return name;
    }

}
