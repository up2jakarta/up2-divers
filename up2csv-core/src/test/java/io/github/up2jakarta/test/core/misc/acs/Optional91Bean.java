package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import java.util.Optional;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public final class Optional91Bean extends Optional91Super<String> {

}

abstract class Optional91Super<T> implements Segment {

    @Position(0)
    private Optional<T> code;

    public Optional<T> getCode() {
        return code;
    }

    public void setCode(Optional<T> code) {
        this.code = code;
    }
}