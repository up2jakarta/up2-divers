package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.cfg.Up2Token;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
@SuppressWarnings("unused")
public class BIdMultipleSegment implements Segment {

    @Position(0)
    @BusinessId
    @Up2Token
    public final String key1;

    @Position(1)
    @BusinessId
    public final @Up2Number Integer key2;

    public BIdMultipleSegment(String key1, Integer key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    public final String getKey1() {
        return key1;
    }

    public final Integer getKey2() {
        return key2;
    }

}
