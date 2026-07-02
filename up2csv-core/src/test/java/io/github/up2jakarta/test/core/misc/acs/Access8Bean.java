package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public class Access8Bean implements Segment {

    @Position(0)
    protected String code;

    public static String getCode() {
        throw new RuntimeException("static");
    }

    public static void setCode(String ignore) {
        throw new RuntimeException("static");
    }
}
