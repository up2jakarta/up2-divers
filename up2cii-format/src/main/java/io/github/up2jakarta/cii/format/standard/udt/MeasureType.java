package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.format.UnitMeasureType;
import io.github.up2jakarta.cii.ppf.MeasurementUnitCode;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeasureType", propOrder = {"value"})
public class MeasureType extends UnitMeasureType<MeasurementUnitCode> {

    @XmlAttribute(name = "unitCode")
    protected MeasurementUnitCode unitCode;

    @Override
    public MeasurementUnitCode getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(MeasurementUnitCode value) {
        this.unitCode = value;
    }
}
