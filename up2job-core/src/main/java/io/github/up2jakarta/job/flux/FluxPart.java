package io.github.up2jakarta.job.flux;

import io.github.up2jakarta.job.core.Archive;
import io.github.up2jakarta.job.core.LocalFile;
import io.github.up2jakarta.job.zip.ArchiveResource;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("unused")
public class FluxPart implements Serializable {

    public static final FluxPart NULL = new FluxPart(null, null, "null");

    private final Long id;
    private final File file;
    private final String name;

    private FluxPart(Long id, File file, String name) {
        this.id = id;
        this.file = file;
        this.name = name;
    }

    public FluxPart(Long id, ArchiveResource archive) {
        this(id, archive.getResource(), archive.toString());
    }

    public FluxPart(Long id, LocalFile file) {
        this(id, file.getResource(), file.toString());
    }

    public static String format(Long id, String name) {
        return "#[" + id + "/" + name + "]";
    }

    public static String format(Archive archive) {
        return format(archive.getId(), LocalFile.decodeName(archive));
    }

    public Long getId() {
        return id;
    }

    public File getFile() {
        return file;
    }

    public FluxPart clone(LocalFile file) {
        if (this.file.equals(file.getResource())) {
            return this;
        }
        return new FluxPart(id, file);
    }

    @Override
    public String toString() {
        return format(id, name);
    }

}
