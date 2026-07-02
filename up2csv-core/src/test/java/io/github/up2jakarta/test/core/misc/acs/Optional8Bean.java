package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import jakarta.validation.Valid;

import java.util.Optional;

import static io.github.up2jakarta.test.core.misc.acs.BIdOptionalSegment.OFragment;

@Valid
public final class Optional8Bean implements Segment {

    @Fragment(value = 0, nullable = true)
    public Optional<@Valid OFragment> fragment = Optional.empty();

}