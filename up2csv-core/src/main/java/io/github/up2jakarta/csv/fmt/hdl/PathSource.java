package io.github.up2jakarta.csv.fmt.hdl;

import io.github.up2jakarta.csv.api.hdl.ISourceEntity;

import java.nio.file.Path;

/**
 * Simple implementation of record-source for {@link io.github.up2jakarta.csv.core.ModeType#FULL}.
 */
public class PathSource implements ISourceEntity<String> {

    private final String key;

    private final Path path;

    public PathSource(String key, Path path) {
        this.key = key;
        this.path = path;
    }

    @Override
    public String getKey() {
        return key;
    }

    public Path getPath() {
        return path;
    }

}
