package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeReasonCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AllowanceChargeReasonCodeAdapter extends CodeListConverter<AllowanceChargeReasonCodeType> {

    public static final String ECE_4465 = "ECE-4465";

    AllowanceChargeReasonCodeAdapter() {
        super(AllowanceChargeReasonCodeType.class, ECE_4465);
    }

}
