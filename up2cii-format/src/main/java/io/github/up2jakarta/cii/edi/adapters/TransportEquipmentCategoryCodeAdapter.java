package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TransportEquipmentCategoryCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TransportEquipmentCategoryCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TransportEquipmentCategoryCodeAdapter extends CodeListAdapter<TransportEquipmentCategoryCodeType> {

    @Inject
    public TransportEquipmentCategoryCodeAdapter() {
        super(TransportEquipmentCategoryCodeType.class, "ECE-8053");
    }

}
