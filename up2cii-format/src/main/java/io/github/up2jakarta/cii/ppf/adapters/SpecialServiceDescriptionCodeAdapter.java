package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
import io.github.up2jakarta.xml.clv.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;

import static io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType.values;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static java.util.Arrays.asList;

/**
 * {@link XmlAdapter} mapping of {@link SpecialServiceDescriptionCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class SpecialServiceDescriptionCodeAdapter extends CodeListConverter<SpecialServiceDescriptionCodeType> {

    public static final String ECE_7161 = "ECE-7161";
    public static final List<SpecialServiceDescriptionCodeType> LOV_7161 = asList(values());

    SpecialServiceDescriptionCodeAdapter() {
        super(SpecialServiceDescriptionCodeType.class, ERROR, ECE_7161, LOV_7161);
    }

}
