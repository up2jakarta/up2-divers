package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.ppf.SchemeCodeType;
import jakarta.xml.bind.annotation.XmlAttribute;

public class PartyIDType extends AbstractIDType<SchemeCodeType> {

    @XmlAttribute(name = "schemeID")
    protected SchemeCodeType schemeID;

    /**
     * {@inheritDoc}
     **/
    @Override
    public SchemeCodeType getSchemeID() {
        return schemeID;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setSchemeID(SchemeCodeType value) {
        this.schemeID = value;
    }

}
