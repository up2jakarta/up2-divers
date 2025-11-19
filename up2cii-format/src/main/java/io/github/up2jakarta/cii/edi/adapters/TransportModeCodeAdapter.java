package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportModeCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportModeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportModeCodeAdapter extends CodeListConverter<TransportModeCodeType> {

    TransportModeCodeAdapter() {
        super(TransportModeCodeType.class, "ECE-R19");
    }

}
