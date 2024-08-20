package io.github.up2jakarta.csv.test.codelist;

import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.springframework.stereotype.Component;

/**
 * {@link XmlAdapter} mapping of {@link MeasurementUnitCode} to CII (D16B) XML-String.
 */
@Component
public class MeasurementUnitConverter extends CodeListConverter<MeasurementUnitCode> {

    public static final String EDI_R_20 = "EDI-R20";

    MeasurementUnitConverter() {
        super(MeasurementUnitCode.class, EDI_R_20);
    }

}
