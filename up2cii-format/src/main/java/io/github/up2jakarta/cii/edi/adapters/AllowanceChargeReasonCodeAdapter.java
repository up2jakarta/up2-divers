package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;

import static io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType.values;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.util.Arrays.asList;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeReasonCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AllowanceChargeReasonCodeAdapter extends CodeListConverter<AllowanceChargeReasonCodeType> {

    private static final String CODE = "ECE-4465";
    private static final List<AllowanceChargeReasonCodeType> VALUES = asList(values());

    AllowanceChargeReasonCodeAdapter() {
        super(AllowanceChargeReasonCodeType.class, ERROR, CODE, VALUES);
    }

    public static ChargeReasonCodeType<?> from(ChargeReasonCodeType<?> value) {
        return find(value.getCode(), AllowanceChargeReasonCodeType.class, VALUES, ERROR, CODE);
    }

}
