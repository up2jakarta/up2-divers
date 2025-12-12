package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentTermsEventTimeReferenceCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentTermsEventTimeReferenceCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentTermsEventTimeReferenceCodeAdapter extends CodeListAdapter<PaymentTermsEventTimeReferenceCodeType> {

    @Inject
    public PaymentTermsEventTimeReferenceCodeAdapter() {
        super(PaymentTermsEventTimeReferenceCodeType.class, "ECE-2475");
    }

}