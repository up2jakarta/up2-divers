package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.DateTimeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplyChainEventType", propOrder = {"occurrenceDateTime"})
public class SupplyChainEventType {

    // EXT-FR-FE-158-0
    @XmlElement(name = "OccurrenceDateTime")
    private DateTimeType occurrenceDateTime;

    public DateTimeType getOccurrenceDateTime() {
        return this.occurrenceDateTime;
    }

    public void setOccurrenceDateTime(DateTimeType occurrenceDateTime) {
        this.occurrenceDateTime = occurrenceDateTime;
    }

}
