package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.format.minified.qdt.FormattedDateTimeType;
import io.github.up2jakarta.cii.format.minified.udt.BinaryObjectType;
import io.github.up2jakarta.cii.ppf.ReferenceType;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferencedDocumentType", propOrder = {
        "issuerAssignedId",
        "uriId",
        "lineId",
        "typeCode",
        "name",
        "attachmentBinaryObject",
        "referenceTypeCode",
        "formattedIssueDateTime",
})
abstract class AbstractReferencedDocumentType<T extends ReferenceType<T>> {

    // SubSpecs: BT-12, BT-13, BT-14, BT-15, BT-16, BT-17, BT-18, BT-25, BT-122, BT-128, EXT-FR-FE-135, EXT-FR-FE-136
    // SubSpecs: EXT-FR-FE-140, EXT-FR-FE-142, EXT-FR-FE-144
    @XmlElement(name = "IssuerAssignedID")
    private String issuerAssignedId;

    // SubSpecs: BT-124
    @XmlElement(name = "URIID")
    private String uriId;

    // SubSpecs: BT-132, EXT-FR-FE-139, EXT-FR-FE-141, EXT-FR-FE-143, EXT-FR-FE-145
    @XmlElement(name = "LineID")
    private String lineId;

    // SubSpecs: EXT-FR-FE-02, EXT-FR-FE-137
    @XmlElement(name = "TypeCode")
    private DocumentCodeType typeCode;

    // SubSpecs: BT-123
    @XmlElement(name = "Name")
    private String name;

    // SubSpecs: BT-125
    @XmlElement(name = "AttachmentBinaryObject")
    private List<BinaryObjectType> attachmentBinaryObject;

    @XmlElement(name = "FormattedIssueDateTime")
    private FormattedDateTimeType formattedIssueDateTime;

    public abstract T getReferenceTypeCode();

    public abstract void setReferenceTypeCode(T referenceTypeCode);

    public String getIssuerAssignedId() {
        return issuerAssignedId;
    }

    public void setIssuerAssignedId(String issuerAssignedId) {
        this.issuerAssignedId = issuerAssignedId;
    }

    public String getUriId() {
        return uriId;
    }

    public void setUriId(String uriId) {
        this.uriId = uriId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public DocumentCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(DocumentCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BinaryObjectType> getAttachmentBinaryObject() {
        return attachmentBinaryObject;
    }

    public void setAttachmentBinaryObject(List<BinaryObjectType> attachmentBinaryObject) {
        this.attachmentBinaryObject = attachmentBinaryObject;
    }

    public FormattedDateTimeType getFormattedIssueDateTime() {
        return formattedIssueDateTime;
    }

    public void setFormattedIssueDateTime(FormattedDateTimeType formattedIssueDateTime) {
        this.formattedIssueDateTime = formattedIssueDateTime;
    }
}
