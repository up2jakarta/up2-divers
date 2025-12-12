package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public record Inner5Segment(@Position(0) String id, @Fragment(0) InnerFragment fragment) implements Segment {

    public class InnerFragment implements Segment {

        @Position(1)
        private String name = "Test";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public final String toString() {
            return Inner5Segment.this.toString();
        }
    }

}
