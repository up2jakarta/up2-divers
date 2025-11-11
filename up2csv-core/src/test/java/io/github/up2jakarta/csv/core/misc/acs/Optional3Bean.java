package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Identifiable;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Optional;

public final class Optional3Bean extends Generic3Bean<Integer> {
}

abstract class Generic3Bean<I extends Number> implements Identifiable<Optional<I>>, Segment {
    @Position(0)
    @Up2Number
    private Optional<I> key;

    @Position(1)
    private Optional<String> content;

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
