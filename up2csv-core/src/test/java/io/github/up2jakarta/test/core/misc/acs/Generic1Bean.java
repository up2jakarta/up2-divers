package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.persistence.Access;

import java.util.Optional;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Generic1Bean extends GenericTest<Integer> {

    public Generic1Bean(Optional<Integer> key) {
        super(key);
    }
}

class GenericTest<I extends Number> implements Segment {

    @Position(0)
    @Up2Number
    private final Optional<I> key;

    public GenericTest(Optional<I> key) {
        this.key = key;
    }

    public Optional<I> getKey() {
        return key;
    }
}
