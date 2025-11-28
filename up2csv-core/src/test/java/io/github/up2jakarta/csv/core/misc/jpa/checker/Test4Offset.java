package io.github.up2jakarta.csv.core.misc.jpa.checker;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.data.Segment;

@Up2EnableJPA
public class Test4Offset implements Segment {

    public final @Position(0) String key1;
    public final @Position(1) String key2;
    public final @Fragment(2) SFragment fragment;

    public Test4Offset(String key1, String key2, SFragment fragment) {
        this.key1 = key1;
        this.key2 = key2;
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
