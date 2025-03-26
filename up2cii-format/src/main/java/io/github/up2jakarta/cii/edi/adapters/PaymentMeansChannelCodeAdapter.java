package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentMeansChannelCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentMeansChannelCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentMeansChannelCodeAdapter extends CodeListConverter<PaymentMeansChannelCodeType> {

    PaymentMeansChannelCodeAdapter() {
        super(PaymentMeansChannelCodeType.class, "ECE-4435");
    }

}
