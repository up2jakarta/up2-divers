package io.github.up2jakarta.test.core.misc.jpa.checker;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.data.Segment;

@Up2EnableJPA
public class Test3Offset implements Segment {

    public final @Position(0) String key;
    public final @Fragment(1) SFragment fragment;

    public Test3Offset(String key, SFragment fragment) {
        this.key = key;
        this.fragment = fragment;
    }

    public static class SFragment implements Segment {

        public final @Position(0) String code;
        public final @Position(2) String value;
        public final @Position(4) String comment;

        public SFragment(String code, String value, String comment) {
            this.code = code;
            this.value = value;
            this.comment = comment;
        }
    }

}
