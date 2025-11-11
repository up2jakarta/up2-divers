package io.github.up2jakarta.csv.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Optional;

@ValidOverride(groups = {Up2Warn.class, Segment.class, Up2Warn.class, Segment.class})
@Access(AccessType.FIELD)
public final class Validator14Bean implements Segment {

    @Fragment(0)
    public Optional<@Valid OFragment> fragment;

    @ValidOverride(groups = {Up2Warn.class, Up2Warn.class}) // Ignore : subset groups
    public static final class OFragment implements Segment {
        @Position(0)
        public Optional<@NotEmpty(groups = Up2Warn.class) String> id;
    }

}