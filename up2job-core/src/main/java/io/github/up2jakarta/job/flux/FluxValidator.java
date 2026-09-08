package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessId;
import io.github.up2jakarta.job.core.BusinessType;
import io.github.up2jakarta.job.core.LocalFile;
import io.github.up2jakarta.job.zip.ArchiveHandler;
import io.github.up2jakarta.job.zip.ZipWalker;
import org.slf4j.Logger;
import org.springframework.batch.core.step.StepExecution;

import java.io.IOException;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FluxValidator<B extends BusinessType<B>, C extends BusinessContext> extends ZipWalker<B, C> {

    public FluxValidator(ArchiveHandler<B, C> handler, FluxIndex<B> index, LocalFile file, Logger logger) throws IOException {
        super(handler, index, file, logger);
    }

    BusinessId decode(String fileName) {
        final ZipEntry entry = super.get(fileName);
        return super.decode(entry).orElseGet(() -> {
            this.skip(fileName);
            return null;
        });
    }

    void skipIf(Predicate<String> filter) {
        try (final ZipFile zip = new ZipFile(resource)) {
            final Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                final String entry = entries.nextElement().getName();
                if (!this.isSkipped(entry) && filter.test(entry)) {
                    this.skip(entry);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clean(StepExecution execution) {
        this.handle(execution);
    }

}
