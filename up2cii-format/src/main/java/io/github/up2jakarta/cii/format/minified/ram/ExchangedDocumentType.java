package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.format.minified.udt.DateTimeType;
import io.github.up2jakarta.cii.ppf.InvoiceCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangedDocumentType", propOrder = {
        "id",
        "typeCode",
        "issueDateTime",
        "includedNote"
})
public class ExchangedDocumentType {

    // BT-1
    @XmlElement(name = "ID", required = true)
    private String id;

    // BT-3
    @XmlElement(name = "TypeCode")
    private InvoiceCodeType typeCode;

    @XmlElement(name = "IssueDateTime", required = true)
    private DateTimeType issueDateTime;

    // BG-1
    @XmlElement(name = "IncludedNote")
    private List<NoteType> includedNote;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvoiceCodeType getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(InvoiceCodeType typeCode) {
        this.typeCode = typeCode;
    }

    public DateTimeType getIssueDateTime() {
        return this.issueDateTime;
    }

    public void setIssueDateTime(DateTimeType issueDateTime) {
        this.issueDateTime = issueDateTime;
    }

    public List<NoteType> getIncludedNote() {
        return this.includedNote;
    }

    public void setIncludedNote(List<NoteType> includedNote) {
        this.includedNote = includedNote;
    }

}
