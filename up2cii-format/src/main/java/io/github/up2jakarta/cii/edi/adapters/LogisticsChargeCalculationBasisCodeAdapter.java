package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.LogisticsChargeCalculationBasisCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link LogisticsChargeCalculationBasisCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class LogisticsChargeCalculationBasisCodeAdapter extends CodeListConverter<LogisticsChargeCalculationBasisCodeType> {

    LogisticsChargeCalculationBasisCodeAdapter() {
        super(LogisticsChargeCalculationBasisCodeType.class, "ECE-6131");
    }

}