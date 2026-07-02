package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.ppf.ScopeType;
import jakarta.xml.bind.annotation.XmlElement;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
public class ScopeContextParameterType extends DocumentContextParameterType<ScopeType> {

    // BT-23
    @XmlElement(name = "ID")
    private ScopeType id;

    @Override
    public ScopeType getId() {
        return this.id;
    }

    @Override
    public void setId(ScopeType id) {
        this.id = id;
    }

}
