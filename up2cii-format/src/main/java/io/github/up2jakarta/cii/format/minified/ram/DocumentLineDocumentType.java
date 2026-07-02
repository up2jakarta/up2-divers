package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentLineDocumentType", propOrder = {
        "lineId",
        "includedNote"
})
public class DocumentLineDocumentType {

    // BT-126
    @XmlElement(name = "LineID")
    private String lineId;

    // BT-127-00
    @XmlElement(name = "IncludedNote")
    private List<NoteType> includedNote;

    public String getLineId() {
        return this.lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public List<NoteType> getIncludedNote() {
        return this.includedNote;
    }

    public void setIncludedNote(List<NoteType> includedNote) {
        this.includedNote = includedNote;
    }

}
