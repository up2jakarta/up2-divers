package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AdjustmentReasonCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AdjustmentReasonCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AdjustmentReasonCodeAdapter extends CodeListConverter<AdjustmentReasonCodeType> {

    AdjustmentReasonCodeAdapter() {
        super(AdjustmentReasonCodeType.class, "ECE-4465");
    }

}
