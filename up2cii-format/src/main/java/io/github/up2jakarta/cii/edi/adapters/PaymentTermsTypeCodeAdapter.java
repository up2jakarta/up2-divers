package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentTermsTypeCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentTermsTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentTermsTypeCodeAdapter extends CodeListConverter<PaymentTermsTypeCodeType> {

    PaymentTermsTypeCodeAdapter() {
        super(PaymentTermsTypeCodeType.class, "ECE-4279");
    }

}
