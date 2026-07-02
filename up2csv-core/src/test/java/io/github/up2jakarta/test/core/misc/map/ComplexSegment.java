package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Trim;

public final class ComplexSegment implements Segment {

    @Position(0)
    @Up2Trim
    private String code;

    @Fragment(value = 0, nullable = true)
    private CountryBean country;

    public String getCode() {
        return code;
    }

    public CountryBean getCountry() {
        return country;
    }

}
