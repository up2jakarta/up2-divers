package io.github.up2jakarta.cii.format.standard.qdt;

import io.github.up2jakarta.cii.edi.LinearMeasurementUnitCodeType;
import io.github.up2jakarta.cii.xml.AbstractUnitMeasureType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinearUnitMeasureType", propOrder = {"value"})
public class LinearUnitMeasureType extends AbstractUnitMeasureType<LinearMeasurementUnitCodeType> {

    @XmlAttribute(name = "unitCode")
    protected LinearMeasurementUnitCodeType unitCode;

    @Override
    public LinearMeasurementUnitCodeType getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(LinearMeasurementUnitCodeType value) {
        this.unitCode = value;
    }
}
