package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.extension.Segment;

@SuppressWarnings("unused")
public class BeanWithInteger implements Segment {

    @Position(0)
    private int id;

    public int getId() {
        return id;
    }
}
