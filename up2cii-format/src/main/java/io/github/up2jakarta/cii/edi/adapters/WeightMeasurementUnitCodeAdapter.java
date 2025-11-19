package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.WeightMeasurementUnitCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link WeightMeasurementUnitCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class WeightMeasurementUnitCodeAdapter extends CodeListConverter<WeightMeasurementUnitCodeType> {

    WeightMeasurementUnitCodeAdapter() {
        super(WeightMeasurementUnitCodeType.class, "ECE-R20");
    }

}
