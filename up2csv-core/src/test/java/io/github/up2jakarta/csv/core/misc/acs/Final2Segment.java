package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Creator;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.constraints.NotBlank;

public class Final2Segment implements Segment {

    @Position(0)
    public String code;

    @Fragment(1)
    public SFragment fragment;

    public static class SFragment implements Segment {

        @Position(0)
        @NotBlank
        public final String value;

        @Creator
        public SFragment(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
