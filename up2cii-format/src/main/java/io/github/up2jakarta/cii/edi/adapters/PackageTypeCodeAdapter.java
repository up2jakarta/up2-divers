package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.PackageTypeCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link PackageTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class PackageTypeCodeAdapter extends CodeListConverter<PackageTypeCodeType> {

    PackageTypeCodeAdapter() {
        super(PackageTypeCodeType.class, "ECE-7065");
    }

}
