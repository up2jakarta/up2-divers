package io.github.up2jakarta.csv.core.misc.xml;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.csv.core.misc.lov.TestCodeList;
import io.github.up2jakarta.csv.core.misc.lov.TestCodeListConverter;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType
@Up2EnableXML
@SuppressWarnings("unused")
public class Test2Bean implements Segment {
    public static final String XML_001 = "XML-001";
    public static final String XML_002 = "XML-002";
    public static final String XML_003 = "XML-003";
    public static final String XML_004 = "XML-004";

    @Position(0)
    @Error(value = XML_001, level = SeverityType.WARNING)
    private XML1Enum enum1;

    @Position(1)
    @Error(value = XML_002, level = SeverityType.WARNING)
    private XML2Enum enum2;

    @Position(2)
    @Error(value = XML_003, level = SeverityType.WARNING)
    private CurrencyCodeType adapter1;

    @Position(3)
    @XmlJavaTypeAdapter(TestCodeListConverter.class)
    @Error(value = XML_004, level = SeverityType.WARNING)
    private TestCodeList adapter2;

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

    public CurrencyCodeType getAdapter1() {
        return adapter1;
    }

    public void setAdapter1(CurrencyCodeType adapter1) {
        this.adapter1 = adapter1;
    }

    public TestCodeList getAdapter2() {
        return adapter2;
    }

    public void setAdapter2(TestCodeList adapter2) {
        this.adapter2 = adapter2;
    }
}
