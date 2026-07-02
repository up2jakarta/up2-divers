package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.lov.core.Identifiable;
import jakarta.persistence.Access;

import java.util.Optional;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public final class Optional3Bean extends Optional3Super<Integer> {
}

abstract class Optional3Super<I extends Number> implements Identifiable<Optional<I>>, Segment {

    @Position(0)
    @Up2Number
    private Optional<I> key = Optional.empty();

    @Position(1)
    private Optional<String> content = Optional.empty();

    @Override
    public Optional<I> getKey() {
        return key;
    }

    public void setKey(Optional<I> key) {
        this.key = key;
    }

    public Optional<String> getContent() {
        return content;
    }

    public void setContent(Optional<String> content) {
        this.content = content;
    }
}
