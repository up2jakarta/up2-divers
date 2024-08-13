package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Severity;
import io.github.up2jakarta.csv.core.Segment;
import io.github.up2jakarta.csv.misc.SeverityType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Valid
@SuppressWarnings("unused")
public class ValidAnnotationBean implements Segment {

    @Position(0)
    @Severity(SeverityType.ERROR)
    @Size(max = 3)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
