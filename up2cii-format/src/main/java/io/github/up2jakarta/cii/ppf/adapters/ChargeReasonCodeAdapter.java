package io.github.up2jakarta.cii.ppf.adapters;

import io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType;
import io.github.up2jakarta.cii.edi.AllowanceChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.ChargeReasonCodeType;
import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
import io.github.up2jakarta.cii.xml.TokenType;
import io.github.up2jakarta.csv.exception.CodeListException;
import io.github.up2jakarta.csv.extension.TypeConverter;
import io.github.up2jakarta.csv.misc.CodeListConverter;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.stream.Stream;

import static io.github.up2jakarta.cii.edi.adapters.AllowanceChargeIdentificationCodeAdapter.ECE_5189;
import static io.github.up2jakarta.cii.edi.adapters.AllowanceChargeReasonCodeAdapter.ECE_4465;
import static io.github.up2jakarta.cii.ppf.adapters.SpecialServiceDescriptionCodeAdapter.ECE_7161;
import static io.github.up2jakarta.csv.extension.SeverityType.ERROR;
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
public class ChargeReasonCodeAdapter extends TypeConverter<ChargeReasonCodeType<?>> {

    ChargeReasonCodeAdapter() {
        super(null, ERROR, null);
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
            final Class<AllowanceChargeReasonCodeType> target = AllowanceChargeReasonCodeType.class;
            final AllowanceChargeReasonCodeType[] values = AllowanceChargeReasonCodeType.values();
            return CodeListConverter.parse(value.getCode(), target, Stream.of(values), ERROR, ECE_4465);
        }
        if (indicator) {
            // BG-21, BG-28
            final Class<SpecialServiceDescriptionCodeType> target = SpecialServiceDescriptionCodeType.class;
            final SpecialServiceDescriptionCodeType[] values = SpecialServiceDescriptionCodeType.values();
            return CodeListConverter.parse(value.getCode(), target, Stream.of(values), ERROR, ECE_7161);
        } else {
            // BG-20, BG-27
            final Class<AllowanceChargeIdentificationCodeType> target = AllowanceChargeIdentificationCodeType.class;
            final AllowanceChargeIdentificationCodeType[] values = AllowanceChargeIdentificationCodeType.values();
            return CodeListConverter.parse(value.getCode(), target, Stream.of(values), ERROR, ECE_5189);
        }
    }

    @Override
    public ChargeReasonCodeType<?> parse(String value) throws CodeListException {
        return TokenType.from(value);
    }

    @Override
    public String format(@NotNull ChargeReasonCodeType value) {
        return value.getCode();
    }

}
