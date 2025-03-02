package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.format.standard.udt.IDType;
import jakarta.xml.bind.annotation.XmlElement;

public class DocumentContextParameterType extends AbstractContextParameterType<IDType> {

    @XmlElement(name = "ID")
    private IDType id;

    /**
     * {@inheritDoc}
     **/
    @Override
    public IDType getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setID(IDType value) {
        this.id = value;
    }
}
