package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.SchemeCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link SchemeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class SchemeCodeAdapter extends CodeListConverter<SchemeCodeType> {

    SchemeCodeAdapter() {
        super(SchemeCodeType.class, "ISO-6523");
    }

}
