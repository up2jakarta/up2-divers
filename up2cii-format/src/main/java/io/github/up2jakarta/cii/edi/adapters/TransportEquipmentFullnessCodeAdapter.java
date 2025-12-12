package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportEquipmentFullnessCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportEquipmentFullnessCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportEquipmentFullnessCodeAdapter extends CodeListAdapter<TransportEquipmentFullnessCodeType> {

    @Inject
    public TransportEquipmentFullnessCodeAdapter() {
        super(TransportEquipmentFullnessCodeType.class, "ECE-8169");
    }

}
