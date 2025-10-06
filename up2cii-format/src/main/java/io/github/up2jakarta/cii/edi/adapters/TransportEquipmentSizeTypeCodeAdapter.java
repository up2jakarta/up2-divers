package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportEquipmentSizeTypeCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportEquipmentSizeTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportEquipmentSizeTypeCodeAdapter extends CodeListConverter<TransportEquipmentSizeTypeCodeType> {

    TransportEquipmentSizeTypeCodeAdapter() {
        super(TransportEquipmentSizeTypeCodeType.class, "ECE-8155");
    }

}
