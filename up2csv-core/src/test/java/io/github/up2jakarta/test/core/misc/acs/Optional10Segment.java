package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.test.core.misc.acs.Final2Segment.SFragment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Valid
public class Optional10Segment implements FinalSegment {

    @Position(0)
    @Up2Number
    private final Integer key;

    @Position(1)
    private final Optional<@NotBlank String> code;

    @Fragment(2)
    private final Optional<@Valid SFragment> fragment;

    public Optional10Segment(Integer key, Optional<String> code, Optional<SFragment> fragment) {
        this.key = key;
        this.code = code;
        this.fragment = fragment;
    }

    @Override
    public final Integer getKey() {
        return key;
    }

    @Override
    public final String getCode() {
        return code.orElse(null);
    }

    @Override
    public final String getValue() {
        return fragment.map(SFragment::getValue).orElse(null);
    }
}
