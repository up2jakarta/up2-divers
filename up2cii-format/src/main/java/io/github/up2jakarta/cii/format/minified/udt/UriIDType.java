package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.ppf.EASchemeIDType;
import jakarta.xml.bind.annotation.XmlAttribute;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
public class UriIDType extends AbstractIDType<EASchemeIDType> {

    // BT-34-1, BT-49-1, EXT-FR-FE-13, EXT-FR-FE-30, EXT-FR-FE-53, EXT-FR-FE-76, EXT-FR-FE-99 and (1) specifications too.
    @XmlAttribute(name = "schemeID")
    private EASchemeIDType schemeId;

    @Override
    public EASchemeIDType getSchemeId() {
        return this.schemeId;
    }

    @Override
    public void setSchemeId(EASchemeIDType schemeId) {
        this.schemeId = schemeId;
    }

}
