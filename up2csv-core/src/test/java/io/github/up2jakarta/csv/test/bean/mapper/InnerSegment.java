package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.core.Segment;

@SuppressWarnings("ALL")
public class InnerSegment implements Segment {

    @Position(0)
    private String id;

    @Fragment(0)
    private InnerFragment inner;

    public void setId(String id) {
        this.id = id;
    }

    public void setInner(InnerFragment inner) {
        this.inner = inner;
    }

    public class InnerFragment implements Segment {

        @Position(1)
        private String name;

        public void setName(String name) {
            this.name = name;
        }
    }

}
