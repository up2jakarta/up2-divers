package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.EASchemeIDType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link EASchemeIDType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class EASchemeIDAdapter extends CodeListConverter<EASchemeIDType> {

    EASchemeIDAdapter() {
        super(EASchemeIDType.class, "PPF-BR63");
    }

}
