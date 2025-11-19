package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.ReferenceCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ReferenceCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ReferenceCodeAdapter extends CodeListConverter<ReferenceCodeType> {

    ReferenceCodeAdapter() {
        super(ReferenceCodeType.class, "ECE-1153");
    }

}