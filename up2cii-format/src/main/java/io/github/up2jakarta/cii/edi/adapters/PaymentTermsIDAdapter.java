package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentTermsIDType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentTermsIDType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentTermsIDAdapter extends CodeListConverter<PaymentTermsIDType> {

    PaymentTermsIDAdapter() {
        super(PaymentTermsIDType.class, "ECE-4277");
    }

}
