package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;

import static io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType.values;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Arrays.asList;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeReasonCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AllowanceChargeReasonCodeAdapter extends CodeListConverter<AllowanceChargeReasonCodeType> {

    public static final String ECE_4465 = "ECE-4465";
    public static final List<AllowanceChargeReasonCodeType> LOV_4465 = asList(values());

    AllowanceChargeReasonCodeAdapter() {
        super(AllowanceChargeReasonCodeType.class, ERROR, ECE_4465, LOV_4465);
    }

}
