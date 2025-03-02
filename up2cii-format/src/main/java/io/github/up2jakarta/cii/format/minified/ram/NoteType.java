package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.ppf.SubjectCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "NoteType", propOrder = {
        "content",
        "subjectCode"
})
public class NoteType {

    // BT-22, BT-127
    private String content;

    // BT-21, EXT-FR-FE-183
    private SubjectCodeType subjectCode;

    @XmlElement(name = "Content")
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlElement(name = "SubjectCode")
    public SubjectCodeType getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(SubjectCodeType subjectCode) {
        this.subjectCode = subjectCode;
    }

}
