package io.github.up2jakarta.cii.format.standard.qdt;

import io.github.up2jakarta.cii.edi.VolumeMeasurementUnitCodeType;
import io.github.up2jakarta.cii.xml.AbstractUnitMeasureType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VolumeUnitMeasureType", propOrder = {"value"})
public class VolumeUnitMeasureType extends AbstractUnitMeasureType<VolumeMeasurementUnitCodeType> {

    @XmlAttribute(name = "unitCode")
    protected VolumeMeasurementUnitCodeType unitCode;

    @Override
    public VolumeMeasurementUnitCodeType getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(VolumeMeasurementUnitCodeType value) {
        this.unitCode = value;
    }
}
