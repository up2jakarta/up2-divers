package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.GoodsTypeCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList like of UN/TDED 7357 : Goods type code.
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList(value = "7357", type = CargoCommodityCategoryCodeType.class)
@Documented(value = "Goods Type Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(GoodsTypeCodeAdapter.class)
public enum GoodsTypeCodeType implements CodeList<GoodsTypeCodeType> {

    ZZZ(CargoCommodityCategoryCodeType.ZZZ),
    ;

    private final String name;
    private final String code;

    GoodsTypeCodeType(CargoCommodityCategoryCodeType cl) {
        this.code = cl.getCode();
        this.name = cl.getName();
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
