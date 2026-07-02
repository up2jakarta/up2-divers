package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

import static io.github.up2jakarta.job.core.ResourceAware.ZIP_EXTENSION;

public final class Zip1Manager<C extends BusinessContext> implements ArchiveManager<C> {

    private final ArchiveTranslator translator;
    private final Logger logger;
    private final C context;

    public Zip1Manager(C context, Logger logger, ArchiveTranslator translator) {
        this.translator = translator;
        this.context = context;
        this.logger = logger;
    }

    @Override
    public Zip1Archive<C> open(final String path) {
        final File resource = new File(path + ZIP_EXTENSION);
        try {
            return new Zip1Archive<>(logger, context, resource, translator);
        } catch (IOException | RuntimeException ex) {
            throw translator.openWriteFailed(resource, ex);
        }
    }

    @Override
    public C getContext() {
        return context;
    }

}
