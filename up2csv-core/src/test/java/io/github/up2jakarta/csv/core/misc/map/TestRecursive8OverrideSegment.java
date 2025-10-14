package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.data.Segment;

public class TestRecursive8OverrideSegment implements Segment {

    @Fragment(0)
    private TestRecursive7OverrideSegment fragment2;

    @Position(2)
    private String description;

    @Fragment(3)
    @FragmentOverride(path = {"recursive"})
    private RecursiveSegment fragment1;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecursiveSegment getFragment1() {
        return fragment1;
    }

    public void setFragment1(RecursiveSegment fragment1) {
        this.fragment1 = fragment1;
    }

    public TestRecursive7OverrideSegment getFragment2() {
        return fragment2;
    }

    public void setFragment2(TestRecursive7OverrideSegment fragment2) {
        this.fragment2 = fragment2;
    }

    @PositionOverride(path = {"id"})
    public static class RecursiveSegment extends TestRecursive1Segment {

        @Position(0)
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
