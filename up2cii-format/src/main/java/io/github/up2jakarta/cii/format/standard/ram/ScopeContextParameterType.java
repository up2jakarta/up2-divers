package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.ppf.ScopeType;
import jakarta.xml.bind.annotation.XmlElement;

public class ScopeContextParameterType extends AbstractContextParameterType<ScopeType> {

    @XmlElement(name = "ID")
    private ScopeType id;

    /**
     * {@inheritDoc}
     **/
    @Override
    public ScopeType getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setID(ScopeType value) {
        this.id = value;
    }
}
