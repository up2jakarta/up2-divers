package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link CurrencyCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class CurrencyCodeAdapter extends CodeListAdapter<CurrencyCodeType> {

    CurrencyCodeAdapter() {
        super(CurrencyCodeType.class, "ISO-4217");
    }

}