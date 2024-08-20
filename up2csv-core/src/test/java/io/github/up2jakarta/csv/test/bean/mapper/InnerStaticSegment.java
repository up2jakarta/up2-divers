package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;

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
