package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Generic3Bean extends TUGeneric<Integer> {

    public Generic3Bean(Integer key) {
        super(key);
    }
}

class TUGeneric<I extends Number> implements Segment {

    @Position(0)
    @Up2Number
    private final I key;

    public TUGeneric(I key) {
        this.key = key;
    }

    public I getKey() {
        return key;
    }
}
