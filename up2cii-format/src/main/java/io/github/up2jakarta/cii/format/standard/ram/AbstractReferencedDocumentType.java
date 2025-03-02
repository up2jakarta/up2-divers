package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.edi.DocumentStatusCodeType;
import io.github.up2jakarta.cii.format.standard.qdt.FormattedDateTimeType;
import io.github.up2jakarta.cii.format.standard.udt.BinaryObjectType;
import io.github.up2jakarta.cii.format.standard.udt.IDType;
import io.github.up2jakarta.cii.format.standard.udt.IndicatorType;
import io.github.up2jakarta.cii.format.standard.udt.TextType;
import io.github.up2jakarta.cii.ppf.ReferenceType;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferencedDocumentType", propOrder = {
        "issuerAssignedId",
        "uriId",
        "statusCode",
        "copyIndicator",
        "lineId",
        "typeCode",
        "globalId",
        "revisionId",
        "name",
        "attachmentBinaryObject",
        "information",
        "referenceTypeCode",
        "sectionName",
        "previousRevisionId",
        "formattedIssueDateTime",
        "effectiveSpecifiedPeriod",
        "issuerTradeParty",
        "attachedSpecifiedBinaryFile"
})
abstract class AbstractReferencedDocumentType<T extends ReferenceType<T>> {

    @XmlElement(name = "IssuerAssignedID")
    private IDType issuerAssignedId;

    @XmlElement(name = "URIID")
    private IDType uriId;

    @XmlElement(name = "StatusCode")
    private DocumentStatusCodeType statusCode;

    @XmlElement(name = "CopyIndicator")
    private IndicatorType copyIndicator;

    @XmlElement(name = "LineID")
    private IDType lineId;

    @XmlElement(name = "TypeCode")
    private DocumentCodeType typeCode;

    @XmlElement(name = "GlobalID")
    private IDType globalId;

    @XmlElement(name = "RevisionID")
    private IDType revisionId;

    @XmlElement(name = "Name")
    private List<TextType> name;

    @XmlElement(name = "AttachmentBinaryObject")
    private List<BinaryObjectType> attachmentBinaryObject;

    @XmlElement(name = "Information")
    private List<TextType> information;

    @XmlElement(name = "SectionName")
    private List<TextType> sectionName;

    @XmlElement(name = "PreviousRevisionID")
    private List<IDType> previousRevisionId;

    @XmlElement(name = "FormattedIssueDateTime")
    private FormattedDateTimeType formattedIssueDateTime;

    @XmlElement(name = "EffectiveSpecifiedPeriod")
    private SpecifiedPeriodType effectiveSpecifiedPeriod;

    @XmlElement(name = "IssuerTradeParty")
    private TradePartyType issuerTradeParty;

    public abstract T getReferenceTypeCode();

    public abstract void setReferenceTypeCode(T referenceTypeCode);

    public IDType getIssuerAssignedId() {
        return issuerAssignedId;
    }

    public void setIssuerAssignedId(IDType issuerAssignedId) {
        this.issuerAssignedId = issuerAssignedId;
    }

    public IDType getUriId() {
        return uriId;
    }

    public void setUriId(IDType uriId) {
        this.uriId = uriId;
    }

    public DocumentStatusCodeType getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(DocumentStatusCodeType statusCode) {
        this.statusCode = statusCode;
    }

    public IndicatorType getCopyIndicator() {
        return copyIndicator;
    }

    public void setCopyIndicator(IndicatorType copyIndicator) {
        this.copyIndicator = copyIndicator;
    }

    public IDType getLineId() {
        return lineId;
    }

    public void setLineId(IDType lineId) {
        this.lineId = lineId;
    }

    public DocumentCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(DocumentCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public IDType getGlobalId() {
        return globalId;
    }

    public void setGlobalId(IDType globalId) {
        this.globalId = globalId;
    }

    public IDType getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(IDType revisionId) {
        this.revisionId = revisionId;
    }

    public List<TextType> getName() {
        return name;
    }

    public void setName(List<TextType> name) {
        this.name = name;
    }

    public List<BinaryObjectType> getAttachmentBinaryObject() {
        return attachmentBinaryObject;
    }

    public void setAttachmentBinaryObject(List<BinaryObjectType> attachmentBinaryObject) {
        this.attachmentBinaryObject = attachmentBinaryObject;
    }

    public List<TextType> getInformation() {
        return information;
    }

    public void setInformation(List<TextType> information) {
        this.information = information;
    }

    public List<TextType> getSectionName() {
        return sectionName;
    }

    public void setSectionName(List<TextType> sectionName) {
        this.sectionName = sectionName;
    }

    public List<IDType> getPreviousRevisionId() {
        return previousRevisionId;
    }

    public void setPreviousRevisionId(List<IDType> previousRevisionId) {
        this.previousRevisionId = previousRevisionId;
    }

    public FormattedDateTimeType getFormattedIssueDateTime() {
        return formattedIssueDateTime;
    }

    public void setFormattedIssueDateTime(FormattedDateTimeType formattedIssueDateTime) {
        this.formattedIssueDateTime = formattedIssueDateTime;
    }

    public SpecifiedPeriodType getEffectiveSpecifiedPeriod() {
        return effectiveSpecifiedPeriod;
    }

    public void setEffectiveSpecifiedPeriod(SpecifiedPeriodType effectiveSpecifiedPeriod) {
        this.effectiveSpecifiedPeriod = effectiveSpecifiedPeriod;
    }

    public TradePartyType getIssuerTradeParty() {
        return issuerTradeParty;
    }

    public void setIssuerTradeParty(TradePartyType issuerTradeParty) {
        this.issuerTradeParty = issuerTradeParty;
    }

}
