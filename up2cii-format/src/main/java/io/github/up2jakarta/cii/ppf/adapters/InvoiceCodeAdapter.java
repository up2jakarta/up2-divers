package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.edi.DocumentCodeType;
import io.github.up2jakarta.cii.ppf.InvoiceCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link DocumentCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class InvoiceCodeAdapter extends CodeListConverter<InvoiceCodeType> {

    InvoiceCodeAdapter() {
        super(InvoiceCodeType.class, "PPF-G101");
    }

}