package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeIdentificationCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AllowanceChargeIdentificationCodeAdapter extends CodeListConverter<AllowanceChargeIdentificationCodeType> {

    public static final String ECE_5189 = "ECE-5189";

    AllowanceChargeIdentificationCodeAdapter() {
        super(AllowanceChargeIdentificationCodeType.class, ECE_5189);
    }

}
