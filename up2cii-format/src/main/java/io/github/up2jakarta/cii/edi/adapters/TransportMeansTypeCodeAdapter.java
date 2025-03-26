package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportMeansTypeCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportMeansTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportMeansTypeCodeAdapter extends CodeListConverter<TransportMeansTypeCodeType> {

    TransportMeansTypeCodeAdapter() {
        super(TransportMeansTypeCodeType.class, "ECE-R28");
    }

}
