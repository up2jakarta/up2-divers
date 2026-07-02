package io.github.up2jakarta.cii.format.minified.udt;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndicatorType", propOrder = {"indicator"})
public class IndicatorType {

    // BG-20, BG-21, BG-27, BG-28
    @XmlElement(name = "Indicator")
    private Boolean indicator;

    public Boolean isIndicator() {
        return this.indicator;
    }

    public void setIndicator(Boolean indicator) {
        this.indicator = indicator;
    }

}
