package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.CurrencyCodeAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on ISO 4217 : ISO 3 Alpha Currency Code.
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "ISO 3 Alpha Currency Code", agency = Agency.ISO, version = "2012-08-31")
@Schema(agency = "UN/CEFACT", version = "11.13", date = "2015-11-04")
@XmlJavaTypeAdapter(CurrencyCodeAdapter.class)
public enum CurrencyCodeType implements CodeList<CurrencyCodeType> {

    AED("AED", "Dirham"),
    AFN("AFN", "Afghani"),
    ALL("ALL", "Lek"),
    AMD("AMD", "Dram"),
    ANG("ANG", "Netherlands Antillian Guilder"),
    AOA("AOA", "Kwanza"),
    ARS("ARS", "Argentine Peso"),
    AUD("AUD", "Australian Dollar"),
    AWG("AWG", "Aruban Florin"),
    AZN("AZN", "Azerbaijan Manat"),
    BAM("BAM", "Convertible Mark"),
    BBD("BBD", "Barbados Dollar"),
    BDT("BDT", "Taka"),
    BGN("BGN", "Bulgarian Lev"),
    BHD("BHD", "Bahraini Dinar"),
    BIF("BIF", "Burundi Franc"),
    BMD("BMD", "Bermudian Dollar (customarily: Bermuda Dollar)"),
    BND("BND", "Brunei Dollar"),
    BOB("BOB", "Boliviano"),
    BOV("BOV", "Mvdol"),
    BRL("BRL", "Brazilian Real"),
    BSD("BSD", "Bahamian Dollar"),
    BTN("BTN", "Ngultrum"),
    BWP("BWP", "Pula"),
    BYN("BYN", "Belarussian Ruble"),
    BZD("BZD", "Belize Dollar"),
    CAD("CAD", "Canadian Dollar"),
    CDF("CDF", "Franc Congolais"),
    CHE("CHE", "WIR Euro"),
    CHF("CHF", "Swiss Franc"),
    CHW("CHW", "WIR Franc"),
    CLF("CLF", "Unidad de Fomento"),
    CLP("CLP", "Chilean Peso"),
    CNY("CNY", "Yuan Renminbi"),
    COP("COP", "Colombian Peso"),
    COU("COU", "Unidad de Valor Real"),
    CRC("CRC", "Costa Rican Colon"),
    CUC("CUC", "Peso Convertible"),
    CUP("CUP", "Cuban Peso"),
    CVE("CVE", "Cabo Verde Escudo"),
    CZK("CZK", "Czech Koruna"),
    DJF("DJF", "Djibouti Franc"),
    DKK("DKK", "Danish Krone"),
    DOP("DOP", "Dominican Peso"),
    DZD("DZD", "Algerian Dinar"),
    EGP("EGP", "Egyptian Pound"),
    ERN("ERN", "Nakfa"),
    ETB("ETB", "Ethopian Birr"),
    EUR("EUR", "Euro"),
    FJD("FJD", "Fiji Dollar"),
    FKP("FKP", "Falkland Islands Pound"),
    GBP("GBP", "Pound Sterling"),
    GEL("GEL", "Lari"),
    GHS("GHS", "Ghana Cedi"),
    GIP("GIP", "Gibraltar Pound"),
    GMD("GMD", "Dalasi"),
    GNF("GNF", "Guinean Franc"),
    GTQ("GTQ", "Quetzal"),
    GYD("GYD", "Guyana Dollar"),
    HKD("HKD", "Honk Kong Dollar"),
    HNL("HNL", "Lempira"),
    HRK("HRK", "Kuna"),
    HTG("HTG", "Gourde"),
    HUF("HUF", "Forint"),
    IDR("IDR", "Rupiah"),
    ILS("ILS", "New Israeli Sheqel"),
    INR("INR", "Indian Rupee"),
    IQD("IQD", "Iraqi Dinar"),
    IRR("IRR", "Iranian Rial"),
    ISK("ISK", "Iceland Krona"),
    JMD("JMD", "Jamaican Dollar"),
    JOD("JOD", "Jordanian Dinar"),
    JPY("JPY", "Yen"),
    KES("KES", "Kenyan Shilling"),
    KGS("KGS", "Som"),
    KHR("KHR", "Riel"),
    KMF("KMF", "Comorian Franc"),
    KPW("KPW", "North Korean Won"),
    KRW("KRW", "Won"),
    KWD("KWD", "Kuwaiti Dinar"),
    KYD("KYD", "Cayman Islands Dollar"),
    KZT("KZT", "Tenge"),
    LAK("LAK", "Lao Kip"),
    LBP("LBP", "Lebanese Pound"),
    LKR("LKR", "Sri Lanka Rupee"),
    LRD("LRD", "Liberian Dollar"),
    LSL("LSL", "Loti"),
    LYD("LYD", "Libyan Dinar"),
    MAD("MAD", "Morrocan Dirham"),
    MDL("MDL", "Moldovan Leu"),
    MGA("MGA", "Ariary"),
    MKD("MKD", "Denar"),
    MMK("MMK", "Kyat"),
    MNT("MNT", "Tugrik"),
    MOP("MOP", "Pataca"),
    MRU("MRU", "Ouguiya"),
    MUR("MUR", "Mauritius Rupee"),
    MVR("MVR", "Rufiyaa"),
    MWK("MWK", "Malawi Kwacha"),
    MXN("MXN", "Mexican Peso"),
    MXV("MXV", "Mexican Unidad de Inversion (UDI)"),
    MYR("MYR", "Malaysian Ringgit"),
    MZN("MZN", "Mozambique Metical"),
    NAD("NAD", "Namibia Dollar"),
    NGN("NGN", "Naira"),
    NIO("NIO", "Cordoba Oro"),
    NOK("NOK", "Norwegian Krone"),
    NPR("NPR", "Nepalese Rupee"),
    NZD("NZD", "New Zealand Dollar"),
    OMR("OMR", "Rial Omani"),
    PAB("PAB", "Balboa"),
    PEN("PEN", "Sol"),
    PGK("PGK", "Kina"),
    PHP("PHP", "Philippine Piso"),
    PKR("PKR", "Pakistan Rupee"),
    PLN("PLN", "Zloty"),
    PYG("PYG", "Guarani"),
    QAR("QAR", "Qatari Rial"),

    /**
     * This currency code is effective from 1 July 2005
     */
    RON("RON", "Romanian Leu"),
    RSD("RSD", "Serbian Dinar"),
    RUB("RUB", "Russian Ruble"),
    RWF("RWF", "Rwanda Franc"),
    SAR("SAR", "Saudi Riyal"),
    SBD("SBD", "Solomon Islands Dollar"),
    SCR("SCR", "Seychelles Rupee"),
    SDG("SDG", "Sudanese Pound"),
    SEK("SEK", "Swedish Krona"),
    SGD("SGD", "Singapore Dollar"),
    SHP("SHP", "St. Helena Pound"),

    @Deprecated(forRemoval = true)
    SLE("SLE", "Leone"),

    @Deprecated
    SLL("SLL", "Leone"),
    SOS("SOS", "Somali Shilling"),
    SRD("SRD", "Suriname Dollar"),
    SSP("SSP", "South Sudanese Pound"),
    STN("STN", "Dobra"),
    SVC("SVC", "El Salvador Colon"),
    SYP("SYP", "Syrian Pound"),
    SZL("SZL", "Lilangeni"),
    THB("THB", "Baht"),
    TJS("TJS", "Somoni"),
    TMT("TMT", "Turkmenistan New Manat"),
    TND("TND", "Tunisian Dinar"),
    TOP("TOP", "Pa'anga"),
    TRY("TRY", "Turkish Lira"),
    TTD("TTD", "Trinidad and Tobago Dollar"),
    TWD("TWD", "New Taiwan Dollar"),
    TZS("TZS", "Tanzanian Shilling"),
    UAH("UAH", "Hryvnia"),
    UGX("UGX", "Uganda Shilling"),
    USD("USD", "US Dollar"),
    USN("USN", "US Dollar (Next day)"),
    UYI("UYI", "Uruguayo Peso en Unidades"),
    UYU("UYU", "Peso Uruguayo"),
    UYW("UYW", "Unidad Previsional"),
    UZS("UZS", "Uzbekistan Sum"),

    @Deprecated(forRemoval = true)
    VED("VED", "Bolívar Soberano"),
    VES("VES", "Bolívar Soberano"),
    VND("VND", "Dong"),
    VUV("VUV", "Vatu"),
    WST("WST", "Tala"),
    XAF("XAF", "CFA Franc"),
    XAG("XAG", "Silver"),
    XAU("XAU", "Gold"),
    XBA("XBA", "Bond Markets Units European Composite Unit (EURCO)"),
    XBB("XBB", "European Monetary Unit (E.M.U.-6)"),
    XBC("XBC", "European Unit of Account 9 (E.U.A.-9)"),
    XBD("XBD", "European Unit of Account 17 (E.U.A.-17)"),
    XCD("XCD", "East Carribean Dollar"),
    XDR("XDR", "SDR"),
    XOF("XOF", "CFA Franc"),
    XPD("XPD", "Palladium"),
    XPF("XPF", "CFP Franc"),
    XPT("XPT", "Platinum"),
    XSU("XSU", "Sucre"),
    XTS("XTS", "Codes specifically reserved for testing purposes"),
    XUA("XUA", "ADB Unit of Account"),
    XXX("XXX", "The codes assigned for transactions where no currency is involved"),
    YER("YER", "Yemeni Rial"),
    ZAR("ZAR", "Rand"),
    ZMW("ZMW", "Zambian Kwacha"),

    /**
     * (effective 1 February 2009)
     */
    ZWL("ZWL", "Zimbabwe Dollar"),
    ;

    private final String name;
    private final String code;

    CurrencyCodeType(String code, String name) {
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
