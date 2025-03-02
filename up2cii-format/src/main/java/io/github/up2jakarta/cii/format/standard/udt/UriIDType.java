package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.ppf.EASchemeIDType;
import jakarta.xml.bind.annotation.XmlAttribute;

public class UriIDType extends AbstractIDType<EASchemeIDType> {

    @XmlAttribute(name = "schemeID")
    protected EASchemeIDType schemeID;

    /**
     * {@inheritDoc}
     **/
    @Override
    public EASchemeIDType getSchemeID() {
        return schemeID;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setSchemeID(EASchemeIDType value) {
        this.schemeID = value;
    }

}
