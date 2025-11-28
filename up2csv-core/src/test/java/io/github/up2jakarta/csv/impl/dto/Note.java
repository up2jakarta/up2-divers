package io.github.up2jakarta.csv.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import static io.github.up2jakarta.lov.SeverityType.WARNING;

@Valid
@Error(value = "CSV-C04", level = WARNING)
@InputType(GroupType.D007)
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
