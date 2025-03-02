package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DocumentLineDocumentType", propOrder = {
        "lineId",
        "includedNote"
})
public class DocumentLineDocumentType {

    // BT-126
    private String lineId;

    // BT-127-00
    private List<NoteType> includedNote;

    @XmlElement(name = "LineID")
    public String getLineId() {
        return this.lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    @XmlElement(name = "IncludedNote")
    public List<NoteType> getIncludedNote() {
        return this.includedNote;
    }

    public void setIncludedNote(List<NoteType> includedNote) {
        this.includedNote = includedNote;
    }

}
