package io.github.up2jakarta.csv.core.misc.xml;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
@SuppressWarnings("unused")
public enum XML4Enum {

    @XmlEnumValue("*") ANY,
    @XmlEnumValue("*") ALL

}
