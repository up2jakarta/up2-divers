package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

public class Inner6Segment implements Segment {

    @Position(0)
    public final String code;

    @Fragment(1)
    public final SFragment fragment;

    @Creator
    public Inner6Segment(String code, SFragment fragment) {
        this.code = code;
        this.fragment = fragment;
    }

    public class SFragment implements Segment {

        @Position(0)
        public final String value;

        @Creator
        public SFragment(String value) {
            this.value = value;
        }

        @Override
        public final String toString() {
            return Inner6Segment.this.toString();
        }
    }

}
