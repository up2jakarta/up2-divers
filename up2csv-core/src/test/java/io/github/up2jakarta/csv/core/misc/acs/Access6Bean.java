package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@SuppressWarnings("unused")
public class Access6Bean implements Segment {

    @Position(0)
    @Access(AccessType.FIELD)
    private String code;

    private Access6Bean() {
    }

}
