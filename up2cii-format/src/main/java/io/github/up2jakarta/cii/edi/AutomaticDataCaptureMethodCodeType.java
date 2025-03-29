package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.AutomaticDataCaptureMethodCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 7233 (AutomaticDataCaptureMethodCode) : Packaging related description code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred7233.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("7233")
@Documented(value = "AutomaticDataCaptureMethodCode", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.3", date = "2008-08-23")
@XmlJavaTypeAdapter(AutomaticDataCaptureMethodCodeAdapter.class)
public enum AutomaticDataCaptureMethodCodeType implements CodeList<AutomaticDataCaptureMethodCodeType> {

    /**
     * The package is barcoded with EAN-13 or EAN-8 code.
     */
    V_50("50", "Package barcoded EAN-13 or EAN-8"),

    /**
     * The package is barcoded with ITF-14 or ITF-6 code.
     */
    V_51("51", "Package barcoded ITF-14 or ITF-6"),

    /**
     * The package is barcoded with UCC or EAN-128 code.
     */
    V_52("52", "Package barcoded UCC or EAN-128"),

    /**
     * The package will never be tagged with an EPC (Electronic Product Code) transponder.
     */
    V_64("64", "Package never EPC tagged"),

    /**
     * The package will sometimes be tagged with an EPC (Electronic Product Code) transponder.
     */
    V_65("65", "Package sometimes EPC tagged"),

    /**
     * The information provides instructions as to how a package is to be tagged or have a
     * bar code applied to it.
     */
    V_67("67", "Tagging/bar code instructions"),

    /**
     * The package is bar-coded and tagged with an EPC (Electronic Product Code) transponder.
     */
    V_78("78", "Package bar-coded and EPC tagged"),

    /**
     * The package is tagged with an EPC (Electronic Product Code) transponder only.
     */
    V_79("79", "Package EPC tagged only"),

    /**
     * The package is marked with a variable measure barcode.
     */
    V_81("81", "Package marked with a variable measure barcode"),

    /**
     * The package is marked with a fixed measure barcode.
     */
    V_82("82", "Package marked with fixed measure barcode."),
    ;

    private final String name;
    private final String code;

    AutomaticDataCaptureMethodCodeType(String code, String name) {
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
