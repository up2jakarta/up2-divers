package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeIdentificationCodeType} to CII (D16B) XML-String.
 */
public class AllowanceChargeIdentificationCodeAdapter extends CodeListAdapter<AllowanceChargeIdentificationCodeType> {

    public static final AllowanceChargeIdentificationCodeAdapter ECE_5189 = new AllowanceChargeIdentificationCodeAdapter();

    private AllowanceChargeIdentificationCodeAdapter() {
        super(AllowanceChargeIdentificationCodeType.class, ERROR, "ECE-5189");
    }

}
