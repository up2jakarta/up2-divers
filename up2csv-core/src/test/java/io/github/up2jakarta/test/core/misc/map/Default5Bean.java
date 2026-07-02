package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.*;

public class Default5Bean implements Segment {

    @Position(0)
    private String code;

    @PositionOverride(path = "id", value = @Position(value = 0, defaultValue = "21"))
    @PositionOverride(path = "code", value = @Position(value = 1, defaultValue = "Up2J"))
    @FragmentOverride(path = "other", value = @Fragment(value = 2, prototype = true))
    @Fragment(value = 1, prototype = true)
    private Inner1Bean bean;

    public Inner1Bean getBean() {
        return bean;
    }

    public record Inner1Bean(@Up2Number int id, String code, Inner2Bean other) implements Segment {
    }

    @PositionOverride(path = "id", value = @Position(value = 0))
    @PositionOverride(path = "code", value = @Position(value = 1, defaultValue = "Up2J-1"))
    @PositionOverride(path = "other", value = @Position(value = 2, defaultValue = "Up2J-2"))
    public record Inner2Bean(@Up2Number int id, String code, String other) implements Segment {
    }
}
