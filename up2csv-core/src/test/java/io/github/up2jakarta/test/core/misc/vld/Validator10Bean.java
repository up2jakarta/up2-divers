package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;

import java.util.Optional;

import static io.github.up2jakarta.test.core.misc.vld.Validator4Bean.OFragment;

@Valid
public final class Validator10Bean implements Segment {

    @Fragment(0)
    @ValidOverride
    public Optional<OFragment> fragment;

}