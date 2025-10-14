package io.github.up2jakarta.csv.impl.dto;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.impl.Parsable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Valid
@SuppressWarnings("unused")
public class Note extends Parsable {

    @Position(0)
    @NotEmpty
    private String key;

    @Position(1)
    @NotEmpty
    private String content;

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
