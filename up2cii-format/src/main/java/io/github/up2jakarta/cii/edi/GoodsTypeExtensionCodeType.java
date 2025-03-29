package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.GoodsTypeExtensionCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 7361 : Customs goods identifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred7361.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Goods Type Extension Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.1", date = "2008-08-23")
@XmlJavaTypeAdapter(GoodsTypeExtensionCodeAdapter.class)
public enum GoodsTypeExtensionCodeType implements CodeList<GoodsTypeExtensionCodeType> {

    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    GoodsTypeExtensionCodeType(String code, String name) {
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
