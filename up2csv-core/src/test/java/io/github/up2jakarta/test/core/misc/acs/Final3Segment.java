package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Optional;

public class Final3Segment implements Segment {

    @Position(0)
    public String code;

    @Fragment(1)
    public SFragment<String, Integer> fragment;

    public static class SFragment<T extends Comparable<T>, N extends Number> implements Segment {

        @Position(0)
        public final Optional<T> value;

        @Position(1)
        @Up2Number
        public final Optional<N> rate;

        @Creator
        public SFragment(Optional<T> value, Optional<N> rate) {
            this.value = value;
            this.rate = rate;
        }
    }

}
