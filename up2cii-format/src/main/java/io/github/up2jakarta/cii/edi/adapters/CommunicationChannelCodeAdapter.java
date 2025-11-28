package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.CommunicationChannelCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link CommunicationChannelCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class CommunicationChannelCodeAdapter extends CodeListAdapter<CommunicationChannelCodeType> {

    CommunicationChannelCodeAdapter() {
        super(CommunicationChannelCodeType.class, "ECE-3155");
    }

}
