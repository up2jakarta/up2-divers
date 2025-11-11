package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

public final class BId5Bean implements Segment {

    @Position(0)
    @Up2Number
    @BusinessId
    @Access(AccessType.FIELD)
    public final Integer id;

    public BId5Bean(Integer id) {
        this.id = id;
    }

}