package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.data.Segment;

public class Inner2Segment implements Segment {

    @Fragment(0)
    private InnerFragment fragment;

    public class InnerFragment implements Segment {

        @Fragment(0)
        private Inner1Segment.InnerFragment fragment;

        public Inner1Segment.InnerFragment getFragment() {
            return fragment;
        }

        public void setFragment(Inner1Segment.InnerFragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public String toString() {
            return Inner2Segment.this.toString();
        }
    }

}
