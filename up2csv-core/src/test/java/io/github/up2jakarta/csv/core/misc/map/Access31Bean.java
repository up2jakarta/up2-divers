package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Access(AccessType.FIELD)
public class Access31Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

}
