package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.persistence.Access;

import java.util.Optional;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public final class Optional1Bean implements Segment {

    @Position(0)
    @Up2Number
    private int id;

    @Position(1)
    private Optional<String> content = Optional.empty();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Optional<String> getContent() {
        return content;
    }

    public void setContent(Optional<String> content) {
        this.content = content;
    }
}
