package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.test.core.misc.acs.Final2Segment.SFragment;

import java.util.Optional;

public class Optional11Segment extends Optional10Segment {

    public Optional11Segment(Integer key, String code, SFragment fragment) {
        super(key, Optional.ofNullable(code), Optional.ofNullable(fragment));
    }
}
