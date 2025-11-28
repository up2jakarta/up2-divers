package io.github.up2jakarta.csv.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Optional;

@Valid
public final class Validator8Bean implements Segment {

    @Fragment(0)
    @ValidOverride(groups = Up2Warn.class)
    public Optional<OFragment> fragment;

    public static final class OFragment implements Segment {
        @Position(0)
        public Optional<@NotEmpty(groups = Up2Warn.class) String> id;
    }
}