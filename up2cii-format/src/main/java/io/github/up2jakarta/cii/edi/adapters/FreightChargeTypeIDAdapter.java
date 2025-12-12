package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.FreightChargeTypeIDType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link FreightChargeTypeIDType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class FreightChargeTypeIDAdapter extends CodeListAdapter<FreightChargeTypeIDType> {

    @Inject
    public FreightChargeTypeIDAdapter() {
        super(FreightChargeTypeIDType.class, "ECE-R23");
    }

}
