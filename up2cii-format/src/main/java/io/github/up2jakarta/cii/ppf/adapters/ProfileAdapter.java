package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.ProfileType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ProfileType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ProfileAdapter extends CodeListAdapter<ProfileType> {

    @Inject
    public ProfileAdapter() {
        super(ProfileType.class, "PPF-S106");
    }

}
