package io.github.up2jakarta.csv.core.misc.vld;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.core.misc.vld.Validator4Bean.OFragment;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.validation.Valid;

import java.util.Optional;

@Valid
public final class Validator5Bean implements Segment {

    @Fragment(0)
    public Optional<@Valid OFragment> fragment;

}