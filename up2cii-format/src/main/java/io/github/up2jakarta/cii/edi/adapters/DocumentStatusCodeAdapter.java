package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.DocumentStatusCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DocumentStatusCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class DocumentStatusCodeAdapter extends CodeListConverter<DocumentStatusCodeType> {

    DocumentStatusCodeAdapter() {
        super(DocumentStatusCodeType.class, "ECE-1373");
    }

}
