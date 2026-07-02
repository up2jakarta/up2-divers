package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import jakarta.persistence.Access;

import static jakarta.persistence.AccessType.PROPERTY;

@SuppressWarnings("unused")
public class Access4Bean implements Segment {

    @Position(0)
    @Access(PROPERTY)
    private String code;

    private String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }
}
