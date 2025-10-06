package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.ChargePayingPartyRoleCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ChargePayingPartyRoleCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ChargePayingPartyRoleCodeAdapter extends CodeListConverter<ChargePayingPartyRoleCodeType> {

    ChargePayingPartyRoleCodeAdapter() {
        super(ChargePayingPartyRoleCodeType.class, "ECE-3035");
    }

}