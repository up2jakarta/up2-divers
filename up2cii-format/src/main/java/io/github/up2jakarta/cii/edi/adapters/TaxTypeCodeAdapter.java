package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TaxTypeCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;


/**
 * {@link XmlAdapter} mapping of {@link TaxTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TaxTypeCodeAdapter extends CodeListConverter<TaxTypeCodeType> {

    TaxTypeCodeAdapter() {
        super(TaxTypeCodeType.class, "ECE-5153");
    }

}
