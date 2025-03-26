package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.ppf.adapters.EASchemeIDAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.Documented;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on FR/PPF EAS : Electronic Address Scheme Identifier.
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@Documented(value = "Electronic Address Scheme Identifier", agency = Agency.EN_16931, version = "2.3")
@XmlJavaTypeAdapter(EASchemeIDAdapter.class)
public enum EASchemeIDType implements PartySchemeIDType<EASchemeIDType> {

    V_0002("0002", "System Information et Repertoire des Entreprise et des Etablissements: SIRENE"),
    V_0007("0007", "Organisationsnummer"),
    V_0009("0009", "SIRET-CODE"),
    V_0037("0037", "LY-tunnus"),
    V_0060("0060", "Data Universal Numbering System (D-U-N-S Number)"),
    V_0088("0088", "EAN Location Code"),
    V_0096("0096", "DANISH CHAMBER OF COMMERCE Scheme (EDIRA compliant)"),
    V_0097("0097", "FTI - Ediforum Italia, (EDIRA compliant)"),
    V_0106("0106", "Vereniging van Kamers van Koophandel en Fabrieken in Nederland (Association of Chambers of Commerce and Industry in the Netherlands), Scheme (EDIRA compliant)"),
    V_0130("0130", "Directorates of the European Commission"),
    V_0135("0135", "SIA Object Identifiers"),
    V_0142("0142", "SECETI Object Identifiers"),
    V_0151("0151", "Australian Business Number (ABN) Scheme"),
    V_0183("0183", "Numéro d'identification suisse des enterprises (IDE), Swiss Unique Business Identification Number (UIDB)"),
    V_0184("0184", "DIGSTORG"),
    V_0190("0190", "Dutch Originator's Identification Number"),
    V_0191("0191", "Centre of Registers and Information Systems of the Ministry of Justice"),
    V_0192("0192", "Enhetsregisteret ved Bronnoysundregisterne"),
    V_0193("0193", "UBL.BE party identifier"),
    V_0194("0194", "KOIOS Open Technical Dictionary"),
    V_0195("0195", "Singapore UEN identifier"),
    V_0196("0196", "Kennitala - Iceland legal id for individuals and legal entities"),
    V_0198("0198", "ERSTORG"),
    V_0199("0199", "Legal Entity Identifier (LEI)"),
    V_0200("0200", "Legal entity code (Lithuania)"),
    V_0201("0201", "Codice Univoco Unità Organizzativa iPA"),
    V_0202("0202", "Indirizzo di Posta Elettronica Certificata"),
    V_0203("0203", "EDelivery Network Participant identifier"),
    V_0204("0204", "Leitweg-ID"),
    V_0208("0208", "Numero d'entreprise / ondernemingsnummer / Unternehmensnummer"),
    V_0209("0209", "GS1 identification keys"),
    V_0210("0210", "CODICE FISCALE"),
    V_0211("0211", "PARTITA IVA"),
    V_0212("0212", "Finnish Organization Identifier"),
    V_0213("0213", "Finnish Organization Value Add Tax Identifier"),
    V_0214("0214", "Tradeplace TradePI Standard"),
    V_0215("0215", "Net service ID"),
    V_0216("0216", "OVTcode"),
    V_0217("0217", "The Netherlands Chamber of Commerce and Industry establishment number"),
    V_0218("0218", "Unified registration number (Latvia)"),
    V_0219("0219", "Taxpayer registration code (Latvia)"),
    V_0220("0220", "The Register of Natural Persons (Latvia)"),
    V_0221("0221", "The registered number of the qualified invoice issuer"),
    V_0222("0222", "Metadata Registry Support"),
    V_9901("9901", "Danish Ministry of the Interior and Health"),
    V_9902("9902", "The Danish Commerce and Companies Agency"),
    V_9904("9904", "Danish Ministry of Taxation, Central Customs and Tax Administration"),
    V_9905("9905", "Danish VANS providers"),
    V_9906("9906", "Ufficio responsabile gestione partite IVA"),
    V_9907("9907", "TAX Authority"),
    V_9910("9910", "Hungary VAT number"),
    V_9913("9913", "Business Registers Network"),
    V_9914("9914", "Österreichische Umsatzsteuer-Identifikationsnummer"),
    V_9915("9915", "Österreichisches Verwaltungs bzw. Organisationskennzeichen"),
    V_9917("9917", "Kennitala - Iceland legal id for organizations and individuals"),
    V_9918("9918", "SOCIETY FOR WORLDWIDE INTERBANK FINANCIAL, TELECOMMUNICATION S.W.I.F.T"),
    V_9919("9919", "Kennziffer des Unternehmensregisters"),
    V_9920("9920", "Agencia Española de Administración Tributaria"),
    V_9921("9921", "Indice delle Pubbliche Amministrazioni"),
    V_9922("9922", "Andorra VAT number"),
    V_9923("9923", "Albania VAT number"),
    V_9924("9924", "Bosnia and Herzegovina VAT number"),
    V_9925("9925", "Belgium VAT number"),
    V_9926("9926", "Bulgaria VAT number"),
    V_9927("9927", "Switzerland VAT number"),
    V_9928("9928", "Cyprus VAT number"),
    V_9929("9929", "Czech Republic VAT number"),
    V_9930("9930", "Germany VAT number"),
    V_9931("9931", "Estonia VAT number"),
    V_9932("9932", "United Kingdom VAT number"),
    V_9933("9933", "Greece VAT number"),
    V_9934("9934", "Croatia VAT number"),
    V_9935("9935", "Ireland VAT number"),
    V_9936("9936", "Liechtenstein VAT number"),
    V_9937("9937", "Lithuania VAT number"),
    V_9938("9938", "Luxemburg VAT number"),
    V_9939("9939", "Latvia VAT number"),
    V_9940("9940", "Monaco VAT number"),
    V_9941("9941", "Montenegro VAT number"),
    V_9942("9942", "Macedonia, the former Yugoslav Republic of VAT number"),
    V_9943("9943", "Malta VAT number"),
    V_9944("9944", "Netherlands VAT number"),
    V_9945("9945", "Poland VAT number"),
    V_9946("9946", "Portugal VAT number"),
    V_9947("9947", "Romania VAT number"),
    V_9948("9948", "Serbia VAT number"),
    V_9949("9949", "Slovenia VAT number"),
    V_9950("9950", "Slovakia VAT number"),
    V_9951("9951", "San Marino VAT number"),
    V_9952("9952", "Turkey VAT number"),
    V_9953("9953", "Holy See (Vatican City State) VAT number"),
    V_9956("9956", "Belgian Crossroad Bank of Enterprises"),
    V_9957("9957", "French VAT number"),
    V_9958("9958", "German Leitweg ID"),
    AN("AN", "O.F.T.P. (ODETTE File Transfer Protocol)"),
    AQ("AQ", "X.400 address for mail text"),
    AS("AS", "AS2 exchange"),
    AU("AU", "File Transfer Protocol"),
    EM("EM", "Electronic mail (SMPT)"),
    ;

    private final String name;
    private final String code;

    EASchemeIDType(String code, String name) {
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
