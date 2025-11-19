package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.DimensionTypeCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DimensionTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class DimensionTypeCodeAdapter extends CodeListConverter<DimensionTypeCodeType> {

    DimensionTypeCodeAdapter() {
        super(DimensionTypeCodeType.class, "ECE-6145");
    }

}
