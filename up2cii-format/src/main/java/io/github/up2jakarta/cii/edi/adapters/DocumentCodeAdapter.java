package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DocumentCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class DocumentCodeAdapter extends CodeListAdapter<DocumentCodeType> {

    @Inject
    public DocumentCodeAdapter() {
        super(DocumentCodeType.class, "ECE-1001");
    }

}