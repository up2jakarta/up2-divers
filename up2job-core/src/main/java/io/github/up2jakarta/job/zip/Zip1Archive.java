package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.SafeUtil;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

public final class Zip1Archive<C extends BusinessContext> extends ZipArchive<C> {

    Zip1Archive(Logger log, C context, File resource, ArchiveTranslator translator) throws IOException {
        super(context, log, resource, translator);
    }

    @Override
    public void write(String name, String key, byte[] data) {
        try {
            final ZipEntry entry = new ZipEntry(name);
            entry.setComment(key);
            stream.putNextEntry(entry);
        } catch (RuntimeException | IOException ex) {
            throw translator.putEntryFailed(this, ex);
        }
        try {
            stream.write(data, 0, data.length);
        } catch (RuntimeException | IOException ex) {
            throw translator.writeEntryFailed(this, ex);
        } finally {
            SafeUtil.safe(stream::closeEntry);
            SafeUtil.safe(stream::flush);
        }
    }

    @Override
    public void close() {
        try {
            stream.close();
        } catch (IOException | RuntimeException ex) {
            throw translator.closeWriteFailed(this, ex);
        }
    }

    @Override
    public void clean(StepExecution execution) {
        this.close();
        if (!this.isClean()) {
            this.clean(resource, execution, false);
        }
    }

}
