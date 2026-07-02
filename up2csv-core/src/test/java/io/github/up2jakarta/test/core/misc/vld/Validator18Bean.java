package io.github.up2jakarta.test.core.misc.vld;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@ValidOverride(groups = Warning.class)
public final class Validator18Bean implements Segment {
    @Fragment(0)
    public @Valid EFragment fragment;

    @ValidOverride(groups = Up2Warn.class)
    public static final class EFragment implements Segment {
        @Position(0)
        public @NotEmpty(groups = {Up2Warn.class, Warning.class}) String code;
    }

}