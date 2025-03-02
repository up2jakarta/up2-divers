package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.LogisticsChargeCalculationBasisCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 6131 : Freight Charge Quantity Unit Basis Code.
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Freight Charge Quantity Unit Basis Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(LogisticsChargeCalculationBasisCodeAdapter.class)
public enum LogisticsChargeCalculationBasisCodeType implements CodeList<LogisticsChargeCalculationBasisCodeType> {

    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    LogisticsChargeCalculationBasisCodeType(String code, String name) {
        this.code = code;
        this.name = name;
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
