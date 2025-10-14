package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Default;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public class DefaultBean implements Segment {

    @Position(0)
    @Up2Default("*")
    private String code;

    @Fragment(1)
    private InnerBean reference;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InnerBean getReference() {
        return reference;
    }

    public void setReference(InnerBean reference) {
        this.reference = reference;
    }

    public static class InnerBean implements Segment {

        @Position(0)
        @Up2Default("*")
        private String code;

        @Position(1)
        @Up2Default("*")
        private String value;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
