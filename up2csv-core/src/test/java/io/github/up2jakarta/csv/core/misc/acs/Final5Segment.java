package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public class Final5Segment implements Segment {

    @Position(0)
    public String code;

    @Fragment(1)
    public SFragment fragment;

    public static class SFragment implements Segment {

        @Position(0)
        public final String value1;
        @Position(1)
        public final String value2;

        public final int rate1;
        public final long rate2;

        @Creator
        public SFragment(String value2, int rate1, String value1, long rate2) {
            this.value1 = value1;
            this.value2 = value2;
            this.rate1 = rate1;
            this.rate2 = rate2;
        }
    }
}
