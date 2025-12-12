package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

public class Default4Bean implements Segment {

    @Position(0)
    private String code;

    @PositionOverride(path = "id", value = @Position(value = 0, defaultValue = "21"))
    @PositionOverride(path = "code", value = @Position(value = 1, defaultValue = "Up2J"))
    @Fragment(value = 1, prototype = true)
    private InnerBean bean;

    public InnerBean getBean() {
        return bean;
    }

    public record InnerBean(@Up2Number int id, String code) implements Segment {
    }
}
