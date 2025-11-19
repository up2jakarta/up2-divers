package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.core.TokenType;
import io.github.up2jakarta.cii.edi.adapters.AllowanceChargeIdentificationCodeAdapter;
import io.github.up2jakarta.cii.edi.adapters.AllowanceChargeReasonCodeAdapter;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.TypeConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import static java.util.Optional.ofNullable;

/**
 * {@link XmlAdapter} mapping of the following classes that implement {@link ChargeReasonCodeType} :
 * <p>
 * {@link io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType}
 * {@link io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType}
 * {@link io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType}
 */
@Named
@Singleton
public final class ChargeReasonCodeAdapter extends TypeConverter<ChargeReasonCodeType<?>> {

    ChargeReasonCodeAdapter() {
        super(null, null, null);
    }

    public static ChargeReasonCodeType<?> from(ChargeReasonCodeType<?> value, io.github.up2jakarta.cii.format.minified.udt.IndicatorType indicator) {
        var i = ofNullable(indicator).map(io.github.up2jakarta.cii.format.minified.udt.IndicatorType::isIndicator).orElse(null);
        return from(value, i);
    }

    public static ChargeReasonCodeType<?> from(ChargeReasonCodeType<?> value, io.github.up2jakarta.cii.format.standard.udt.IndicatorType indicator) {
        var i = ofNullable(indicator).map(io.github.up2jakarta.cii.format.standard.udt.IndicatorType::isIndicator).orElse(null);
        return from(value, i);
    }

    public static ChargeReasonCodeType<?> from(ChargeReasonCodeType<?> value, Boolean indicator) {
        if (indicator == null) {
            return AllowanceChargeReasonCodeAdapter.from(value);
        } else if (indicator) {
            return SpecialServiceDescriptionCodeAdapter.from(value); // BG-21, BG-28
        }
        return AllowanceChargeIdentificationCodeAdapter.from(value); // BG-20, BG-27
    }

    @Override
    protected ChargeReasonCodeType<?> doParse(String value) throws CodeListException {
        return TokenType.from(value);
    }

    @Override
    protected String doFormat(ChargeReasonCodeType<?> value) {
        return value.getCode();
    }

}
