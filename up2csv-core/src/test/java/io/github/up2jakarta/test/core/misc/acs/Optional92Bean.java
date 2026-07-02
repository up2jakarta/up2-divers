package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;
import jakarta.validation.Valid;

import java.util.Optional;

import static jakarta.persistence.AccessType.PROPERTY;

@Valid
@Access(PROPERTY)
public final class Optional92Bean extends Optional92Super<String> {
    public Optional<?> getCode() {
        return code;
    }

    public void setCode(Optional<String> code) {
        this.code = code;
    }
}

abstract class Optional92Super<T> implements Segment {
    @Position(0)
    protected Optional<T> code;
}