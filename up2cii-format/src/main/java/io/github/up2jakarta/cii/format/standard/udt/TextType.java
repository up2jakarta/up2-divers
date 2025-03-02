package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.edi.CountryIDType;
import io.github.up2jakarta.cii.ppf.LanguageCodeType;
import jakarta.xml.bind.annotation.*;

import java.util.Locale;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TextType", propOrder = {"value"})
public class TextType {

    @XmlValue
    protected String value;

    @XmlAttribute(name = "languageID")
    protected LanguageCodeType languageID;

    @XmlAttribute(name = "languageLocaleID")
    protected CountryIDType languageLocaleID;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public String getValue() {
        Locale.getISOLanguages();
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
     * Gets the value of the languageID property.
     *
     * @return possible object is {@link LanguageCodeType }
     */
    public LanguageCodeType getLanguageID() {
        return languageID;
    }

    /**
     * Sets the value of the languageID property.
     *
     * @param value allowed object is {@link LanguageCodeType }
     */
    public void setLanguageID(LanguageCodeType value) {
        this.languageID = value;
    }

    /**
     * Gets the value of the languageLocaleID property.
     *
     * @return possible object is {@link CountryIDType }
     */
    public CountryIDType getLanguageLocaleID() {
        return languageLocaleID;
    }

    /**
     * Sets the value of the languageLocaleID property.
     *
     * @param value allowed object is {@link CountryIDType }
     */
    public void setLanguageLocaleID(CountryIDType value) {
        this.languageLocaleID = value;
    }

}
