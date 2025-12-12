package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AccountingAccountTypeCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AccountingAccountTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AccountingAccountTypeCodeAdapter extends CodeListAdapter<AccountingAccountTypeCodeType> {

    @Inject
    public AccountingAccountTypeCodeAdapter() {
        super(AccountingAccountTypeCodeType.class, "EDI-E501");
    }

}
