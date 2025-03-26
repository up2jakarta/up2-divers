package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.xml.adapters.LocalDateAdapter;
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
@XmlType(name = "DateType", propOrder = {"dateString"})
public class DateType {

    // BT-7
    private LocalDate dateString;

    @XmlElement(name = "DateString")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDateString() {
        return this.dateString;
    }

    public void setDateString(LocalDate dateString) {
        this.dateString = dateString;
    }

}
