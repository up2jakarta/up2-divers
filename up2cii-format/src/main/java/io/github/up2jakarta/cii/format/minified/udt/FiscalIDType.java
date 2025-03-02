package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.edi.ReferenceCodeType;
import jakarta.xml.bind.annotation.XmlAttribute;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
public class FiscalIDType extends IDType<ReferenceCodeType> {

    // BT-31-0, BT-32-0, BT-48-0, EXT-FR-FE-11, EXT-FR-FE-28, EXT-FR-FE-51, EXT-FR-FE-74 and (3) specifications too.
    private ReferenceCodeType schemeId;

    @XmlAttribute(name = "schemeID")
    @Override
    public ReferenceCodeType getSchemeId() {
        return this.schemeId;
    }

    @Override
    public void setSchemeId(ReferenceCodeType schemeId) {
        this.schemeId = schemeId;
    }

}
