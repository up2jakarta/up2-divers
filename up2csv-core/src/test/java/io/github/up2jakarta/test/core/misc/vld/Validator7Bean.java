package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Optional;

@Valid
public final class Validator7Bean implements Segment {

    @Fragment(0)
    public Optional<OFragment> fragment;

    @Valid
    public static final class OFragment implements Segment {
        @Position(0)
        public Optional<@NotEmpty String> id;
    }
}