package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public class Inner3Segment implements Segment {

    @Position(0)
    private String id;

    @Fragment(0)
    private InnerFragment fragment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InnerFragment getFragment() {
        return fragment;
    }

    public void setFragment(InnerFragment fragment) {
        this.fragment = fragment;
    }

    public static class InnerFragment implements Segment {

        @Position(1)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
