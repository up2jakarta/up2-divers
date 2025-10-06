package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.TaxExemptionReasonCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link TaxExemptionReasonCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TaxExemptionReasonCodeAdapter extends CodeListConverter<TaxExemptionReasonCodeType> {

    TaxExemptionReasonCodeAdapter() {
        super(TaxExemptionReasonCodeType.class, "EDI-E307");
    }

}
