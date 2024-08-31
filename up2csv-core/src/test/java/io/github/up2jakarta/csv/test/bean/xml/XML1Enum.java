package io.github.up2jakarta.csv.test.bean.xml;

import io.github.up2jakarta.csv.annotation.Error;
import io.github.up2jakarta.csv.extension.SeverityType;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum
@Error(value = Test1Bean.XML_XXX, severity = SeverityType.WARNING)
public enum XML1Enum {

    @XmlEnumValue("1") ONE

}
