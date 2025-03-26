package io.github.up2jakarta.csv.test.bean.converter;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.ValidOverride;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.validation.Up2NotEmpty;
import io.github.up2jakarta.csv.test.validation.Up2Warn;
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
    @Error(value = TU_P_009, severity = SeverityType.FATAL)
    @NotEmpty
    private String sequence;

    @Position(1)
    @Error(value = TU_P_010, severity = SeverityType.WARNING)
    @NotNull(payload = Up2Warn.class)
    private String other;

    @Position(2)
    @Error(value = TU_P_021, severity = SeverityType.ERROR)
    @Up2NotEmpty(payload = Up2Warn.class)
    private String andMore;

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setAndMore(String andMore) {
        this.andMore = andMore;
    }
}
