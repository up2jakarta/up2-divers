package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.ppf.ProfileType;
import jakarta.xml.bind.annotation.XmlElement;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
public class ProfileContextParameterType extends DocumentContextParameterType<ProfileType> {

    // BT-24
    @XmlElement(name = "ID")
    private ProfileType id;

    @Override
    public ProfileType getId() {
        return this.id;
    }

    @Override
    public void setId(ProfileType id) {
        this.id = id;
    }

}
