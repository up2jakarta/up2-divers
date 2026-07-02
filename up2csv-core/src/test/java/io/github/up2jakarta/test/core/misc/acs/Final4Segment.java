package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

public class Final4Segment implements Segment {

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

        @Override
        public final String toString() {
            return Final4Segment.this.toString();
        }
    }

}
