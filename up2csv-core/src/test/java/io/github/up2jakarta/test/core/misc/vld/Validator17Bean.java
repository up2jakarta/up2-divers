package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Valid
public final class Validator17Bean implements Segment {
    @Fragment(0)
    public EFragment fragment;

    @ValidOverride(disable = true)
    public static final class EFragment implements Segment {
        @Position(0)
        public @NotEmpty String code;

        @Fragment(1)
        public DFragment fragment;
    }

    @Valid
    public static final class DFragment implements Segment {
        @Position(0)
        public @NotEmpty String id;
    }

}