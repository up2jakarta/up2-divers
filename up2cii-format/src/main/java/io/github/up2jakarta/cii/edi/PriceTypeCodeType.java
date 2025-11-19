package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.PriceTypeCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 5375 : Price type code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred5375.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Price Type Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.2", date = "2008-08-23")
@XmlJavaTypeAdapter(PriceTypeCodeAdapter.class)
public enum PriceTypeCodeType implements CodeList<PriceTypeCodeType> {

    /**
     * Price authorized to be charged in the event of an order being cancelled.
     */
    AA("AA", "Cancellation price"),

    /**
     * To indicate that the price applies per ton.
     */
    AB("AB", "Per ton"),

    /**
     * A code to identify the price when the minimum number is purchased.
     */
    AC("AC", "Minimum order price"),

    /**
     * A code to identify a price for the export market.
     */
    AD("AD", "Export price"),

    /**
     * A code identifying the price for a specific range of purchase quantities.
     */
    AE("AE", "Range dependent price"),

    /**
     * Price given by competitor.
     */
    AF("AF", "Competitor price"),

    /**
     * A daily fixed sum of money for a product or service which is or may be bought or sold.
     */
    AG("AG", "Daily Price"),

    /**
     * A sum of money for a service which is or may be bought or sold.
     */
    AH("AH", "Service Price"),

    /**
     * The price is referring to the active ingredient.
     */
    AI("AI", "Active ingredient"),

    /**
     * A sum of money for a product or service is or may be bought or sold.that is flexible
     * and determined real-time in response to market demands.
     */
    AJ("AJ", "Dynamic Price"),

    /**
     * A sum of money for which a product or service is or may be bought or sold without any
     * discount or surcharge being applied.
     */
    AK("AK", "Basic Price"),

    /**
     * A sum of money which is an addition to the price for which a product or service is or
     * may be bought or sold, such as for a special meal.
     */
    AL("AL", "Extra Price"),

    /**
     * A sum of money which is discount against a price for which a product or service is or
     * may be bought or sold..
     */
    AM("AM", "Discount Price"),

    /**
     * A sum of money for cancelling a product or service which is or may be bought or sold.
     */
    AN("AN", "Cancellation Price"),

    /**
     * A sum of money that is paid back to somebody, especially because somebody paid too much
     * or because somebody returned a product or service.
     */
    AO("AO", "Refund Price"),

    /**
     * A sum of money that is paid to somebody for selling a product or service and that increases
     * with the amount of a product or service that is or may be bought or sold..
     */
    AP("AP", "Commission Price"),

    /**
     * The price is referring to the measured quantity.
     */
    AQ("AQ", "As is quantity"),

    /**
     * A sum of money that is paid or has to be paid because of breaking a law, rule, or contract.
     */
    AR("AR", "Penalty Price"),

    /**
     * Code specifying the catalogue price.
     */
    CA("CA", "Catalogue"),

    /**
     * Code specifying the contract price.
     */
    CT("CT", "Contract"),

    /**
     * The price is referring to the consumer unit.
     */
    CU("CU", "Consumer unit"),

    /**
     * Code specifying the distributor price.
     */
    DI("DI", "Distributor"),

    /**
     * Price registered at European Commission Steel and Carbon office (DG III).
     */
    EC("EC", "ECSC price"),

    /**
     * Code specifying the net weight price.
     */
    NW("NW", "Net weight"),

    /**
     * Code specifying the catalogue price.
     */
    PC("PC", "Price catalogue"),

    /**
     * Code specifying the price per item.
     */
    PE("PE", "Per each"),

    /**
     * Code specifying the price per kilogram.
     */
    PK("PK", "Per kilogram"),

    /**
     * Code specifying the price per litre.
     */
    PL("PL", "Per litre"),

    /**
     * Code specifying the price per tonne.
     */
    PT("PT", "Per tonne"),

    /**
     * Code specifying the price per specified unit.
     */
    PU("PU", "Specified unit"),

    /**
     * Code specifying a provisional price.
     */
    PV("PV", "Provisional price"),

    /**
     * Code specifying the gross weight price.
     */
    PW("PW", "Gross weight"),

    /**
     * Code specifying the quoted price.
     */
    QT("QT", "Quoted"),

    /**
     * Code specifying the suggested retail price.
     */
    SR("SR", "Suggested retail"),

    /**
     * Code specifying that the price has to be negotiated.
     */
    TB("TB", "To be negotiated"),

    /**
     * The price is referring to the traded unit.
     */
    TU("TU", "Traded unit"),

    /**
     * Weight calculated on ordered dimension (length, width, thickness) not on final dimension
     * (e.g. steel products).
     */
    TW("TW", "Theoretical weight"),

    /**
     * Code specifying the wholesale price.
     */
    WH("WH", "Wholesale"),

    /**
     * The price is calculated based on gross volume.
     */
    WI("WI", "Gross volume"),
    ;

    private final String name;
    private final String code;

    PriceTypeCodeType(String code, String name) {
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
