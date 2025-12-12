package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.FreightChargeTariffClassCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link FreightChargeTariffClassCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class FreightChargeTariffClassCodeAdapter extends CodeListAdapter<FreightChargeTariffClassCodeType> {

    @Inject
    public FreightChargeTariffClassCodeAdapter() {
        super(FreightChargeTariffClassCodeType.class, "ECE-5243");
    }

}
