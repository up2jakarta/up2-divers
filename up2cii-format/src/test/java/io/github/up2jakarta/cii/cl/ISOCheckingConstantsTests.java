package io.github.up2jakarta.cii.cl;

import io.github.up2jakarta.cii.TUConfiguration;
import io.github.up2jakarta.cii.api.TestUtil;
import io.github.up2jakarta.cii.edi.CountryIDType;
import io.github.up2jakarta.cii.edi.CurrencyCodeType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ISOCheckingConstantsTests {

    @Autowired
    private TestUtil util;

    /**
     * @see CurrencyCodeType
     */
    @Test
    public void iso4217() throws Exception {
        util.assertSameBinding(CurrencyCodeType.class);
    }

    /**
     * @see CountryIDType
     */
    @Test
    public void iso3166() throws Exception {
        util.assertSameBinding(CountryIDType.class);
    }

}
