package io.github.up2jakarta.cii.format.standard.udt;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The default implementation is {@link IDType}.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IDType", propOrder = {"value"})
public abstract class AbstractIDType<E> {

    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String value;

    @XmlAttribute(name = "schemeName")
    protected String schemeName;

    @XmlAttribute(name = "schemeAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeAgencyID;

    @XmlAttribute(name = "schemeAgencyName")
    protected String schemeAgencyName;

    @XmlAttribute(name = "schemeVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeVersionID;

    @XmlAttribute(name = "schemeDataURI")
    @XmlSchemaType(name = "anyURI")
    protected String schemeDataURI;

    @XmlAttribute(name = "schemeURI")
    @XmlSchemaType(name = "anyURI")
    protected String schemeURI;

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
        this.value = value;
    }

    /**
     * Gets the value of the schemeID property.
     *
     * @return possible object is {@link E }
     */
    public abstract E getSchemeID();

    /**
     * Sets the value of the schemeID property.
     *
     * @param value allowed object is {@link E }
     */
    public abstract void setSchemeID(E value);

    /**
     * Gets the value of the schemeName property.
     *
     * @return possible object is {@link String }
     */
    public String getSchemeName() {
        return schemeName;
    }

    /**
     * Sets the value of the schemeName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSchemeName(String value) {
        this.schemeName = value;
    }

    /**
     * Gets the value of the schemeAgencyID property.
     *
     * @return possible object is {@link String }
     */
    public String getSchemeAgencyID() {
        return schemeAgencyID;
    }

    /**
     * Sets the value of the schemeAgencyID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSchemeAgencyID(String value) {
        this.schemeAgencyID = value;
    }

    /**
     * Gets the value of the schemeAgencyName property.
     *
     * @return possible object is {@link String }
     */
    public String getSchemeAgencyName() {
        return schemeAgencyName;
    }

    /**
     * Sets the value of the schemeAgencyName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSchemeAgencyName(String value) {
        this.schemeAgencyName = value;
    }

    /**
     * Gets the value of the schemeVersionID property.
     *
     * @return possible object is {@link String }
     */
    public String getSchemeVersionID() {
        return schemeVersionID;
    }

    /**
     * Sets the value of the schemeVersionID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSchemeVersionID(String value) {
        this.schemeVersionID = value;
    }

    /**
     * Gets the value of the schemeDataURI property.
     *
     * @return possible object is {@link String }
     */
    public String getSchemeDataURI() {
        return schemeDataURI;
    }

    /**
     * Sets the value of the schemeDataURI property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSchemeDataURI(String value) {
        this.schemeDataURI = value;
    }

    /**
     * Gets the value of the schemeURI property.
     *
     * @return possible object is {@link String }
     */
    public String getSchemeURI() {
        return schemeURI;
    }

    /**
     * Sets the value of the schemeURI property.
     *
     * @param value allowed object is {@link String }
     */
    public void setSchemeURI(String value) {
        this.schemeURI = value;
    }

}
