package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;

public class NoOrderBean implements Segment {

    @Position(0)
    private String id;

    @Position(2)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
