package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public abstract class ZipArchive<C extends BusinessContext> extends ZipResource<C> {
    protected final ZipOutputStream stream;

    protected ZipArchive(C context, Logger logger, File resource, ArchiveTranslator translator) throws IOException {
        super(context, logger, resource, translator);
        this.stream = new ZipOutputStream(new FileOutputStream(resource));
    }

    public abstract void write(String name, String key, byte[] data);

}
