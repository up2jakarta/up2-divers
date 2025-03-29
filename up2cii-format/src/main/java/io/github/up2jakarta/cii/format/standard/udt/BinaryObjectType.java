package io.github.up2jakarta.cii.format.standard.udt;

import io.github.up2jakarta.cii.ppf.MimeCodeType;
import io.github.up2jakarta.xml.adapters.CharsetAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.nio.charset.Charset;

@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryObjectType", propOrder = {"value"})
public class BinaryObjectType {

    @XmlValue
    protected byte[] value;

    @XmlAttribute(name = "format")
    protected String format;

    @XmlAttribute(name = "mimeCode")
    protected MimeCodeType mimeCode;

    @XmlAttribute(name = "encodingCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String encodingCode;

    @XmlAttribute(name = "characterSetCode")
    @XmlJavaTypeAdapter(CharsetAdapter.class)
    protected Charset charset;

    @XmlAttribute(name = "uri")
    @XmlSchemaType(name = "anyURI")
    protected String uri;

    @XmlAttribute(name = "filename")
    protected String filename;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is byte[]
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is byte[]
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * Gets the value of the format property.
     *
     * @return possible object is {@link String }
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     *
     * @param value allowed object is {@link String }
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the mimeCode property.
     *
     * @return possible object is {@link MimeCodeType }
     */
    public MimeCodeType getMimeCode() {
        return mimeCode;
    }

    /**
     * Sets the value of the mimeCode property.
     *
     * @param value allowed object is {@link MimeCodeType }
     */
    public void setMimeCode(MimeCodeType value) {
        this.mimeCode = value;
    }

    /**
     * Gets the value of the encodingCode property.
     *
     * @return possible object is {@link String }
     */
    public String getEncodingCode() {
        return encodingCode;
    }

    /**
     * Sets the value of the encodingCode property.
     *
     * @param value allowed object is {@link String }
     */
    public void setEncodingCode(String value) {
        this.encodingCode = value;
    }

    /**
     * Gets the value of the characterSetCode property.
     *
     * @return possible object is {@link Charset }
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Sets the value of the characterSetCode property.
     *
     * @param value allowed object is {@link Charset }
     */
    public void setCharset(Charset value) {
        this.charset = value;
    }

    /**
     * Gets the value of the uri property.
     *
     * @return possible object is {@link String }
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     *
     * @param value allowed object is {@link String }
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the filename property.
     *
     * @return possible object is {@link String }
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     *
     * @param value allowed object is {@link String }
     */
    public void setFilename(String value) {
        this.filename = value;
    }

}
