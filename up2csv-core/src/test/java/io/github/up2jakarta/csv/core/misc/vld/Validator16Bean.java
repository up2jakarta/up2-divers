package io.github.up2jakarta.csv.core.misc.vld;

import io.github.up2jakarta.csv.api.Warning;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;

@Valid
public final class Validator16Bean implements Segment {

    public @Position(0) String code;

    @Valid
    public @Fragment(1) OFragment fragment = new OFragment();

    public static final class OFragment implements Segment {

        public @Position(0) String value;

        @AssertTrue(payload = Warning.class)
        public boolean hasValue() {
            return value != null;
        }
    }
}