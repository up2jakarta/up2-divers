package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.ProfileType;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.SeverityType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ProfileType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ProfileAdapter extends CodeListConverter<ProfileType> {

    ProfileAdapter() {
        super(ProfileType.class, "PPF-S106");
    }

    @Override
    public ProfileType parse(String value) throws CodeListException {
        if (!ProfileType.EN_16931.getCode().equals(value)) {
            throw new CodeListException(ProfileType.class, value, SeverityType.ERROR, getErrorCode());
        }
        return ProfileType.EN_16931;
    }

}
