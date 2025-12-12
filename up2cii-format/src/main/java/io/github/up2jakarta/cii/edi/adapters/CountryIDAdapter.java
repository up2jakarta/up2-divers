package io.github.up2jakarta.cii.edi.adapters;

import io.github.up2jakarta.cii.edi.CountryIDType;
import io.github.up2jakarta.lov.CodeListAdapter;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.SafeAdapter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static io.github.up2jakarta.lov.SeverityType.ERROR;

/**
 * {@link XmlAdapter} mapping of {@link CountryIDType} to CII (D16B) XML-String.
 */
@Named
@Singleton
public final class CountryIDAdapter extends SafeAdapter<CountryIDType> {

    private static final String CODE_GREECE = "EL";
    private final CodeListAdapter<CountryIDType> delegate;

    @Inject
    public CountryIDAdapter() {
        super(CountryIDType.class, ERROR, "ISO-3166");
        this.delegate = new CodeListAdapter<>(type, level, code);
    }

    @Override
    protected CountryIDType doParse(String value) throws CodeListException {
        if (CODE_GREECE.equals(value)) {
            return CountryIDType.GR;
        }
        return delegate.parse(value);
    }

    @Override
    protected String doFormat(CountryIDType value) {
        return value.getCode();
    }

}
