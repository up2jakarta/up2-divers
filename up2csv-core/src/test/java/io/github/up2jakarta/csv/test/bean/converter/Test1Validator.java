package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Number;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.misc.Errors;
import io.github.up2jakarta.csv.test.validation.Up2NotEmpty;
import io.github.up2jakarta.csv.test.validation.Up2Warn;
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
    @Max(value = 100, payload = Errors.Warning.class)
    private Integer aWarning;

    @Position(2)
    @NotEmpty(payload = Errors.Error.class)
    private String anError;

    @Position(3)
    @Up2Number
    @NotNull(payload = Errors.Fatal.class)
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

    public void setADefault(String aDefault) {
        this.aDefault = aDefault;
    }

    public void setAWarning(Integer aWarning) {
        this.aWarning = aWarning;
    }

    public void setAnError(String anError) {
        this.anError = anError;
    }

    public void setAFatal(Integer aFatal) {
        this.aFatal = aFatal;
    }

    public void setAnOther(Integer anOther) {
        this.anOther = anOther;
    }

    public void setAndMore(String andMore) {
        this.andMore = andMore;
    }

    public void setAndOverride(String andOverride) {
        this.andOverride = andOverride;
    }
}
