package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.data.Segment;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Up2EnableXML
public class Test9Resolver implements Segment {

    @Position(0)
    @XmlJavaTypeAdapter(NumberConverter.class)
    public DummyNumber test;

    @XmlEnum
    public static class DummyNumber extends Number {

        @Override
        public int intValue() {
            return 0;
        }

        @Override
        public long longValue() {
            return 0;
        }

        @Override
        public float floatValue() {
            return 0;
        }

        @Override
        public double doubleValue() {
            return 0;
        }
    }

}
