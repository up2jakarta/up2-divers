package io.github.up2jakarta.cii.format.standard.ram;


import io.github.up2jakarta.cii.format.standard.udt.TextType;
import jakarta.xml.bind.annotation.*;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentContextParameterType", propOrder = {"id", "value", "specifiedDocumentVersion"})
abstract class AbstractContextParameterType<T> {

    @XmlElement(name = "Value")
    private TextType value;

    @XmlElement(name = "SpecifiedDocumentVersion")
    private DocumentVersionType specifiedDocumentVersion;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is {@link T }
     */
    public abstract T getID();

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is {@link T }
     */
    public abstract void setID(T value);

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link TextType }
     */
    public TextType getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link TextType }
     */
    public void setValue(TextType value) {
        this.value = value;
    }

    /**
     * Gets the value of the specifiedDocumentVersion property.
     *
     * @return possible object is {@link DocumentVersionType }
     */
    public DocumentVersionType getSpecifiedDocumentVersion() {
        return specifiedDocumentVersion;
    }

    /**
     * Sets the value of the specifiedDocumentVersion property.
     *
     * @param value allowed object is {@link DocumentVersionType }
     */
    public void setSpecifiedDocumentVersion(DocumentVersionType value) {
        this.specifiedDocumentVersion = value;
    }

}
