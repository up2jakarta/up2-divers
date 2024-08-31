package io.github.up2jakarta.csv.test.bean.xml;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableXML;
import io.github.up2jakarta.csv.extension.Segment;

//@XmlType
@Up2EnableXML
@SuppressWarnings("unused")
public class Test3Bean implements Segment {

    @Position(0)
    private XML1Enum enum1;

    public void setEnum1(XML1Enum enum1) {
        this.enum1 = enum1;
    }
}
