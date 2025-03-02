package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.FreightChargeTariffClassCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 5243 : Rate or tariff class description code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred5243.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Freight Charge Tariff Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(FreightChargeTariffClassCodeAdapter.class)
public enum FreightChargeTariffClassCodeType implements CodeList<FreightChargeTariffClassCodeType> {

    /**
     * Rate class applies to senior persons.
     */
    A("A", "Senior person rate"),

    /**
     * Code specifying that the rate or tariff is a basic one.
     */
    B("B", "Basic"),

    /**
     * Code specifying the specific commodity rate.
     */
    C("C", "Specific commodity rate"),

    /**
     * Rate class applies to teenagers.
     */
    D("D", "Teenager rate"),

    /**
     * Rate class applies to children.
     */
    E("E", "Child rate"),

    /**
     * Rate class applies to adults.
     */
    F("F", "Adult rate"),

    /**
     * Rate a foreign government supplies in the export process
     */
    @Deprecated(since = "D23A", forRemoval = false)
    G("G", "Export Subsidy Rate"),

    /**
     * Rate a foreign government gives its manufacturers so that they can manufacture their
     * goods for less
     */
    @Deprecated(since = "D23A", forRemoval = false)
    H("H", "Subsidy Rate"),

    /**
     * Code specifying the rate per kilogram.
     */
    K("K", "Rate per kilogram"),

    /**
     * Code specifying the minimum charge rate.
     */
    M("M", "Minimum charge rate"),

    /**
     * Code specifying the normal rate.
     */
    N("N", "Normal rate"),

    /**
     * Code specifying the quantity rate.
     */
    Q("Q", "Quantity rate"),

    /**
     * Code specifying the reduction on normal rate.
     */
    R("R", "Class rate (Reduction on normal rate)"),

    /**
     * Code specifying the surcharge on normal rate.
     */
    S("S", "Class rate (Surcharge on normal rate)"),
    ;

    private final String name;
    private final String code;

    FreightChargeTariffClassCodeType(String code, String name) {
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
