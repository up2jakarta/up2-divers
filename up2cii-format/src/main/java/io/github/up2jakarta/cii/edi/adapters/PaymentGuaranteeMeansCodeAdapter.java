package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PaymentGuaranteeMeansCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PaymentGuaranteeMeansCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PaymentGuaranteeMeansCodeAdapter extends CodeListAdapter<PaymentGuaranteeMeansCodeType> {

    @Inject
    public PaymentGuaranteeMeansCodeAdapter() {
        super(PaymentGuaranteeMeansCodeType.class, "ECE-4431");
    }

}