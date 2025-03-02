package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.edi.TimePointFormatCodeType;
import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "", propOrder = {"value"})
public class DateStringType {

    @XmlValue
    protected String value;

    @XmlAttribute(name = "format")
    protected TimePointFormatCodeType format;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     */
    public void setValue(String value) {
        if (this.format != null) {
            this.format.getFormatter().parse(value);
        }
        this.value = value;
    }

    /**
     * Gets the value of the format property.
     *
     * @return possible object is {@link TimePointFormatCodeType }
     */
    public TimePointFormatCodeType getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     *
     * @param value allowed object is {@link String }
     */
    public void setFormat(TimePointFormatCodeType value) {
        this.format = value;
    }

}
