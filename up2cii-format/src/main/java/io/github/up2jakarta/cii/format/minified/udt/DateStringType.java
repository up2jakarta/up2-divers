package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.core.LocalDateAdapter;
import io.github.up2jakarta.cii.edi.TimePointFormatCodeType;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"value"})
public class DateStringType {

    // EXT-FR-FE-158
    @XmlValue
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate value;

    // EXT-FR-FE-158-1
    @XmlAttribute(name = "format")
    private TimePointFormatCodeType format;

    public LocalDate getValue() {
        return this.value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }

    public TimePointFormatCodeType getFormat() {
        return this.format;
    }

    public void setFormat(TimePointFormatCodeType format) {
        this.format = format;
    }

}
