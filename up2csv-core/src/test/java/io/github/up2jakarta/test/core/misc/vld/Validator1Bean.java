package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.lov.CurrencyConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Valid
public class Validator1Bean implements Segment {

    @Position(0)
    @Error(CurrencyConverter.ISO_4217)
    @Size(max = 3)
    private String currency;

}
