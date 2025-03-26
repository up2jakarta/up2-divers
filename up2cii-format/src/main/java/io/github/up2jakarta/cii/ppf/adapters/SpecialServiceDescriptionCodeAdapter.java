package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link SpecialServiceDescriptionCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class SpecialServiceDescriptionCodeAdapter extends CodeListConverter<SpecialServiceDescriptionCodeType> {

    public static final String ECE_7161 = "ECE-7161";

    SpecialServiceDescriptionCodeAdapter() {
        super(SpecialServiceDescriptionCodeType.class, ECE_7161);
    }

}
