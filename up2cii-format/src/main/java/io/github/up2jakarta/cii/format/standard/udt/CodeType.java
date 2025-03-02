package io.github.up2jakarta.cii.format.standard.udt;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class CodeType extends AbstractCodeType<String> {

    @XmlAttribute(name = "listID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listID;

    /**
     * {@inheritDoc}
     **/
    @Override
    public String getListID() {
        return listID;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setListID(String listID) {
        this.listID = listID;
    }

}
