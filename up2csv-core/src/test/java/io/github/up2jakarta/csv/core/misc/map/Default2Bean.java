package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.core.misc.map.Default1Bean.InnerBean;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class Default2Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

    @Fragment(value = 1, nullable = true)
    private InnerBean reference;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InnerBean getReference() {
        return reference;
    }

    public void setReference(InnerBean reference) {
        this.reference = reference;
    }
}
