package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.edi.ReferenceCodeType;
import jakarta.xml.bind.annotation.XmlAttribute;

public class FiscalIDType extends AbstractIDType<ReferenceCodeType> {

    @XmlAttribute(name = "schemeID")
    protected ReferenceCodeType schemeID;

    /**
     * {@inheritDoc}
     **/
    @Override
    public ReferenceCodeType getSchemeID() {
        return schemeID;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setSchemeID(ReferenceCodeType value) {
        this.schemeID = value;
    }

}
