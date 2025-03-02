package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.MimeCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link MimeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class MimeCodeAdapter extends CodeListConverter<MimeCodeType> {

    MimeCodeAdapter() {
        super(MimeCodeType.class, "PPF-G417");
    }

}