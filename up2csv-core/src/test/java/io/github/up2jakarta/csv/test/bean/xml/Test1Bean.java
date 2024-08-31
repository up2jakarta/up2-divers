package io.github.up2jakarta.csv.test.bean.xml;

import io.github.up2jakarta.csv.annotation.Position;
import io.github.up2jakarta.csv.annotation.Up2EnableXML;
import io.github.up2jakarta.csv.extension.Segment;
import io.github.up2jakarta.csv.test.codelist.CurrencyCodeType;
import io.github.up2jakarta.csv.test.codelist.TestCodeList;
import io.github.up2jakarta.csv.test.codelist.TestCodeListConverter;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType
@Up2EnableXML
@SuppressWarnings("unused")
public class Test1Bean implements Segment {

    public static final String XML_XXX = "XML-XXX";

    @Position(0)
    private XML1Enum enum1;

    @Position(1)
    private XML2Enum enum2;

    @Position(2)
    private XML3Enum enum3;

    @Position(3)
    private CurrencyCodeType adapter1;

    @Position(4)
    @XmlJavaTypeAdapter(TestCodeListConverter.class)
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

    public XML3Enum getEnum3() {
        return enum3;
    }

    public void setEnum3(XML3Enum enum3) {
        this.enum3 = enum3;
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
