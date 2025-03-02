package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.DateTimeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "SpecifiedPeriodType", propOrder = {
        "startDateTime",
        "endDateTime"
})
public class SpecifiedPeriodType {

    private DateTimeType startDateTime;

    private DateTimeType endDateTime;

    @XmlElement(name = "StartDateTime")
    public DateTimeType getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(DateTimeType startDateTime) {
        this.startDateTime = startDateTime;
    }

    @XmlElement(name = "EndDateTime")
    public DateTimeType getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(DateTimeType endDateTime) {
        this.endDateTime = endDateTime;
    }

}
