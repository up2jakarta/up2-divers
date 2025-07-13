package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessType;
import org.slf4j.Logger;

@SuppressWarnings("unused")
public class ZipReader<B extends BusinessType<B>, C extends BusinessContext> implements ArchiveReader<Entry<B, C>, ArchiveWalker<B, C>> {

    private final BusinessContext context;
    private final Logger logger;
    private ArchiveWalker<B, C> walker;

    public ZipReader(BusinessContext context, Logger logger) {
        this.context = context;
        this.logger = logger;
    }

    @Override
    public void open(ArchiveWalker<B, C> resource) {
        logger.debug("{} : Opening output archive {}", context, resource);
        this.walker = resource;
    }

    @Override
    public Entry<B, C> read() {
        if (walker != null) {
            return walker.next();
        }
        return null;
    }

    @Override
    public void close() {
        if (walker != null) {
            logger.debug("{} : Closing output archive {}", context, walker);
            this.walker.close();
            this.walker = null;
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
