package io.github.up2jakarta.job.core;

import java.io.File;

@SuppressWarnings("unused")
public class LocalFile implements ResourceAware {

    protected final File resource;
    protected final String name;

    public LocalFile(String name, File resource) {
        this.name = name;
        this.resource = resource;
    }

    public LocalFile(File resource) {
        this.name = resource.getName();
        this.resource = resource;
    }

    protected static String decodeName(String path) {
        final String name = new File(path).getName();
        final int index = name.indexOf("_"); // jobId
        return name.substring(index + 1);
    }

    public static String decodeName(Archive archive) {
        return decodeName(archive.getArchivePath());
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
