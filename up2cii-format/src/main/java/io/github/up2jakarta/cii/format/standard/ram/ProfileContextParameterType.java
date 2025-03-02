package io.github.up2jakarta.cii.format.standard.ram;

import io.github.up2jakarta.cii.ppf.ProfileType;
import jakarta.xml.bind.annotation.XmlElement;

public class ProfileContextParameterType extends AbstractContextParameterType<ProfileType> {

    @XmlElement(name = "ID")
    private ProfileType id;

    /**
     * {@inheritDoc}
     **/
    @Override
    public ProfileType getID() {
        return id;
    }

    /**
     * {@inheritDoc}
     **/
    @Override
    public void setID(ProfileType value) {
        this.id = value;
    }
}
