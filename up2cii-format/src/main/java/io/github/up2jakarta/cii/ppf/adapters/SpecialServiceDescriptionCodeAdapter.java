package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
import io.github.up2jakarta.lov.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.List;

import static io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType.values;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static java.util.Arrays.asList;

/**
 * {@link XmlAdapter} mapping of {@link SpecialServiceDescriptionCodeType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public class SpecialServiceDescriptionCodeAdapter extends CodeListConverter<SpecialServiceDescriptionCodeType> {

    private static final String CODE = "ECE-7161";
    private static final List<SpecialServiceDescriptionCodeType> VALUES = asList(values());

    SpecialServiceDescriptionCodeAdapter() {
        super(SpecialServiceDescriptionCodeType.class, ERROR, CODE, VALUES);
    }

    public static ChargeReasonCodeType<?> from(ChargeReasonCodeType<?> value) {
        return find(value.getCode(), SpecialServiceDescriptionCodeType.class, VALUES, ERROR, CODE);
    }

}
