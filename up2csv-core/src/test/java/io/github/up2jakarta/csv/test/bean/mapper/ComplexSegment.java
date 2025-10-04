package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Trim;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public final class ComplexSegment implements Segment {

    @Position(0)
    @Up2Trim
    private String code;

    @Fragment(value = 0, nullable = true)
    private CountryBean country;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CountryBean getCountry() {
        return country;
    }

    public void setCountry(CountryBean country) {
        this.country = country;
    }
}
