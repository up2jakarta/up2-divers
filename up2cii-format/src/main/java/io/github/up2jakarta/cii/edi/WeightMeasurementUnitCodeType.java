package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.WeightMeasurementUnitCodeAdapter;
import io.github.up2jakarta.cii.ppf.MeasurementUnitCode;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT R20 (Weight) : Measurement Unit Common Code Weight.
 * {@see https://unece.org/trade/uncefact/cl-recommendations}
 */
@Generated(value = "CII", comments = "by Abderrazek ABBESSI")
@SubList(value = "R20", type = MeasurementUnitCode.class)
@Documented(value = "Measurement Unit Common Code Weight", agency = Agency.UN_ECE, version = "4")
@Schema(agency = "UN/CEFACT", version = "1.1", date = "2008-08-23")
@XmlJavaTypeAdapter(WeightMeasurementUnitCodeAdapter.class)
public enum WeightMeasurementUnitCodeType implements CodeList<WeightMeasurementUnitCodeType> {

    /**
     * A unit of mass equal to one thousand grams.
     */
    KGM(MeasurementUnitCode.KGM),

    /**
     * Synonym: metric ton
     */
    TNE(MeasurementUnitCode.TNE),
    ;

    private final String name;
    private final String code;

    WeightMeasurementUnitCodeType(MeasurementUnitCode code) {
        this.code = code.getCode();
        this.name = code.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }
}
