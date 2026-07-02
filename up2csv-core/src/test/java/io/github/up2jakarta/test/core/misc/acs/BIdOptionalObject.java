package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Error("TU")
@BusinessObject("00")
public class BIdOptionalObject implements Segment {

    @Position(0)
    @BusinessId
    @Up2Number
    public Optional<@NotNull Integer> key;

}
