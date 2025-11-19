package io.github.up2jakarta.cii.core;

import io.github.up2jakarta.cii.format.standard.qdt.LinearUnitMeasureType;
import io.github.up2jakarta.cii.format.standard.qdt.VolumeUnitMeasureType;
import io.github.up2jakarta.cii.format.standard.qdt.WeightUnitMeasureType;
import io.github.up2jakarta.lov.CodeList;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;

/**
 * The common class for XML UnitMeasureType.
 * {@link WeightUnitMeasureType}
 * {@link VolumeUnitMeasureType}
 * {@link LinearUnitMeasureType}
 */
@XmlTransient
@SuppressWarnings("unused")
public abstract class AbstractUnitMeasureType<E extends Enum<E> & CodeList<E>> {

    @XmlValue
    protected BigDecimal value;

    @XmlAttribute(name = "unitCodeListVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCodeListVersionID;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link BigDecimal }
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link BigDecimal }
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Gets the value of the unitCode property.
     *
     * @return possible object is {@link E }
     */
    public abstract E getUnitCode();

    /**
     * Sets the value of the unitCode property.
     *
     * @param value allowed object is {@link E }
     */
    public abstract void setUnitCode(E value);

    /**
     * Gets the value of the unitCodeListVersionID property.
     *
     * @return possible object is {@link String }
     */
    public String getUnitCodeListVersionID() {
        return unitCodeListVersionID;
    }

    /**
     * Sets the value of the unitCodeListVersionID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnitCodeListVersionID(String value) {
        this.unitCodeListVersionID = value;
    }

}
