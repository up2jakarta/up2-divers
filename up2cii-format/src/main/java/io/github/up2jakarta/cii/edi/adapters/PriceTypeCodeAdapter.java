package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PriceTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PriceTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PriceTypeCodeAdapter extends CodeListAdapter<PriceTypeCodeType> {

    PriceTypeCodeAdapter() {
        super(PriceTypeCodeType.class, "ECE-5375");
    }

}
