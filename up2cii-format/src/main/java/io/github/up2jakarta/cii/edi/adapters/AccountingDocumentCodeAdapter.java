package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AccountingDocumentCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AccountingDocumentCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AccountingDocumentCodeAdapter extends CodeListConverter<AccountingDocumentCodeType> {

    AccountingDocumentCodeAdapter() {
        super(AccountingDocumentCodeType.class, "ECE-1001");
    }

}
