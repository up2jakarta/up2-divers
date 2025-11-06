package io.github.up2jakarta.csv.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2CodeList;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.xml.clv.CodeList;

@SuppressWarnings("unused")
public class Test3Resolver implements Segment {

    @Position(0)
    @Up2CodeList
    private EnumLike unit = EnumLike.NAN;

    public EnumLike getUnit() {
        return unit;
    }

    public void setUnit(EnumLike unit) {
        this.unit = unit;
    }

    public final static class EnumLike implements CodeList<EnumLike> {

        public static final EnumLike ONE = new EnumLike("1", "ONE");
        public static final EnumLike TWO = new EnumLike("2", "TWO");

        private static final EnumLike NAN = new EnumLike("N", "NAN");

        private final String name;
        private final String code;

        private EnumLike(String code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getCode() {
            return code;
        }
    }

}
