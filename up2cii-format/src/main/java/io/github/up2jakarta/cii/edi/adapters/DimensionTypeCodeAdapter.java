package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.DimensionTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DimensionTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class DimensionTypeCodeAdapter extends CodeListAdapter<DimensionTypeCodeType> {

    @Inject
    public DimensionTypeCodeAdapter() {
        super(DimensionTypeCodeType.class, "ECE-6145");
    }

}
