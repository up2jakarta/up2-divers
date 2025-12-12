package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.GoodsTypeExtensionCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link GoodsTypeExtensionCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class GoodsTypeExtensionCodeAdapter extends CodeListAdapter<GoodsTypeExtensionCodeType> {

    @Inject
    public GoodsTypeExtensionCodeAdapter() {
        super(GoodsTypeExtensionCodeType.class, "ECE-7361");
    }

}
