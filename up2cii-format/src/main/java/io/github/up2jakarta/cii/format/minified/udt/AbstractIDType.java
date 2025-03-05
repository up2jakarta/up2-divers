package io.github.up2jakarta.cii.format.minified.udt;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlTransient
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "IDType", propOrder = {"value"})
public abstract class AbstractIDType<S> {

    protected String value;

    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public abstract S getSchemeId();

    public abstract void setSchemeId(S schemeId);

}
