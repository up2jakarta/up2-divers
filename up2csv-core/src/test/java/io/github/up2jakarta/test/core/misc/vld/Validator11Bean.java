package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import jakarta.validation.Valid;

import java.util.Optional;

import static io.github.up2jakarta.test.core.misc.vld.Validator6Bean.OFragment;

@ValidOverride(groups = Up2Warn.class)
public final class Validator11Bean implements Segment {

    @Fragment(0)
    @ValidOverride(groups = {Up2Warn.class, Up2Warn.class}) // Ignored :: Same groups
    public Optional<@Valid OFragment> fragment;

}