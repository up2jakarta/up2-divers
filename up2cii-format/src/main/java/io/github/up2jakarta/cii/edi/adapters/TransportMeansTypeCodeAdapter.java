package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportMeansTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportMeansTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportMeansTypeCodeAdapter extends CodeListAdapter<TransportMeansTypeCodeType> {

    @Inject
    public TransportMeansTypeCodeAdapter() {
        super(TransportMeansTypeCodeType.class, "ECE-R28");
    }

}
