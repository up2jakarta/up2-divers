package io.github.up2jakarta.test.impl.dto;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import static io.github.up2jakarta.lov.SeverityType.WARNING;
import static io.github.up2jakarta.test.impl.TermType.*;

@Valid
@Error(value = "CSV-C05", level = WARNING)
@BusinessType(D005)
public class Note extends Parsable {

    @Position(0)
    @NotEmpty
    @BusinessType(A001)
    private final String key;

    @Position(1)
    @NotEmpty
    @BusinessType(A004)
    private final String content;

    public Note(String key, String content) {
        this.key = key;
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public String getContent() {
        return content;
    }

}
