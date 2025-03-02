package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PartyRoleCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PartyRoleCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PartyRoleCodeAdapter extends CodeListConverter<PartyRoleCodeType> {

    PartyRoleCodeAdapter() {
        super(PartyRoleCodeType.class, "ECE-3035");
    }

}
