package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.MimeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link MimeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class MimeCodeAdapter extends CodeListAdapter<MimeCodeType> {

    @Inject
    public MimeCodeAdapter() {
        super(MimeCodeType.class, "PPF-G417");
    }

}