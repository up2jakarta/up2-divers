package io.github.up2jakarta.test.core.misc.xml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(Integer.class)
public enum XML2Enum {

    @XmlEnumValue("2") TWO

}
