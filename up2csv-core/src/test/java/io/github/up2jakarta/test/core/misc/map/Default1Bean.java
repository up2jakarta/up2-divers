package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;

public class Default1Bean implements Segment {

    @Position(value = 0, defaultValue = "*")
    private String code;

    @Fragment(value = 1, prototype = true)
    private InnerBean reference;

    public String getCode() {
        return code;
    }

    public InnerBean getReference() {
        return reference;
    }

    public static class InnerBean implements Segment {

        @Position(value = 0, defaultValue = "*")
        private String code;

        @Position(value = 1, defaultValue = "*")
        private String value;

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

    }
}
