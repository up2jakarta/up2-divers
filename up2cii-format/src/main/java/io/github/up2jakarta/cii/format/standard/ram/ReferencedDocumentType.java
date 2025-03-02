package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.edi.ReferenceCodeType;
import jakarta.xml.bind.annotation.XmlElement;

public class ReferencedDocumentType extends AbstractReferencedDocumentType<ReferenceCodeType> {

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
