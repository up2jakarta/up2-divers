package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.LinearMeasurementUnitCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link LinearMeasurementUnitCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class LinearMeasurementUnitCodeAdapter extends CodeListConverter<LinearMeasurementUnitCodeType> {

    LinearMeasurementUnitCodeAdapter() {
        super(LinearMeasurementUnitCodeType.class, "ECE-R20");
    }

}
