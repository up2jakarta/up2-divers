package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.lov.core.Wrapper;
import io.github.up2jakarta.test.core.misc.acs.Final2Segment.SFragment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
public class Final10Segment implements FinalSegment {

    @Position(0)
    @Up2Number
    private final Integer key;

    @Position(1)
    @NotBlank
    private final Wrapper<String> code;

    @Fragment(2)
    private final Wrapper<@Valid SFragment> fragment;

    public Final10Segment(Integer key, Wrapper<String> code, Wrapper<SFragment> fragment) {
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
        return code.get();
    }

    @Override
    public final String getValue() {
        return fragment.map(SFragment::getValue, null);
    }
}
