package io.github.up2jakarta.csv.io.dto;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Definition;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Valid
@SuppressWarnings("unused")
public class Note implements Segment {

    @Position(0)
    @NotEmpty
    @Definition(code = "N01", value = "Key")
    private String key;

    @Position(1)
    @NotEmpty
    @Definition(code = "N02", value = "Content")
    private String content;

    public Note() {
    }

    public Note(String key, String content) {
        this.key = key;
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
