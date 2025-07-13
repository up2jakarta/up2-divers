package io.github.up2jakarta.job.zip;

import io.github.up2jakarta.job.core.BusinessContext;
import io.github.up2jakarta.job.core.BusinessTranslator;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public abstract class ZipResource<T extends BusinessContext> extends ZipCleaner<T> {

    private final String name;

    protected ZipResource(T context, Logger logger, String name, File resource, ArchiveTranslator translator) {
        super(context, logger, resource, translator);
        this.name = name;
    }

    static boolean isValid(ZipResource<?> zip, BusinessTranslator interceptor) {
        try (final ZipFile zipFile = new ZipFile(zip.getResource())) {
            return zipFile.entries().hasMoreElements();
        } catch (IOException | RuntimeException ex) {
            throw interceptor.translate(zip, ex);
        }
    }

    static long count(ZipResource<?> zip, BusinessTranslator interceptor) {
        try (final ZipFile zipFile = new ZipFile(zip.getResource())) {
            return zipFile.stream().count();
        } catch (IOException | RuntimeException ex) {
            throw interceptor.translate(zip, ex);
        }
    }

    @Override
    public final long count() {
        return count(this, translator::countFailed);
    }

    @Override
    public final boolean isValid() {
        return isValid(this, translator::validateFailed);
    }

    @Override
    public final File getResource() {
        return resource;
    }

    @Override
    public final String toString() {
        return name;
    }

}
