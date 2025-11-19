package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.SubjectCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link SubjectCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class SubjectCodeAdapter extends CodeListConverter<SubjectCodeType> {

    SubjectCodeAdapter() {
        super(SubjectCodeType.class, "ECE-4451");
    }

}
