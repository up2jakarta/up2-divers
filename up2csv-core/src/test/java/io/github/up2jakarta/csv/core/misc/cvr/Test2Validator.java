package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.misc.vld.Up2NotEmpty;
import io.github.up2jakarta.csv.core.misc.vld.Up2Warn;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@ValidOverride
@SuppressWarnings("unused")
public class Test2Validator implements Segment {

    public static final String TU_P_009 = "TU-P009";
    public static final String TU_P_010 = "TU-P010";
    public static final String TU_P_021 = "TU-P021";

    @Position(0)
    @Error(value = TU_P_009, severity = SeverityType.WARNING)
    @NotEmpty
    private String sequence;

    @Position(1)
    @Error(value = TU_P_010, severity = SeverityType.WARNING)
    @NotNull(payload = Up2Warn.class)
    private String other;

    @Position(2)
    @Error(value = TU_P_021, severity = SeverityType.WARNING)
    @Up2NotEmpty(payload = Up2Warn.class)
    private String andMore;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAndMore() {
        return andMore;
    }

    public void setAndMore(String andMore) {
        this.andMore = andMore;
    }
}
