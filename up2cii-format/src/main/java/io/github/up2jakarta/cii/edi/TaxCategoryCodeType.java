package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.TaxCategoryCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 5305 : Duty or tax or fee category code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred5305.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Duty or Tax or Fee Category Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.7", date = "2008-08-23")
@XmlJavaTypeAdapter(TaxCategoryCodeAdapter.class)
public enum TaxCategoryCodeType implements CodeList<TaxCategoryCodeType> {

    /**
     * Code specifying that the rate is based on mixed tax.
     */
    @Deprecated(forRemoval = true)
    A("A", "Mixed tax rate"),

    /**
     * Tax rate is lower than standard rate.
     */
    @Deprecated(forRemoval = true)
    AA("AA", "Lower rate"),

    /**
     * A tax category code indicating the item is tax exempt when the item is bought for future
     * resale.
     */
    @Deprecated(forRemoval = true)
    AB("AB", "Exempt for resale"),

    /**
     * A code to indicate that the Value Added Tax (VAT) amount which is due on the current
     * invoice is to be paid on receipt of a separate VAT payment request.
     */
    @Deprecated(forRemoval = true)
    AC("AC", "Value Added Tax (VAT) not now due for payment"),

    /**
     * A code to indicate that the Value Added Tax (VAT) amount of a previous invoice is to
     * be paid.
     */
    @Deprecated(forRemoval = true)
    AD("AD", "Value Added Tax (VAT) due from a previous invoice"),

    /**
     * Code specifying that the standard VAT rate is levied from the invoicee.
     */
    AE("AE", "VAT Reverse Charge"),

    /**
     * VAT not to be paid to the issuer of the invoice but directly to relevant tax authority.
     */
    @Deprecated(forRemoval = true)
    B("B", "Transferred (VAT)"),

    /**
     * Duty associated with shipment of goods is paid by the supplier; customer receives goods
     * with duty paid.
     */
    @Deprecated(forRemoval = true)
    C("C", "Duty paid by supplier"),

    /**
     * Indication that the VAT margin scheme for travel agents is applied.
     */
    @Deprecated(forRemoval = true)
    D("D", "Value Added Tax (VAT) margin scheme - travel agents"),

    /**
     * Code specifying that taxes are not applicable.
     */
    E("E", "Exempt from tax"),

    /**
     * Indication that the VAT margin scheme for second-hand goods is applied.
     */
    @Deprecated(forRemoval = true)
    F("F", "Value Added Tax (VAT) margin scheme - second-hand goods"),

    /**
     * Code specifying that the item is free export and taxes are not charged.
     */
    G("G", "Free export item, tax not charged"),

    /**
     * Code specifying a higher rate of duty or tax or fee.
     */
    @Deprecated(forRemoval = true)
    H("H", "Higher rate"),

    /**
     * Indication that the VAT margin scheme for works of art is applied.
     */
    @Deprecated(forRemoval = true)
    I("I", "Value Added Tax (VAT) margin scheme - works of art Margin scheme - Works of art"),

    /**
     * Indication that the VAT margin scheme for collector’s items and antiques is applied.
     */
    @Deprecated(forRemoval = true)
    J("J", "Value Added Tax (VAT) margin scheme - collector’s items and antiques"),

    /**
     * A tax category code indicating the item is VAT exempt due to an intra-community supply
     * in the European Economic Area.
     */
    K("K", "VAT exempt for EEA intra-community supply of goods and services"),

    /**
     * Impuesto General Indirecto Canario (IGIC) is an indirect tax levied on goods and services
     * supplied in the Canary Islands (Spain) by traders and professionals, as well as on import
     * of goods.
     */
    L("L", "Canary Islands general indirect tax"),

    /**
     * Impuesto sobre la Producción, los Servicios y la Importación (IPSI) is an indirect municipal
     * tax, levied on the production, processing and import of all kinds of movable tangible
     * property, the supply of services and the transfer of immovable property located in the
     * cities of Ceuta and Melilla.
     */
    M("M", "Tax for production, services and importation in Ceuta and Melilla"),

    /**
     * standard VAT calculated for an additional taxable base when the additional taxable base
     * is not included in the document totals
     */
    @Deprecated(forRemoval = true)
    N("N", "Standard rate additional VAT"),

    /**
     * Code specifying that taxes are not applicable to the services.
     */
    O("O", "Services outside scope of tax"),

    /**
     * Code specifying the standard rate.
     */
    S("S", "Standard rate"),

    /**
     * Code specifying that the goods are at a zero rate.
     */
    Z("Z", "Zero rated goods"),
    ;

    private final String name;
    private final String code;

    TaxCategoryCodeType(String code, String name) {
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
