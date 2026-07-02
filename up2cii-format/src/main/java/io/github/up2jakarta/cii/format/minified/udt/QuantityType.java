package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.ppf.MeasurementUnitCode;
import jakarta.xml.bind.annotation.*;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuantityType", propOrder = {"value"})
public class QuantityType {

    // BT-129, BT-149
    @XmlValue
    private BigDecimal value;

    // BT-130, BT-150
    @XmlAttribute(name = "unitCode")
    private MeasurementUnitCode unitCode;

    public BigDecimal getValue() {
        return this.value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public MeasurementUnitCode getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(MeasurementUnitCode unitCode) {
        this.unitCode = unitCode;
    }

}
