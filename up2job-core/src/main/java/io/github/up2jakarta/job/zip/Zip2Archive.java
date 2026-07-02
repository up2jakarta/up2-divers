package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.SafeUtil;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.*;

public final class Zip2Archive<C extends BusinessContext> extends ZipArchive<C> {

    private static final String UC_NAME = "user:c";
    private final Path path;

    Zip2Archive(Logger log, C context, Path path, File resource, ArchiveTranslator translator) throws IOException {
        super(context, log, resource, translator);
        this.path = createDirectory(path);
    }

    @Override
    public void write(String name, String key, byte[] data) {
        final Path path = this.path.resolve(name);
        try {
            Files.write(path, data);
            final ByteBuffer comment = UTF_8.encode(key);
            setAttribute(path, UC_NAME, comment);
        } catch (RuntimeException | IOException ex) {
            throw translator.writeEntryFailed(this, ex);
        }
    }

    @Override
    public void close() {
        try (final DirectoryStream<Path> ds = newDirectoryStream(path)) {
            for (final Path item : ds) {
                try {
                    final String name = path.relativize(item).toString();
                    final String comment = new String((byte[]) getAttribute(item, UC_NAME), UTF_8);
                    final ZipEntry entry = new ZipEntry(name);
                    entry.setComment(comment);
                    stream.putNextEntry(entry);
                } catch (RuntimeException | IOException ex) {
                    throw translator.putEntryFailed(this, ex);
                }
                try {
                    copy(item, stream);
                } catch (RuntimeException | IOException ex) {
                    throw translator.writeEntryFailed(this, ex);
                } finally {
                    SafeUtil.safe(stream::closeEntry);
                    SafeUtil.safe(stream::flush);
                    SafeUtil.safe(() -> delete(item));
                }
            }
        } catch (IOException | RuntimeException ex) {
            throw translator.openWriteFailed(resource, ex);
        } finally {
            SafeUtil.safe(stream::close);
            SafeUtil.safe(() -> delete(path));
        }
    }

    @Override
    public void clean(StepExecution execution) {
        if (!this.isClean()) {
            this.clean(path, execution, false);
        }
        this.close();
    }

}
