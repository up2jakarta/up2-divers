package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TaxCategoryCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TaxCategoryCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TaxCategoryCodeAdapter extends CodeListConverter<TaxCategoryCodeType> {

    TaxCategoryCodeAdapter() {
        super(TaxCategoryCodeType.class, "ECE-5305");
    }

}
