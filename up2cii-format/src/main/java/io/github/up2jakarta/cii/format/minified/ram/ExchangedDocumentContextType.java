package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangedDocumentContextType", propOrder = {
        "businessProcessSpecifiedDocumentContextParameter",
        "guidelineSpecifiedDocumentContextParameter"
})
public class ExchangedDocumentContextType {

    @XmlElement(name = "BusinessProcessSpecifiedDocumentContextParameter")
    private ScopeContextParameterType businessProcessSpecifiedDocumentContextParameter;

    @XmlElement(name = "GuidelineSpecifiedDocumentContextParameter")
    private ProfileContextParameterType guidelineSpecifiedDocumentContextParameter;

    public ScopeContextParameterType getBusinessProcessSpecifiedDocumentContextParameter() {
        return this.businessProcessSpecifiedDocumentContextParameter;
    }

    public void setBusinessProcessSpecifiedDocumentContextParameter(ScopeContextParameterType businessProcessSpecifiedDocumentContextParameter) {
        this.businessProcessSpecifiedDocumentContextParameter = businessProcessSpecifiedDocumentContextParameter;
    }

    public ProfileContextParameterType getGuidelineSpecifiedDocumentContextParameter() {
        return this.guidelineSpecifiedDocumentContextParameter;
    }

    public void setGuidelineSpecifiedDocumentContextParameter(ProfileContextParameterType guidelineSpecifiedDocumentContextParameter) {
        this.guidelineSpecifiedDocumentContextParameter = guidelineSpecifiedDocumentContextParameter;
    }

}
