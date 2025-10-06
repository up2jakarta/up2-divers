package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AccountingAmountTypeCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AccountingAmountTypeCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AccountingAmountTypeCodeAdapter extends CodeListConverter<AccountingAmountTypeCodeType> {

    AccountingAmountTypeCodeAdapter() {
        super(AccountingAmountTypeCodeType.class, "EDI-E601");
    }

}
