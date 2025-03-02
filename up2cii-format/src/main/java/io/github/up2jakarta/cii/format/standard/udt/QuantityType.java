package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.ppf.MeasurementUnitCode;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuantityType", propOrder = {"value"})
public class QuantityType {

    @XmlValue
    protected BigDecimal value;

    @XmlAttribute(name = "unitCode")
    protected MeasurementUnitCode unitCode;

    @XmlAttribute(name = "unitCodeListID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCodeListID;

    @XmlAttribute(name = "unitCodeListAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unitCodeListAgencyID;

    @XmlAttribute(name = "unitCodeListAgencyName")
    protected String unitCodeListAgencyName;

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
     * @return possible object is {@link MeasurementUnitCode }
     */
    public MeasurementUnitCode getUnitCode() {
        return unitCode;
    }

    /**
     * Sets the value of the unitCode property.
     *
     * @param value allowed object is {@link MeasurementUnitCode }
     */
    public void setUnitCode(MeasurementUnitCode value) {
        this.unitCode = value;
    }

    /**
     * Gets the value of the unitCodeListID property.
     *
     * @return possible object is {@link String }
     */
    public String getUnitCodeListID() {
        return unitCodeListID;
    }

    /**
     * Sets the value of the unitCodeListID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnitCodeListID(String value) {
        this.unitCodeListID = value;
    }

    /**
     * Gets the value of the unitCodeListAgencyID property.
     *
     * @return possible object is {@link String }
     */
    public String getUnitCodeListAgencyID() {
        return unitCodeListAgencyID;
    }

    /**
     * Sets the value of the unitCodeListAgencyID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnitCodeListAgencyID(String value) {
        this.unitCodeListAgencyID = value;
    }

    /**
     * Gets the value of the unitCodeListAgencyName property.
     *
     * @return possible object is {@link String }
     */
    public String getUnitCodeListAgencyName() {
        return unitCodeListAgencyName;
    }

    /**
     * Sets the value of the unitCodeListAgencyName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUnitCodeListAgencyName(String value) {
        this.unitCodeListAgencyName = value;
    }

}
