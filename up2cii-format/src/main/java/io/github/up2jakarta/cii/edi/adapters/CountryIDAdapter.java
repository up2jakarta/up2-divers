package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.CountryIDType;
import io.github.up2jakarta.lov.CodeListConverter;
import io.github.up2jakarta.lov.CodeListException;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * {@link XmlAdapter} mapping of {@link CountryIDType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public final class CountryIDAdapter extends CodeListConverter<CountryIDType> {

    private static final String CODE_GREECE = "EL";

    CountryIDAdapter() {
        super(CountryIDType.class, "ISO-3166");
    }

    @Override
    protected CountryIDType doParse(String value) throws CodeListException {
        if (CODE_GREECE.equals(value)) {
            return CountryIDType.GR;
        }
        return super.doParse(value);
    }

}