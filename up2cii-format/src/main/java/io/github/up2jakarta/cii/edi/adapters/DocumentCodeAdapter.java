package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DocumentCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class DocumentCodeAdapter extends CodeListConverter<DocumentCodeType> {

    DocumentCodeAdapter() {
        super(DocumentCodeType.class, "ECE-1001");
    }

}