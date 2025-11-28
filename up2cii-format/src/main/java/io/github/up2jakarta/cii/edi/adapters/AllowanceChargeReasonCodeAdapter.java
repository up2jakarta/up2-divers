package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * {@link XmlAdapter} mapping of {@link AllowanceChargeReasonCodeType} to CII (D16B) XML-String.
 */
public class AllowanceChargeReasonCodeAdapter extends CodeListAdapter<AllowanceChargeReasonCodeType> {

    public static final AllowanceChargeReasonCodeAdapter ECE_4465 = new AllowanceChargeReasonCodeAdapter();

    private AllowanceChargeReasonCodeAdapter() {
        super(AllowanceChargeReasonCodeType.class, ERROR, "ECE-4465");
    }

}
