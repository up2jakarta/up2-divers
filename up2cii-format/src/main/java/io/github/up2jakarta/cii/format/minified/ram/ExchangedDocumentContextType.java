package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ExchangedDocumentContextType", propOrder = {
        "businessProcessSpecifiedDocumentContextParameter",
        "guidelineSpecifiedDocumentContextParameter"
})
public class ExchangedDocumentContextType {

    private ScopeContextParameterType businessProcessSpecifiedDocumentContextParameter;

    private ProfileContextParameterType guidelineSpecifiedDocumentContextParameter;

    @XmlElement(name = "BusinessProcessSpecifiedDocumentContextParameter")
    public ScopeContextParameterType getBusinessProcessSpecifiedDocumentContextParameter() {
        return this.businessProcessSpecifiedDocumentContextParameter;
    }

    public void setBusinessProcessSpecifiedDocumentContextParameter(ScopeContextParameterType businessProcessSpecifiedDocumentContextParameter) {
        this.businessProcessSpecifiedDocumentContextParameter = businessProcessSpecifiedDocumentContextParameter;
    }

    @XmlElement(name = "GuidelineSpecifiedDocumentContextParameter")
    public ProfileContextParameterType getGuidelineSpecifiedDocumentContextParameter() {
        return this.guidelineSpecifiedDocumentContextParameter;
    }

    public void setGuidelineSpecifiedDocumentContextParameter(ProfileContextParameterType guidelineSpecifiedDocumentContextParameter) {
        this.guidelineSpecifiedDocumentContextParameter = guidelineSpecifiedDocumentContextParameter;
    }

}
