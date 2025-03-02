package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.api.SubList;
import io.github.up2jakarta.cii.edi.adapters.VolumeMeasurementUnitCodeAdapter;
import io.github.up2jakarta.cii.ppf.MeasurementUnitCode;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT R20 (Volume) : Measurement Unit Common Code Volume.
 * {@see https://unece.org/trade/uncefact/cl-recommendations}
 */
@Generated(value = "CII", comments = "by Abderrazek ABBESSI")
@SubList(value = "R20", type = MeasurementUnitCode.class)
@Documented(value = "Measurement Unit Common Code Volume", agency = Agency.UN_ECE, version = "4")
@Schema(agency = "UN/CEFACT", version = "1.1", date = "2008-08-23")
@XmlJavaTypeAdapter(VolumeMeasurementUnitCodeAdapter.class)
public enum VolumeMeasurementUnitCodeType implements CodeList<VolumeMeasurementUnitCodeType> {

    CMQ(MeasurementUnitCode.CMQ),
    FTQ(MeasurementUnitCode.FTQ),
    MMQ(MeasurementUnitCode.MMQ),

    /**
     * Synonym: metre cubed
     */
    MTQ(MeasurementUnitCode.MTQ),
    ;

    private final String name;
    private final String code;

    VolumeMeasurementUnitCodeType(MeasurementUnitCode code) {
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
