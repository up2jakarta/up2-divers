package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PackagingMarkingCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PackagingMarkingCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PackagingMarkingCodeAdapter extends CodeListConverter<PackagingMarkingCodeType> {

    PackagingMarkingCodeAdapter() {
        super(PackagingMarkingCodeType.class, "ECE-7233");
    }

}
