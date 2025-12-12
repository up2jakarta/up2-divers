package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

public class Default3Bean implements Segment {

    @Position(0)
    private String code;

    @Fragment(value = 1, prototype = true)
    private InnerBean bean;

    public InnerBean getBean() {
        return bean;
    }

    public static class InnerBean implements Segment {

        @Position(0)
        @Up2Number
        private int id;

        @Position(value = 1, defaultValue = "Up2J")
        private String code = "Java"; // Ignored

        @Position(value = 2, defaultValue = "Up2J")
        private String value;

        @Position(3)
        private String source;
    }
}
