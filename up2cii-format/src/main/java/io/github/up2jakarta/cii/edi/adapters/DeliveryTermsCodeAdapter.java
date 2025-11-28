package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.DeliveryTermsCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DeliveryTermsCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class DeliveryTermsCodeAdapter extends CodeListAdapter<DeliveryTermsCodeType> {

    DeliveryTermsCodeAdapter() {
        super(DeliveryTermsCodeType.class, "ECE-4053");
    }

}
