package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Access(AccessType.FIELD)
public class Final6Segment implements Segment {

    @Position(0)
    public String code;

    @Fragment(1)
    public SFragment fragment;

    public class SFragment implements Segment {

        @Position(0)
        public final String value;

        @Creator
        public SFragment(String value) {
            this.value = value;
        }

        public final Final6Segment getParent() {
            return Final6Segment.this;
        }
    }
}
