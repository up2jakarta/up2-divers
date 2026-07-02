package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.validation.constraints.NotBlank;

@Error("TU")
@BusinessObject("00")
public class BIdWrapperObject implements Segment {

    @Position(0)
    @BusinessId
    public final Wrapper<@NotBlank String> key = new Wrapper<>();

}
