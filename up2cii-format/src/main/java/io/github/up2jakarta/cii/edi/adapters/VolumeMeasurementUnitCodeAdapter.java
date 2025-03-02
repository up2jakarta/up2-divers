package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.VolumeMeasurementUnitCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link VolumeMeasurementUnitCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class VolumeMeasurementUnitCodeAdapter extends CodeListConverter<VolumeMeasurementUnitCodeType> {

    VolumeMeasurementUnitCodeAdapter() {
        super(VolumeMeasurementUnitCodeType.class, "ECE-R20");
    }

}
