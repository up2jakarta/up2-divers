package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.ctx.ContextHolder;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class ZipCleaner<C extends BusinessContext> implements ArchiveResource, EntrySkipper, ContextHolder<C> {

    static final Map<String, String> ZIP = Map.of("create", "false", "encoding", UTF_8.name());

    protected final C context;
    protected final Logger logger;
    protected final File resource;
    protected final ArchiveTranslator translator;
    private final List<String> skips = new LinkedList<>();

    public ZipCleaner(C context, Logger logger, File resource, ArchiveTranslator translator) {
        this.translator = translator;
        this.resource = resource;
        this.context = context;
        this.logger = logger;
    }

    protected final void clean(StepExecution execution, boolean failure) {
        logger.info("{} : Cleaning ({}) ZIP entries from #[{}]", context, skips.size(), this);
        try (final FileSystem zip = FileSystems.newFileSystem(resource.toPath(), ZIP)) {
            for (final ListIterator<String> it = skips.listIterator(); it.hasNext(); ) {
                final String xml = it.next();
                try {
                    Files.delete(zip.getPath(xml));
                    it.remove();
                    logger.debug("{} : deleting ZIP entry {} from #[{}]", context, xml, this);
                } catch (NoSuchFileException ignore) {
                    it.remove();
                } catch (RuntimeException | IOException cause) {
                    logger.warn("{} : failed to delete ZIP entry {} from #[{}]", context, xml, this);
                }
            }
            if (failure && !skips.isEmpty()) {
                execution.addFailureException(translator.cleanFailed(this));
            }
        } catch (IOException | RuntimeException cause) {
            execution.addFailureException(translator.cleanFailed(this, cause));
        }
    }

    protected final void ignore() {
        skips.clear();
    }

    @Override
    public final boolean isClean() {
        return skips.isEmpty();
    }

    @Override
    public final void skip(String file) {
        skips.add(file);
    }

    @Override
    public boolean isSkipped(String file) {
        return skips.contains(file);
    }

    @Override
    public C getContext() {
        return context;
    }

}
