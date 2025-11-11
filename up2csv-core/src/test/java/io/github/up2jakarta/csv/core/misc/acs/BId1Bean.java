package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Access(AccessType.FIELD)
public final class BId1Bean implements Segment {

    @Fragment(0)
    public AFragment fragment = new AFragment();

    public static final class AFragment implements Segment {
        @Position(0)
        @Up2Number
        @BusinessId
        public Integer id;
    }
}