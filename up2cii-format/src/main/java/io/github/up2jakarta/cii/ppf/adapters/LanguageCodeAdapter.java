package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.LanguageCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link LanguageCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class LanguageCodeAdapter extends CodeListConverter<LanguageCodeType> {

    LanguageCodeAdapter() {
        super(LanguageCodeType.class, "ISO-639");
    }

}
