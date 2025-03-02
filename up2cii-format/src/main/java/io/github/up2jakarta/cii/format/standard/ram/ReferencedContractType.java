package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.ppf.ContractType;
import jakarta.xml.bind.annotation.XmlElement;

public class ReferencedContractType extends AbstractReferencedDocumentType<ContractType> {

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
