package io.github.up2jakarta.cii.format.standard.qdt;

import io.github.up2jakarta.cii.core.AbstractUnitMeasureType;
import io.github.up2jakarta.cii.edi.WeightMeasurementUnitCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeightUnitMeasureType", propOrder = {"value"})
public class WeightUnitMeasureType extends AbstractUnitMeasureType<WeightMeasurementUnitCodeType> {

    @XmlAttribute(name = "unitCode")
    protected WeightMeasurementUnitCodeType unitCode;

    @Override
    public WeightMeasurementUnitCodeType getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(WeightMeasurementUnitCodeType value) {
        this.unitCode = value;
    }
}
