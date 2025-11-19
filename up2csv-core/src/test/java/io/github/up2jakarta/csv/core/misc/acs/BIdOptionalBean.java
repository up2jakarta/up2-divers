package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.util.Optional;

@Valid
@Access(AccessType.FIELD)
public final class BIdOptionalBean implements Segment {

    @Fragment(0)
    public Optional<@Valid OFragment> fragment;

    public static final class OFragment implements Segment {
        @Position(0)
        @Up2Number
        @BusinessId
        public Optional<Integer> id;
    }
}