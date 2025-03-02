package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.ppf.ContractType;
import jakarta.xml.bind.annotation.XmlElement;

public class ReferencedContractType extends AbstractReferencedDocumentType<ContractType> {

    // SubSpecs: EXT-FR-FE-01
    @XmlElement(name = "ReferenceTypeCode")
    protected ContractType referenceTypeCode;

    @Override
    public ContractType getReferenceTypeCode() {
        return referenceTypeCode;
    }

    @Override
    public void setReferenceTypeCode(ContractType value) {
        this.referenceTypeCode = value;
    }

}
