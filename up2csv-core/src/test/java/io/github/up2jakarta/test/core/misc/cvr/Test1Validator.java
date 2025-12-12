package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.vld.Up2NotEmpty;
import io.github.up2jakarta.test.core.misc.vld.Up2Warn;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Valid
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

}
