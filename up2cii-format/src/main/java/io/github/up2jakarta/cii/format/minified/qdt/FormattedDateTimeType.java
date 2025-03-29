package io.github.up2jakarta.cii.format.minified.qdt;

import io.github.up2jakarta.cii.core.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "FormattedDateTimeType", propOrder = {"dateTimeString"})
public class FormattedDateTimeType {

    // BT-26, EXT-FR-FE-138
    private LocalDate dateTimeString;

    @XmlElement(name = "DateTimeString", required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDateTimeString() {
        return this.dateTimeString;
    }

    public void setDateTimeString(LocalDate dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

}
