package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.LogisticsChargeCalculationBasisCodeType;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link LogisticsChargeCalculationBasisCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class LogisticsChargeCalculationBasisCodeAdapter extends CodeListAdapter<LogisticsChargeCalculationBasisCodeType> {

    LogisticsChargeCalculationBasisCodeAdapter() {
        super(LogisticsChargeCalculationBasisCodeType.class, "ECE-6131");
    }

}