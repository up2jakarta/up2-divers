package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.ContactTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link ContactTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class ContactTypeCodeAdapter extends CodeListAdapter<ContactTypeCodeType> {

    @Inject
    public ContactTypeCodeAdapter() {
        super(ContactTypeCodeType.class, "ECE-3139");
    }

}
