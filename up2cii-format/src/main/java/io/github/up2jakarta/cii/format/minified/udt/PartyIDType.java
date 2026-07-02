package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.ppf.SchemeCodeType;
import jakarta.xml.bind.annotation.XmlAttribute;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
public class PartyIDType extends AbstractIDType<SchemeCodeType> {

    // BT-29a-1, BT-29b-1, BT-29c-1, BT-29d-1, BT-30-1, BT-46a-1, BT-46b-1, BT-46c-1 and (28) specifications too.
    @XmlAttribute(name = "schemeID")
    private SchemeCodeType schemeId;

    @Override
    public SchemeCodeType getSchemeId() {
        return this.schemeId;
    }

    @Override
    public void setSchemeId(SchemeCodeType schemeId) {
        this.schemeId = schemeId;
    }

}
