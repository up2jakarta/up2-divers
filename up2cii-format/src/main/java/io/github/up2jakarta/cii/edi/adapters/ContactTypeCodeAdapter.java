package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.ContactTypeCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ContactTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ContactTypeCodeAdapter extends CodeListConverter<ContactTypeCodeType> {

    ContactTypeCodeAdapter() {
        super(ContactTypeCodeType.class, "ECE-3139");
    }

}
