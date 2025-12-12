package io.github.up2jakarta.test.cl;

import io.github.up2jakarta.cii.ppf.*;
import org.junit.jupiter.api.Test;

import static io.github.up2jakarta.test.api.TestUtil.assertUniqueness;

public class PPFCheckingUniquenessTests {

    /**
     * @see InvoiceCodeType
     */
    @Test
    public void tred1001() {
        assertUniqueness(InvoiceCodeType.class, InvoiceCodeType::getCode);
    }

    /**
     * @see MeasurementUnitCode
     */
    @Test
    public void rec20() {
        assertUniqueness(MeasurementUnitCode.class, MeasurementUnitCode::getCode);
    }

    /**
     * @see SpecialServiceDescriptionCodeType
     */
    @Test
    public void tred7161() {
        assertUniqueness(SpecialServiceDescriptionCodeType.class, SpecialServiceDescriptionCodeType::getCode);
    }

    /**
     * @see EASchemeIDType
     */
    @Test
    public void tred3155() {
        assertUniqueness(EASchemeIDType.class, EASchemeIDType::getCode);
    }

    /**
     * @see ItemTypeIDCodeType
     */
    @Test
    public void tred7143() {
        assertUniqueness(ItemTypeIDCodeType.class, ItemTypeIDCodeType::getCode);
    }

    /**
     * @see TaxExemptionReasonCodeType
     */
    @Test
    public void e307() {
        assertUniqueness(TaxExemptionReasonCodeType.class, TaxExemptionReasonCodeType::getCode);
    }

    /**
     * @see SchemeCodeType
     */
    @Test
    public void iso6523() {
        assertUniqueness(SchemeCodeType.class, SchemeCodeType::getCode);
    }

    /**
     * @see SubjectCodeType
     */
    @Test
    public void tred4451() {
        assertUniqueness(SubjectCodeType.class, SubjectCodeType::getCode);
    }

    /**
     * @see LanguageCodeType
     */
    @Test
    public void iso639() {
        assertUniqueness(LanguageCodeType.class, LanguageCodeType::getCode);
    }

    /**
     * @see MimeCodeType
     */
    @Test
    public void mime() {
        assertUniqueness(MimeCodeType.class, MimeCodeType::getCode, false);
    }

    /**
     * @see ProfileType
     */
    @Test
    public void profile() {
        assertUniqueness(ProfileType.class, ProfileType::getCode, false);
    }

    /**
     * @see ScopeType
     */
    @Test
    public void scope() {
        assertUniqueness(ScopeType.class, ScopeType::getCode);
    }

}
