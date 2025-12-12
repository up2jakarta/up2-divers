package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportModeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportModeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportModeCodeAdapter extends CodeListAdapter<TransportModeCodeType> {

    @Inject
    public TransportModeCodeAdapter() {
        super(TransportModeCodeType.class, "ECE-R19");
    }

}
