package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.LineStatusCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link LineStatusCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class LineStatusCodeAdapter extends CodeListAdapter<LineStatusCodeType> {

    LineStatusCodeAdapter() {
        super(LineStatusCodeType.class, "ECE-1229");
    }

}
