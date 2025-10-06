package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportEquipmentCategoryCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportEquipmentCategoryCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportEquipmentCategoryCodeAdapter extends CodeListConverter<TransportEquipmentCategoryCodeType> {

    TransportEquipmentCategoryCodeAdapter() {
        super(TransportEquipmentCategoryCodeType.class, "ECE-8053");
    }

}
