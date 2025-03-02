package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.ReferenceCodeType;
import jakarta.xml.bind.annotation.XmlElement;

public class ReferencedDocumentType extends AbstractReferencedDocumentType<ReferenceCodeType> {

    // SubSpecs: BT-18-1, BT-128-1
    @XmlElement(name = "ReferenceTypeCode")
    protected ReferenceCodeType referenceTypeCode;

    @Override
    public ReferenceCodeType getReferenceTypeCode() {
        return referenceTypeCode;
    }

    @Override
    public void setReferenceTypeCode(ReferenceCodeType value) {
        this.referenceTypeCode = value;
    }

}
