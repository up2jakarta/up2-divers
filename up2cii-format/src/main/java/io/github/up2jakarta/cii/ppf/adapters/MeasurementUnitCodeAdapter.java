package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.MeasurementUnitCode;
import io.github.up2jakarta.lov.CodeListAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link MeasurementUnitCode} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class MeasurementUnitCodeAdapter extends CodeListAdapter<MeasurementUnitCode> {

    @Inject
    public MeasurementUnitCodeAdapter() {
        super(MeasurementUnitCode.class, "ECE-R20");
    }

}
