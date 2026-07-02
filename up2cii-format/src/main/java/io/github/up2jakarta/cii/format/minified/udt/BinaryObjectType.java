package io.github.up2jakarta.cii.format.minified.udt;

import io.github.up2jakarta.cii.ppf.MimeCodeType;
import jakarta.xml.bind.annotation.*;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryObjectType", propOrder = {"value"})
public class BinaryObjectType {

    // BT-125
    @XmlValue
    private byte[] value;

    // BT-125-1
    @XmlAttribute(name = "mimeCode")
    private MimeCodeType mimeCode;

    // BT-125-2
    @XmlAttribute(name = "filename")
    private String filename;

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public MimeCodeType getMimeCode() {
        return this.mimeCode;
    }

    public void setMimeCode(MimeCodeType mimeCode) {
        this.mimeCode = mimeCode;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
