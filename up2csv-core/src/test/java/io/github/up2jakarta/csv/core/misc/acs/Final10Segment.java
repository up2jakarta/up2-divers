package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.core.misc.acs.Final2Segment.SFragment;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.core.Wrapper;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
@Access(AccessType.FIELD)
public class Final10Segment implements Segment {

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

    public Integer getKey() {
        return key;
    }

    public String getCode() {
        return code.get();
    }

    public String getValue() {
        return fragment.map(SFragment::getValue, null);
    }
}
