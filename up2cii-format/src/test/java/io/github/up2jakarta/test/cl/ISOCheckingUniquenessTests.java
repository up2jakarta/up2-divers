package io.github.up2jakarta.test.cl;

import io.github.up2jakarta.cii.edi.CountryIDType;
import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import org.junit.jupiter.api.Test;

import static io.github.up2jakarta.test.api.TestUtil.assertUniqueness;

public class ISOCheckingUniquenessTests {

    /**
     * @see CurrencyCodeType
     */
    @Test
    public void iso4217() {
        assertUniqueness(CurrencyCodeType.class, CurrencyCodeType::getCode);
    }

    /**
     * @see CountryIDType
     */
    @Test
    public void iso3166() {
        assertUniqueness(CountryIDType.class, CountryIDType::getCode);
        assertUniqueness(CountryIDType.class, CountryIDType::getName, false);
    }

}
