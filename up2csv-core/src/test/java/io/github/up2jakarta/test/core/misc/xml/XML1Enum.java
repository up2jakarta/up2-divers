package io.github.up2jakarta.test.core.misc.xml;

import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.lov.SeverityType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
@Error(value = Test1Bean.XML_XXX, level = SeverityType.WARNING)
public enum XML1Enum {

    @SuppressWarnings("unused")
    @XmlEnumValue("1") ONE,
    ALL

}
