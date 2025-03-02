package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentMeansCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentMeansCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentMeansCodeAdapter extends CodeListConverter<PaymentMeansCodeType> {

    PaymentMeansCodeAdapter() {
        super(PaymentMeansCodeType.class, "ECE-4461");
    }

}