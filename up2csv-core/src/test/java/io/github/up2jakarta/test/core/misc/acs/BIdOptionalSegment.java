package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.validation.Valid;

import java.util.Optional;

@Valid
public final class BIdOptionalSegment implements Segment {

    @Fragment(0)
    public Optional<@Valid OFragment> fragment = Optional.empty();

    public static final class OFragment implements Segment {
        @Position(0)
        @Up2Number
        @BusinessId
        public Optional<Integer> id = Optional.empty();
    }
}