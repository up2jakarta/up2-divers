package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.FreightChargeTariffClassCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link FreightChargeTariffClassCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class FreightChargeTariffClassCodeAdapter extends CodeListConverter<FreightChargeTariffClassCodeType> {

    FreightChargeTariffClassCodeAdapter() {
        super(FreightChargeTariffClassCodeType.class, "ECE-5243");
    }

}
