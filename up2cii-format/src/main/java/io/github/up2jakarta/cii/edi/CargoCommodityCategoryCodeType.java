package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.CargoCommodityCategoryCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 7357 : Commodity identification code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred7357.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Commodity Identification Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(CargoCommodityCategoryCodeAdapter.class)
public enum CargoCommodityCategoryCodeType implements CodeList<CargoCommodityCategoryCodeType> {

    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    CargoCommodityCategoryCodeType(String code, String name) {
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
