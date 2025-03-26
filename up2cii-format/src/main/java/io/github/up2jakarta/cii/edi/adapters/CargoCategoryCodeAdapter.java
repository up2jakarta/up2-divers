package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.CargoCategoryCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link CargoCategoryCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class CargoCategoryCodeAdapter extends CodeListConverter<CargoCategoryCodeType> {

    CargoCategoryCodeAdapter() {
        super(CargoCategoryCodeType.class, "ECE-R21");
    }

}
