package io.github.up2jakarta.cii.format.minified.udt;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
public class IDType extends AbstractIDType<String> {

    // BT-90
    @XmlAttribute(name = "schemeID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    private String schemeId;

    @Override
    public String getSchemeId() {
        return this.schemeId;
    }

    @Override
    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

}
