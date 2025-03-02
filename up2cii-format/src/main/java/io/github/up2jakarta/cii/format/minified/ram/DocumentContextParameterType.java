package io.github.up2jakarta.cii.format.minified.ram;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlTransient
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "DocumentContextParameterType", propOrder = {"id"})
public abstract class DocumentContextParameterType<I> {

    public abstract I getId();

    public abstract void setId(I id);

}
