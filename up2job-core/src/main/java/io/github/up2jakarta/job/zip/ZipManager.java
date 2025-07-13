package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public final class ZipManager<C extends BusinessContext> implements ArchiveManager<C> {

    private final ArchiveTranslator translator;
    private final Logger logger;
    private final C context;

    public ZipManager(C context, Logger logger, ArchiveTranslator translator) {
        this.translator = translator;
        this.context = context;
        this.logger = logger;
    }

    @Override
    public ZipArchive<C> open(final File resource) {
        try {
            final ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(resource));
            return new ZipArchive<>(logger, this, resource, stream, translator);
        } catch (IOException | RuntimeException ex) {
            throw translator.openWriteFailed(resource, ex);
        }
    }

    @Override
    public void close(ZipArchive<C> zip, ZipOutputStream stream) {
        try {
            stream.close();
        } catch (IOException | RuntimeException ex) {
            throw translator.closeWriteFailed(zip, ex);
        }
    }

    @Override
    public C getContext() {
        return context;
    }

}
