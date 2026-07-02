package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.lov.core.Wrapper;
import io.github.up2jakarta.test.core.misc.acs.Final2Segment.SFragment;

public class Final11Segment extends Final10Segment {

    public Final11Segment(Integer key, String code, SFragment fragment) {
        super(key, new Wrapper<>(code), new Wrapper<>(fragment));
    }
}
