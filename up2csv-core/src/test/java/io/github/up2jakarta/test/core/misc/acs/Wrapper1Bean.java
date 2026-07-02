package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Wrapper1Bean extends WrapperTest<Integer> {

    public Wrapper1Bean(Integer key) {
        super(key);
    }
}

class WrapperTest<I extends Number> implements Segment {

    @Position(0)
    @Up2Number
    private final Wrapper<I> key;

    public WrapperTest(I key) {
        this.key = new Wrapper<>(key);
    }

    public Wrapper<I> getKey() {
        return key;
    }
}
