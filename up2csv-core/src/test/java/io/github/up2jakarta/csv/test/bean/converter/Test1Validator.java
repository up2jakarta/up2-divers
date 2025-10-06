package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.valid.Up2NotEmpty;
import io.github.up2jakarta.csv.test.valid.Up2Warn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Valid
@SuppressWarnings("unused")
public class Test1Validator implements Segment {

    @Position(0)
    @Size(max = 1)
    private String aDefault;

    @Position(1)
    @Up2Number
    @Max(value = 100, payload = Warning.class)
    private Integer aWarning;

    @Position(2)
    @NotEmpty(payload = Warning.class)
    private String anError;

    @Position(3)
    @Up2Number
    @NotNull(payload = Warning.class)
    private Integer aFatal;

    @Position(4)
    @Up2Number
    @Positive(payload = Up2Warn.class)
    private Integer anOther;

    @Position(5)
    @Up2NotEmpty
    private String andMore;

    @Position(6)
    @Up2NotEmpty(payload = Up2Warn.class)
    private String andOverride;

    public String getADefault() {
        return aDefault;
    }

    public void setADefault(String aDefault) {
        this.aDefault = aDefault;
    }

    public Integer getAWarning() {
        return aWarning;
    }

    public void setAWarning(Integer aWarning) {
        this.aWarning = aWarning;
    }

    public String getAnError() {
        return anError;
    }

    public void setAnError(String anError) {
        this.anError = anError;
    }

    public Integer getAFatal() {
        return aFatal;
    }

    public void setAFatal(Integer aFatal) {
        this.aFatal = aFatal;
    }

    public Integer getAnOther() {
        return anOther;
    }

    public void setAnOther(Integer anOther) {
        this.anOther = anOther;
    }

    public String getAndMore() {
        return andMore;
    }

    public void setAndMore(String andMore) {
        this.andMore = andMore;
    }

    public String getAndOverride() {
        return andOverride;
    }

    public void setAndOverride(String andOverride) {
        this.andOverride = andOverride;
    }
}
