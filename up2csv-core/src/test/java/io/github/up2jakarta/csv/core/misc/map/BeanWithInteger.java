package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class BeanWithInteger implements Segment {

    @Position(0)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
