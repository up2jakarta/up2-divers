package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.CargoCommodityCategoryCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link CargoCommodityCategoryCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class CargoCommodityCategoryCodeAdapter extends CodeListAdapter<CargoCommodityCategoryCodeType> {

    @Inject
    public CargoCommodityCategoryCodeAdapter() {
        super(CargoCommodityCategoryCodeType.class, "ECE-7357");
    }

}
