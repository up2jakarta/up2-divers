package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.AutomaticDataCaptureMethodCodeType;
import io.github.up2jakarta.xml.codelist.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link AutomaticDataCaptureMethodCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class AutomaticDataCaptureMethodCodeAdapter extends CodeListConverter<AutomaticDataCaptureMethodCodeType> {

    AutomaticDataCaptureMethodCodeAdapter() {
        super(AutomaticDataCaptureMethodCodeType.class, "ECE-7233");
    }

}
