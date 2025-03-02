package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.edi.TimePointFormatCodeType;
import io.github.up2jakarta.cii.xml.adapters.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "", propOrder = {"value"})
public class DateStringType {

    // EXT-FR-FE-158
    private LocalDate value;

    // EXT-FR-FE-158-1
    private TimePointFormatCodeType format;

    @XmlValue
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getValue() {
        return this.value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }

    @XmlAttribute(name = "format")
    public TimePointFormatCodeType getFormat() {
        return this.format;
    }

    public void setFormat(TimePointFormatCodeType format) {
        this.format = format;
    }

}
