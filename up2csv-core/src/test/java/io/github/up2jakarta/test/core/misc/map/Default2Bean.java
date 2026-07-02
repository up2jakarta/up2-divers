package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.test.core.misc.map.Default1Bean.InnerBean;

public class Default2Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

    @Fragment(value = 1, nullable = true)
    private InnerBean reference;

    public String getCode() {
        return code;
    }

    public InnerBean getReference() {
        return reference;
    }

}
