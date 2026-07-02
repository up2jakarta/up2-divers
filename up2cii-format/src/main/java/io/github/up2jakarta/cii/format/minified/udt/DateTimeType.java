package io.github.up2jakarta.cii.format.minified.udt;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimeType", propOrder = {"dateTimeString"})
public class DateTimeType {

    // BT-2, BT-9, BT-72, BT-73, BT-74, EXT-FR-FE-158, BT-134, BT-135
    @XmlElement(name = "DateTimeString")
    private DateStringType dateTimeString;

    public DateStringType getDateTimeString() {
        return this.dateTimeString;
    }

    public void setDateTimeString(DateStringType dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

}
