package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;

public class TestRecursive8Override implements Segment {

    @Fragment(0)
    private TestRecursive7Override fragment2;

    @Position(3)
    private String description;

    @Fragment(4)
    @FragmentOverride(path = {"recursive", "recursive"})
    private RecursiveSegment fragment1;

    public static class RecursiveSegment extends TestRecursive1Segment {

        @Position(2)
        private String label;

    }

}
