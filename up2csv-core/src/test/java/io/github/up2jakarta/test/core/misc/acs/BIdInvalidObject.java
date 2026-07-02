package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;

import java.util.Optional;

@Error("TU")
@BusinessObject("00")
public class BIdInvalidObject implements Segment {

    @Position(0)
    @BusinessId
    public Optional<String> key;

}
