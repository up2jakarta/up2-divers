package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;

import static io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType.values;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Arrays.asList;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeIdentificationCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AllowanceChargeIdentificationCodeAdapter extends CodeListConverter<AllowanceChargeIdentificationCodeType> {

    public static final String ECE_5189 = "ECE-5189";
    public static final List<AllowanceChargeIdentificationCodeType> LOV_5189 = asList(values());

    AllowanceChargeIdentificationCodeAdapter() {
        super(AllowanceChargeIdentificationCodeType.class, ERROR, ECE_5189, LOV_5189);
    }

}
