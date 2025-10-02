package io.github.up2jakarta.csv.test.bean.mapper;

import io.github.up2jakarta.csv.annotation.Fragment;
import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2Default;
import io.github.up2jakarta.csv.extension.Segment;

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
