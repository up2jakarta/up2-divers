package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentTermsTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentTermsTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentTermsTypeCodeAdapter extends CodeListAdapter<PaymentTermsTypeCodeType> {

    @Inject
    public PaymentTermsTypeCodeAdapter() {
        super(PaymentTermsTypeCodeType.class, "ECE-4279");
    }

}
