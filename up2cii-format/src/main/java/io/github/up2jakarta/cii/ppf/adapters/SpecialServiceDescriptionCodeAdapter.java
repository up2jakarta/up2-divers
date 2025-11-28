package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * {@link XmlAdapter} mapping of {@link SpecialServiceDescriptionCodeType} to CII (D16B) XML-String.
 */
public class SpecialServiceDescriptionCodeAdapter extends CodeListAdapter<SpecialServiceDescriptionCodeType> {

    public static final SpecialServiceDescriptionCodeAdapter ECE_7161 = new SpecialServiceDescriptionCodeAdapter();

    private SpecialServiceDescriptionCodeAdapter() {
        super(SpecialServiceDescriptionCodeType.class, ERROR, "ECE-7161");
    }

}
