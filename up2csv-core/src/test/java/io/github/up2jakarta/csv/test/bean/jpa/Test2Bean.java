package io.github.up2jakarta.csv.test.bean.jpa;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableJPA;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.test.clv.TestCodeList;
import io.github.up2jakarta.csv.test.clv.TestCodeListConverter;
import io.github.up2jakarta.xml.api.SeverityType;
import jakarta.persistence.*;

@Entity
@Up2EnableJPA
@Table(name = "TU_SEGMENT")
@SuppressWarnings("unused")
public class Test2Bean implements Segment {

    public static final String XML_001 = "JPA-001";
    public static final String XML_002 = "JPA-002";
    public static final String XML_003 = "JPA-003";

    @Position(0)
    @Error(value = XML_001, severity = SeverityType.WARNING)
    @Enumerated
    @Transient
    private XML1Enum enum1;

    @Position(1)
    @Error(value = XML_002, severity = SeverityType.WARNING)
    @Enumerated
    @Transient
    private XML2Enum enum2;

    @Position(2)
    @Error(value = XML_003, severity = SeverityType.WARNING)
    @Convert(converter = TestCodeListConverter.class)
    @Transient
    private TestCodeList adapter;

    public XML1Enum getEnum1() {
        return enum1;
    }

    public void setEnum1(XML1Enum enum1) {
        this.enum1 = enum1;
    }

    public XML2Enum getEnum2() {
        return enum2;
    }

    public void setEnum2(XML2Enum enum2) {
        this.enum2 = enum2;
    }

    public TestCodeList getAdapter() {
        return adapter;
    }

    public void setAdapter(TestCodeList adapter) {
        this.adapter = adapter;
    }
}
