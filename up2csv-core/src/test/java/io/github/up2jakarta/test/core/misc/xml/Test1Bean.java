package io.github.up2jakarta.test.core.misc.xml;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.test.core.misc.lov.CurrencyCodeType;
import io.github.up2jakarta.test.core.misc.lov.TestCodeList;
import io.github.up2jakarta.test.core.misc.lov.TestCodeListConverter;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType
@Up2EnableXML
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

    public XML2Enum getEnum2() {
        return enum2;
    }

    public XML3Enum getEnum3() {
        return enum3;
    }

    public CurrencyCodeType getAdapter1() {
        return adapter1;
    }

    public TestCodeList getAdapter2() {
        return adapter2;
    }

}
