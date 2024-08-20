package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Default;
import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.extension.Segment;

@SuppressWarnings("unused")
public class DefaultBean implements Segment {

    @Position(0)
    @Up2Number
    @Up2Default("invalid")
    private Integer key;

    public void setKey(Integer key) {
        this.key = key;
    }
}
