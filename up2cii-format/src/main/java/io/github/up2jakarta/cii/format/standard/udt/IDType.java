package io.github.up2jakarta.cii.format.standard.udt;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class IDType extends AbstractIDType<String> {

    @XmlAttribute(name = "schemeID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String schemeID;

    /**
     * {@inheritDoc}
     **/
    @Override
    public String getSchemeID() {
        return schemeID;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setSchemeID(String value) {
        this.schemeID = value;
    }

}
