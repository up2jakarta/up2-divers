package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.SafeUtil;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class ZipArchive<C extends BusinessContext> extends ZipResource<C> {

    private final ZipManager<C> manager;
    private final ZipOutputStream stream;

    ZipArchive(Logger log, ZipManager<C> zip, File src, ZipOutputStream zos, ArchiveTranslator translator) {
        super(zip.getContext(), log, src.getName(), src, translator);
        this.manager = zip;
        this.stream = zos;
    }

    public void write(ZipEntry entry, byte[] data) {
        try {
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
        manager.close(this, stream);
    }

    @Override
    public void clean(StepExecution execution) {
        this.close();
        if (!this.isClean()) {
            this.clean(execution, false);
        }
    }

}
