package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentTermsEventTimeReferenceCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentTermsEventTimeReferenceCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentTermsEventTimeReferenceCodeAdapter extends CodeListConverter<PaymentTermsEventTimeReferenceCodeType> {

    PaymentTermsEventTimeReferenceCodeAdapter() {
        super(PaymentTermsEventTimeReferenceCodeType.class, "ECE-2475");
    }

}