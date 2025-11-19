package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.Documented;
import io.github.up2jakarta.cii.core.Agency;
import io.github.up2jakarta.cii.edi.adapters.TaxTypeCodeAdapter;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 5153 : Duty or tax or fee type name code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred5153.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Duty Tax Fee Type Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(TaxTypeCodeAdapter.class)
public enum TaxTypeCodeType implements CodeList<TaxTypeCodeType> {

    /**
     * A tax levied on the volume of petroleum being transacted.
     */
    AAA("AAA", "Petroleum tax"),

    /**
     * Countervailing duty paid in cash prior to a formal finding of subsidization by Customs.
     */
    AAB("AAB", "Provisional countervailing duty cash"),

    /**
     * Countervailing duty paid by posting a bond during an investigation period prior to a
     * formal decision on subsidization by Customs.
     */
    AAC("AAC", "Provisional countervailing duty bond"),

    /**
     * A tax levied on tobacco products.
     */
    AAD("AAD", "Tobacco tax"),

    /**
     * General fee or tax for the use of energy.
     */
    AAE("AAE", "Energy fee"),

    /**
     * A tax levied specifically on coffee products.
     */
    AAF("AAF", "Coffee tax"),

    /**
     * A harmonized sales tax consisting of a goods and service tax, a Canadian provincial
     * sales tax and, as applicable, a Quebec sales tax which is recoverable.
     */
    AAG("AAG", "Harmonised sales tax, Canadian"),

    /**
     * A sales tax charged within the Canadian province of Quebec which is recoverable.
     */
    AAH("AAH", "Quebec sales tax"),

    /**
     * A sales tax charged within Canadian provinces which is non-recoverable.
     */
    AAI("AAI", "Canadian provincial sales tax"),

    /**
     * A tax levied on a replacement part, where the original part is returned.
     */
    AAJ("AAJ", "Tax on replacement part"),

    /**
     * Tax that is levied specifically on products containing mineral oil.
     */
    AAK("AAK", "Mineral oil tax"),

    /**
     * To indicate a special type of tax.
     */
    AAL("AAL", "Special tax"),

    /**
     * A tax levied specifically on insurances.
     */
    AAM("AAM", "Insurance tax"),

    /**
     * A tax levied on Cannabis products
     */
    AAO("AAO", "Provincial Cannabis Tax"),

    /**
     * Fee levied due to outstanding duties to be paid
     */
    AAP("AAP", "Outstanding duty interest"),

    /**
     * Duty applied to goods ruled to have been dumped in an import market at a price lower
     * than that in the exporter's domestic market.
     */
    ADD("ADD", "Anti-dumping duty"),

    /**
     * Tax required in Italy, which may be fixed or graduated in various circumstances (e.g.
     * VAT exempt documents or bank receipts).
     */
    BOL("BOL", "Stamp duty (Imposta di Bollo)"),

    /**
     * Levy imposed on agricultural products where there is a difference between the selling
     * price between trading countries.
     */
    CAP("CAP", "Agricultural levy"),

    /**
     * A tax that is levied on the value of the automobile.
     */
    CAR("CAR", "Car tax"),

    /**
     * Italian Paper consortium tax.
     */
    COC("COC", "Paper consortium tax (Italy)"),

    /**
     * Tax related to a specified commodity, e.g. illuminants, salts.
     */
    CST("CST", "Commodity specific tax"),

    /**
     * Duties laid down in the Customs tariff, to which goods are liable on entering or leaving
     * the Customs territory (CCC).
     */
    CUD("CUD", "Customs duty"),

    /**
     * A duty on imported goods applied for compensate for subsidies granted to those goods
     * in the exporting country.
     */
    CVD("CVD", "Countervailing duty"),

    /**
     * Tax assessed for funding or assuring environmental protection or clean-up.
     */
    ENV("ENV", "Environmental tax"),

    /**
     * Customs or fiscal authorities code to identify a specific or ad valorem levy on a specific
     * commodity, applied either domestically or at time of importation.
     */
    EXC("EXC", "Excise duty"),

    /**
     * Monetary rebate given to the seller in certain circumstances when agricultural products
     * are exported.
     */
    EXP("EXP", "Agricultural export rebate"),

    /**
     * Tax levied by the federal government on the manufacture of specific items.
     */
    FET("FET", "Federal excise tax"),

    /**
     * No tax levied.
     */
    FRE("FRE", "Free"),

    /**
     * General tax for construction.
     */
    GCN("GCN", "General construction tax"),

    /**
     * Tax levied on the final consumption of goods and services throughout the production
     * and distribution chain.
     */
    GST("GST", "Goods and services tax"),

    /**
     * Tax of illuminants.
     */
    ILL("ILL", "Illuminants tax"),

    /**
     * Tax assessed on imports.
     */
    IMP("IMP", "Import tax"),

    /**
     * A tax levied based on an individual's ability to pay.
     */
    IND("IND", "Individual tax"),

    /**
     * Government assessed charge for permit to do business.
     */
    LAC("LAC", "Business license fee"),

    /**
     * Local tax for construction.
     */
    LCN("LCN", "Local construction tax"),

    /**
     * Fee levied on a vessel to pay for port navigation lights.
     */
    LDP("LDP", "Light dues payable"),

    /**
     * Assessment charges on sale of goods or services by city, borough country or other taxing
     * authorities below state or provincial level.
     */
    LOC("LOC", "Local sales tax"),

    /**
     * Tax imposed for clean-up of leaky underground storage tanks.
     */
    LST("LST", "Lust tax"),

    /**
     * Levy on Common Agricultural Policy (European Union) goods used to compensate for fluctuating
     * currencies between member states.
     */
    MCA("MCA", "Monetary compensatory amount"),

    /**
     * Duty paid and held on deposit, by Customs, during an investigation period prior to a
     * final decision being made on any aspect related to imported goods (except valuation)
     * by Customs.
     */
    MCD("MCD", "Miscellaneous cash deposit"),

    /**
     * Unspecified, miscellaneous tax charges.
     */
    OTH("OTH", "Other taxes"),

    /**
     * Anti-dumping duty paid by posting a bond during an investigation period prior to a formal
     * decision on dumping by Customs.
     */
    PDB("PDB", "Provisional duty bond"),

    /**
     * Anti-dumping duty paid in cash prior to a formal finding of dumping by Customs.
     */
    PDC("PDC", "Provisional duty cash"),

    /**
     * Duties laid down in the Customs tariff, to which goods are liable on entering or leaving
     * the Customs territory falling under a preferential regime such as Generalised System
     * of Preferences (GSP).
     */
    PRF("PRF", "Preference duty"),

    /**
     * Special tax for construction.
     */
    SCN("SCN", "Special construction tax"),

    /**
     * Social securities share of the invoice amount to be paid directly to the social securities
     * collector.
     */
    SSS("SSS", "Shifted social securities"),

    /**
     * All applicable sale taxes by authorities at the state or provincial level, below national
     * level.
     */
    STT("STT", "State/provincial sales tax"),

    /**
     * Duty suspended or deferred from payment.
     */
    SUP("SUP", "Suspended duty"),

    /**
     * A tax or duty applied on and in addition to existing duties and taxes.
     */
    SUR("SUR", "Surtax"),

    /**
     * Wage tax share of the invoice amount to be paid directly to the tax collector(s office).
     */
    SWT("SWT", "Shifted wage tax"),

    /**
     * A tax levied based on the type of alcohol being obtained.
     */
    TAC("TAC", "Alcohol mark tax"),

    /**
     * The summary amount of all taxes.
     */
    TOT("TOT", "Total"),

    /**
     * Tax levied on the total sales/turnover of a corporation.
     */
    TOX("TOX", "Turnover tax"),

    /**
     * Tax levied based on the vessel's net tonnage.
     */
    TTA("TTA", "Tonnage taxes"),

    /**
     * Duty paid and held on deposit, by Customs, during an investigation period prior to a
     * formal decision on valuation of the goods being made.
     */
    VAD("VAD", "Valuation deposit"),

    /**
     * A tax on domestic or imported goods applied to the value added at each stage in the
     * production/distribution cycle.
     */
    VAT("VAT", "Value added tax"),
    ;

    private final String name;
    private final String code;

    TaxTypeCodeType(String code, String name) {
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
