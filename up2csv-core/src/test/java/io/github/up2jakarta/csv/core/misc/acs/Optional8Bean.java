package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;

import java.util.Optional;

import static io.github.up2jakarta.csv.core.misc.acs.BIdOptionalBean.OFragment;

@Valid
@Access(AccessType.FIELD)
public final class Optional8Bean implements Segment {

    @Fragment(value = 0, nullable = true)
    public Optional<@Valid OFragment> fragment;

}