package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public class CountryBean implements Segment {

    @Position(1)
    private String code;

    @Position(2)
    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
