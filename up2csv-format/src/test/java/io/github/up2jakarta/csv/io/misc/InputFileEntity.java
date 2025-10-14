package io.github.up2jakarta.csv.io.misc;

import io.github.up2jakarta.csv.api.hdl.ISourceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.nio.file.Path;

@Entity
@Table(name = "TB_INPUT_FILES")
@SuppressWarnings("unused")
public class InputFileEntity implements ISourceEntity<Long> {

    @Id
    @Column(name = "FILE_ID", nullable = false)
    private Long key;

    @Column(name = "FILE_HOST", length = 31)
    private String host;

    @Column(name = "FILE_PATH", length = 511)
    private String path;

    public InputFileEntity(Path file) {
        this.key = (long) file.hashCode();
        this.host = "local";
        this.path = file.toString();
    }

    @Override
    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
