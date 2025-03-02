package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.CargoCategoryCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT R21 : Cargo Type Code.
 * {@see https://unece.org/trade/uncefact/cl-recommendations}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Cargo Type Code", agency = Agency.UN_ECE, version = "1996 Rev 2 Final")
@Schema(agency = "UN/CEFACT", version = "1.0", date = "2008-08-23")
@XmlJavaTypeAdapter(CargoCategoryCodeAdapter.class)
public enum CargoCategoryCodeType implements CodeList<CargoCategoryCodeType> {

    V_0("0", "Liquid Bulk"),
    V_1("1", "Solid Bulk"),
    V_2("2", "Large Freight Container (20 feet or more in length)"),
    V_3("3", "Other Freight Container (Less than 20 feet in length)"),
    V_4("4", "Palletized"),
    V_5("5", "Pre-slung"),
    V_6("6", "Mobile self-propelled"),
    V_7("7", "Other mobile units"),
    V_9("9", "Other types of cargo"),
    ;

    private final String name;
    private final String code;

    CargoCategoryCodeType(String code, String name) {
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
