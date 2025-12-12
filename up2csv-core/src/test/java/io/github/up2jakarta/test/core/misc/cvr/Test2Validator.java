package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import io.github.up2jakarta.test.core.misc.vld.Up2NotEmpty;
import io.github.up2jakarta.test.core.misc.vld.Up2Warn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@ValidOverride
public class Test2Validator implements Segment {

    public static final String TU_P_009 = "TU-P009";
    public static final String TU_P_010 = "TU-P010";
    public static final String TU_P_021 = "TU-P021";

    @Position(0)
    @Error(value = TU_P_009, level = SeverityType.WARNING)
    @NotEmpty
    private String sequence;

    @Position(1)
    @Error(value = TU_P_010, level = SeverityType.WARNING)
    @NotNull(payload = Up2Warn.class)
    private String other;

    @Position(2)
    @Error(value = TU_P_021, level = SeverityType.WARNING)
    @Up2NotEmpty(payload = Up2Warn.class)
    private String andMore;

}
