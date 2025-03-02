package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.TimePointFormatCodeType;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;


/**
 * {@link XmlAdapter} mapping of {@link TimePointFormatCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class TimePointFormatCodeAdapter extends CodeListConverter<TimePointFormatCodeType> {

    TimePointFormatCodeAdapter() {
        super(TimePointFormatCodeType.class, "ECE-2379");
    }

}
