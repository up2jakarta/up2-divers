package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.edi.ReferenceCodeType;
import io.github.up2jakarta.cii.format.minified.qdt.FormattedDateTimeType;
import io.github.up2jakarta.cii.format.minified.udt.BinaryObjectType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "ReferencedDocumentType", propOrder = {
        "issuerAssignedId",
        "uriId",
        "lineId",
        "typeCode",
        "name",
        "attachmentBinaryObject",
        "referenceTypeCode",
        "formattedIssueDateTime"
})
public class ReferencedDocumentType {

    // BT-12, BT-13, BT-14, BT-15, BT-16, BT-17, BT-18, BT-25, BT-122, BT-128, EXT-FR-FE-135 and (4) specifications too.
    private String issuerAssignedId;

    // BT-124
    private String uriId;

    // BT-132, EXT-FR-FE-139, EXT-FR-FE-141, EXT-FR-FE-143, EXT-FR-FE-145
    private String lineId;

    // EXT-FR-FE-02, EXT-FR-FE-137
    private DocumentCodeType typeCode;

    // BT-123
    private String name;

    // BT-125
    private BinaryObjectType attachmentBinaryObject;

    // EXT-FR-FE-01, BT-18-1, BT-128-1
    private ReferenceCodeType referenceTypeCode;

    private FormattedDateTimeType formattedIssueDateTime;

    @XmlElement(name = "IssuerAssignedID")
    public String getIssuerAssignedId() {
        return this.issuerAssignedId;
    }

    public void setIssuerAssignedId(String issuerAssignedId) {
        this.issuerAssignedId = issuerAssignedId;
    }

    @XmlElement(name = "URIID")
    public String getUriId() {
        return this.uriId;
    }

    public void setUriId(String uriId) {
        this.uriId = uriId;
    }

    @XmlElement(name = "LineID")
    public String getLineId() {
        return this.lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    @XmlElement(name = "TypeCode")
    public DocumentCodeType getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(DocumentCodeType typeCode) {
        this.typeCode = typeCode;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "AttachmentBinaryObject")
    public BinaryObjectType getAttachmentBinaryObject() {
        return this.attachmentBinaryObject;
    }

    public void setAttachmentBinaryObject(BinaryObjectType attachmentBinaryObject) {
        this.attachmentBinaryObject = attachmentBinaryObject;
    }

    @XmlElement(name = "ReferenceTypeCode")
    public ReferenceCodeType getReferenceTypeCode() {
        return this.referenceTypeCode;
    }

    public void setReferenceTypeCode(ReferenceCodeType referenceTypeCode) {
        this.referenceTypeCode = referenceTypeCode;
    }

    @XmlElement(name = "FormattedIssueDateTime")
    public FormattedDateTimeType getFormattedIssueDateTime() {
        return this.formattedIssueDateTime;
    }

    public void setFormattedIssueDateTime(FormattedDateTimeType formattedIssueDateTime) {
        this.formattedIssueDateTime = formattedIssueDateTime;
    }

}
