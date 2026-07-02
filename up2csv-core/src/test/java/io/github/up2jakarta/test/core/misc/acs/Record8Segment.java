package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.test.core.misc.acs.Final2Segment.SFragment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Valid
public record Record8Segment(
        @Position(0) @Up2Number Integer key,
        @Position(1) Optional<@NotBlank String> code,
        @Fragment(2) Optional<@Valid SFragment> fragment
) implements FinalSegment {

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getCode() {
        return code.orElse(null);
    }

    @Override
    public String getValue() {
        return fragment.map(SFragment::getValue).orElse(null);
    }
}
