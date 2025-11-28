package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.GoodsTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link GoodsTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class GoodsTypeCodeAdapter extends CodeListAdapter<GoodsTypeCodeType> {

    GoodsTypeCodeAdapter() {
        super(GoodsTypeCodeType.class, "ECE-7357");
    }

}
