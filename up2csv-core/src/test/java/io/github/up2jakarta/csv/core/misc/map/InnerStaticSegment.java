package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class InnerStaticSegment implements Segment {

    @Position(0)
    private String id;

    @Fragment(0)
    private InnerFragment inner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InnerFragment getInner() {
        return inner;
    }

    public void setInner(InnerFragment inner) {
        this.inner = inner;
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
