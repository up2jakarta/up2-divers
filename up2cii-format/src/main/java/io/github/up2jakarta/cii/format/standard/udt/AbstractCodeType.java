package io.github.up2jakarta.cii.format.standard.udt;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The default implementation is {@link CodeType}.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeType", propOrder = {"value"})
public abstract class AbstractCodeType<E> {

    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String value;

    @XmlAttribute(name = "listAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listAgencyID;

    @XmlAttribute(name = "listAgencyName")
    protected String listAgencyName;

    @XmlAttribute(name = "listName")
    protected String listName;

    @XmlAttribute(name = "listVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listVersionID;

    @XmlAttribute(name = "name")
    protected String name;

    @XmlAttribute(name = "languageID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String languageID;

    @XmlAttribute(name = "listURI")
    @XmlSchemaType(name = "anyURI")
    protected String listURI;

    @XmlAttribute(name = "listSchemeURI")
    @XmlSchemaType(name = "anyURI")
    protected String listSchemeURI;

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
     * Gets the value of the listID property.
     *
     * @return possible object is {@link E }
     */
    public abstract E getListID();

    /**
     * Sets the value of the listID property.
     *
     * @param value allowed object is {@link E }
     */
    public abstract void setListID(E value);

    /**
     * Gets the value of the listAgencyID property.
     *
     * @return possible object is {@link String }
     */
    public String getListAgencyID() {
        return listAgencyID;
    }

    /**
     * Sets the value of the listAgencyID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setListAgencyID(String value) {
        this.listAgencyID = value;
    }

    /**
     * Gets the value of the listAgencyName property.
     *
     * @return possible object is {@link String }
     */
    public String getListAgencyName() {
        return listAgencyName;
    }

    /**
     * Sets the value of the listAgencyName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setListAgencyName(String value) {
        this.listAgencyName = value;
    }

    /**
     * Gets the value of the listName property.
     *
     * @return possible object is {@link String }
     */
    public String getListName() {
        return listName;
    }

    /**
     * Sets the value of the listName property.
     *
     * @param value allowed object is {@link String }
     */
    public void setListName(String value) {
        this.listName = value;
    }

    /**
     * Gets the value of the listVersionID property.
     *
     * @return possible object is {@link String }
     */
    public String getListVersionID() {
        return listVersionID;
    }

    /**
     * Sets the value of the listVersionID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setListVersionID(String value) {
        this.listVersionID = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the languageID property.
     *
     * @return possible object is {@link String }
     */
    public String getLanguageID() {
        return languageID;
    }

    /**
     * Sets the value of the languageID property.
     *
     * @param value allowed object is {@link String }
     */
    public void setLanguageID(String value) {
        this.languageID = value;
    }

    /**
     * Gets the value of the listURI property.
     *
     * @return possible object is {@link String }
     */
    public String getListURI() {
        return listURI;
    }

    /**
     * Sets the value of the listURI property.
     *
     * @param value allowed object is {@link String }
     */
    public void setListURI(String value) {
        this.listURI = value;
    }

    /**
     * Gets the value of the listSchemeURI property.
     *
     * @return possible object is {@link String }
     */
    public String getListSchemeURI() {
        return listSchemeURI;
    }

    /**
     * Sets the value of the listSchemeURI property.
     *
     * @param value allowed object is {@link String }
     */
    public void setListSchemeURI(String value) {
        this.listSchemeURI = value;
    }

}
