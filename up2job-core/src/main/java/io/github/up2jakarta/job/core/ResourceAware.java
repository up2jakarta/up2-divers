package io.github.up2jakarta.job.core;

import io.github.up2jakarta.job.ctx.ContextAware;
import org.slf4j.Logger;

import java.io.File;

@SuppressWarnings("unused")
public interface ResourceAware {

    String XML_EXTENSION = ".xml";
    String CSV_EXTENSION = ".csv";
    String ZIP_EXTENSION = ".zip";
    String CSV_MIME = "text/csv";
    String ZIP_MIME = "application/zip";

    static void delete(File file, Logger logger, ContextAware context) {
        if (file.exists() && file.canWrite() && !file.delete() && file.exists()) {
            logger.warn("{} : Cannot delete File[{}]", context, file);
        }
    }

    default File getResource() {
        throw new UnsupportedOperationException("The resource [" + getClass().getSimpleName() + "] is multiple");
    }

}
