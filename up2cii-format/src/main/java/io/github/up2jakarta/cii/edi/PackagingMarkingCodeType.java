package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.api.SubList;
import io.github.up2jakarta.cii.edi.adapters.PackagingMarkingCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 7233 (PackagingMarkingCode) : Packaging related description code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred7233.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("7233")
@Documented(value = "PackagingMarkingCode", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(PackagingMarkingCodeAdapter.class)
public enum PackagingMarkingCodeType implements CodeList<PackagingMarkingCodeType> {

    /**
     * The ingredients of the product are not marked on the packaging of the product.
     */
    V_1("1", "Product ingredients not marked on package"),

    /**
     * The product price is not marked on the packaging of the product.
     */
    V_2("2", "Product price not marked on packaging"),

    /**
     * The product best before date is not marked on the packaging of the product.
     */
    V_3("3", "Product best before date not marked on product package"),

    /**
     * Package is not marked as recyclable.
     */
    V_4("4", "Package not marked recyclable"),

    /**
     * A code indicating that promotional details have been marked on the package.
     */
    V_5("5", "Promotional details marked"),

    /**
     * The item is labeled according to the general EAN.UCC specifications for clothing and
     * fashion accessories.
     */
    V_6("6", "Labeled according to general EAN.UCC specifications for clothing and fashion accessories"),

    /**
     * The package is marked with the last date on which the item may be sold.
     */
    V_7("7", "Sell-by date marked on package"),

    /**
     * The package is marked with the last date on which the item may be used.
     */
    V_8("8", "Use-by date marked on package"),

    /**
     * The package is marked with the date of the packaging or manufacturing of the item.
     */
    V_9("9", "Packaging / manufacturing date marked on package"),

    /**
     * Package is not marked with any kind of date indicating freshness, such as use-by, best
     * before or packaging date.
     */
    V_10("10", "No freshness date marked on package"),

    /**
     * Package is marked with the product best before date.
     */
    V_31("31", "Package best before date marked"),

    /**
     * The package is marked recyclable.
     */
    V_32("32", "Package marked recyclable"),

    /**
     * The package is marked returnable.
     */
    V_33("33", "Package marked returnable"),

    /**
     * The information is related to product marking.
     */
    V_34("34", "Product marking"),

    /**
     * The information is related to the type of package.
     */
    V_35("35", "Type of package"),

    /**
     * The information specifies the package.
     */
    V_36("36", "Package specifications"),

    /**
     * The information is related to protection of the package.
     */
    V_37("37", "Package protection"),

    /**
     * The information describes how coverage with tarpaulins is to be provided.
     */
    V_38("38", "Tarping"),

    /**
     * The information describes the platform or skid location.
     */
    V_39("39", "Platform/skid location"),

    /**
     * The information gives the location of the load bearing piece.
     */
    V_40("40", "Bearing piece location"),

    /**
     * The information describes the type of skid or pallet.
     */
    V_41("41", "Skid/pallet type"),

    /**
     * The information describes the placement on the carrier.
     */
    V_42("42", "Placement on carrier"),

    /**
     * Descriptions to be provided.
     */
    V_43("43", "Spacing directions"),

    /**
     * The information specifies the unloading device which must be used to handle the package.
     */
    V_44("44", "Unloading device"),

    /**
     * The information specifies the unloading equipment which must be used to handle the
     * package.
     */
    V_45("45", "Unloading equipment"),

    /**
     * The method used in packing the commodity, e.g. hermetically sealed, repacked in original
     * container.
     */
    V_46("46", "Packing method"),

    /**
     * Substances presenting high danger.
     */
    V_47("47", "Packing group I"),

    /**
     * Substances presenting medium danger.
     */
    V_48("48", "Packing group II"),

    /**
     * Substances presenting low danger.
     */
    V_49("49", "Packing group III"),

    /**
     * The package is marked with the price.
     */
    V_53("53", "Package price marked"),

    /**
     * The ingredients of the product contained in a package are marked on that package.
     */
    V_54("54", "Product ingredients marked on package"),

    /**
     * Specifies the characteristics of the core of the package.
     */
    V_55("55", "Core characteristics"),

    /**
     * The packaging as per shipping requirement.
     */
    V_56("56", "Shipping requirement"),

    /**
     * The packaging as per Customs requirement.
     */
    V_57("57", "Customs requirement"),

    /**
     * The packaging as per transport contract requirement.
     */
    V_58("58", "Transport contract requirement"),

    /**
     * The packaging related information is for methods of preservation.
     */
    V_59("59", "Preservation method"),

    /**
     * The information describes the pattern used to mark the product.
     */
    V_60("60", "Product marking pattern"),

    /**
     * The information provides the location of the product marking.
     */
    V_61("61", "Product marking location"),

    /**
     * The information provides the location of a package or container mark.
     */
    V_62("62", "Package/container mark location"),

    /**
     * The information details the marking method.
     */
    V_63("63", "Marking method"),

    /**
     * The information describes limitations which apply to a receiving facility.
     */
    V_66("66", "Receiving facility limitations"),

    /**
     * The labelling of a package in which goods are shipped.
     */
    V_68("68", "Shipping package labelling"),

    /**
     * The sealing particulars of a package in which goods are shipped.
     */
    V_69("69", "Shipping package sealing"),

    /**
     * To indicate an optional procedure for packaging.
     */
    V_70("70", "Optional packaging procedure"),

    /**
     * Identification of the cleaning or drying specification.
     */
    V_71("71", "Cleaning or drying specification"),

    /**
     * Identification of the cushioning thickness specification.
     */
    V_72("72", "Cushioning thickness specification"),

    /**
     * Identification of the cushioning and dunnage specification.
     */
    V_73("73", "Cushioning and dunnage specification"),

    /**
     * Identification of the level of preservation specification.
     */
    V_74("74", "Level of preservation specification"),

    /**
     * Identification of the preservation material specification.
     */
    V_75("75", "Preservation material specification"),

    /**
     * Identification of the unit container specification.
     */
    V_76("76", "Unit container specification"),

    /**
     * Identification of the material wrapping specification.
     */
    V_77("77", "Material wrapping specification"),

    /**
     * Package is marked with the date by which the product should be removed from the display
     * location.
     */
    V_80("80", "Marked with “display until date”"),
    ;

    private final String name;
    private final String code;

    PackagingMarkingCodeType(String code, String name) {
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
