package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.ppf.adapters.ItemTypeIDCodeAdapter;
import io.github.up2jakarta.xml.clv.Agency;
import io.github.up2jakarta.xml.clv.CodeList;
import io.github.up2jakarta.xml.clv.Documented;
import io.github.up2jakarta.xml.clv.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 7143 : Item type identification code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred7143.htm}
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@Documented(value = "Item type identification code", agency = Agency.UN_ECE, version = "2.3")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(ItemTypeIDCodeAdapter.class)
public enum ItemTypeIDCodeType implements CodeList<ItemTypeIDCodeType> {

    /**
     * Number assigned by manufacturer or seller to identify the release of a product.
     */
    AA("AA", "Product version number"),

    /**
     * The item number is that of an assembly.
     */
    AB("AB", "Assembly"),

    /**
     * Article identifier used within health sector to indicate data used conforms to HIBC.
     */
    AC("AC", "HIBC (Health Industry Bar Code)"),

    /**
     * Number assigned to a cold roll.
     */
    AD("AD", "Cold roll number"),

    /**
     * Number assigned to a hot roll.
     */
    AE("AE", "Hot roll number"),

    /**
     * Number assigned to a slab, which is produced in a particular production step.
     */
    AF("AF", "Slab number"),

    /**
     * A number assigned to indicate a revision of software.
     */
    AG("AG", "Software revision number"),

    /**
     * An 11-digit code that uniquely identifies consumer packaging of a product; does not
     * have a check digit.
     */
    AH("AH", "UPC (Universal Product Code) Consumer package code (1-5-5)"),

    /**
     * A 12-digit code that uniquely identifies the consumer packaging of a product, including
     * a check digit.
     */
    AI("AI", "UPC (Universal Product Code) Consumer package code (1-5-5-1)"),

    /**
     * Number assigned to a sample.
     */
    AJ("AJ", "Sample number"),

    /**
     * Number assigned to a pack containing a stack of items put together (e.g. cold roll sheets
     * (steel product)).
     */
    AK("AK", "Pack number"),

    /**
     * A 13-digit code that uniquely identifies the manufacturer's shipping unit, including
     * the packaging indicator.
     */
    AL("AL", "UPC (Universal Product Code) Shipping container code (1-2-5-5)"),

    /**
     * A 14-digit code that uniquely identifies the manufacturer's shipping unit, including
     * the packaging indicator and the check digit.
     */
    AM("AM", "UPC (Universal Product Code)/EAN (European article number) Shipping container code (1-2-5-5-1)"),

    /**
     * A suffix used in conjunction with a higher level UPC (Universal product code) to define
     * packing variations for a product.
     */
    AN("AN", "UPC (Universal Product Code) suffix"),

    /**
     * A code which specifies the codification of the state's labelling requirements.
     */
    AO("AO", "State label code"),

    /**
     * Number assigned to the heat (also known as the iron charge) for the production of steel
     * products.
     */
    AP("AP", "Heat number"),

    /**
     * A number identifying a coupon.
     */
    AQ("AQ", "Coupon number"),

    /**
     * A number to identify a resource.
     */
    AR("AR", "Resource number"),

    /**
     * A number to identify a work task.
     */
    AS("AS", "Work task number"),

    /**
     * Identification number on a product allowing a quick electronic retrieval of price information
     * for that product.
     */
    AT("AT", "Price look up number"),

    /**
     * Number assigned under the NATO (North Atlantic Treaty Organization) codification system
     * to provide the identification of an approved item of supply.
     */
    AU("AU", "NSN (North Atlantic Treaty Organization Stock Number)"),

    /**
     * A code specifying the product refinement designation.
     */
    AV("AV", "Refined product code"),

    /**
     * A code indicating that the product is identified by an exhibit number.
     */
    AW("AW", "Exhibit"),

    /**
     * A number specifying an end item.
     */
    AX("AX", "End item"),

    /**
     * A code to specify a product's Federal supply classification.
     */
    AY("AY", "Federal supply classification"),

    /**
     * A code specifying the product's engineering data list.
     */
    AZ("AZ", "Engineering data list"),

    /**
     * A number to identify a milestone event.
     */
    BA("BA", "Milestone event number"),

    /**
     * A number indicating the lot number of a product.
     */
    BB("BB", "Lot number"),

    /**
     * A code identifying the product in national drug format 4-4-2.
     */
    BC("BC", "National drug code 4-4-2 format"),

    /**
     * A code identifying the product in national drug format 5-3-2.
     */
    BD("BD", "National drug code 5-3-2 format"),

    /**
     * A code identifying the product in national drug format 5-4-1.
     */
    BE("BE", "National drug code 5-4-1 format"),

    /**
     * A code identifying the product in national drug format 5-4-2.
     */
    BF("BF", "National drug code 5-4-2 format"),

    /**
     * A code specifying the national drug classification.
     */
    BG("BG", "National drug code"),

    /**
     * A number indicating the part.
     */
    BH("BH", "Part number"),

    /**
     * A local number assigned to an item of stock.
     */
    BI("BI", "Local Stock Number (LSN)"),

    /**
     * A number specifying the next higher assembly or component into which the product is
     * being incorporated.
     */
    BJ("BJ", "Next higher assembly number"),

    /**
     * A code specifying a category of data.
     */
    BK("BK", "Data category"),

    /**
     * To specify the control number.
     */
    BL("BL", "Control number"),

    /**
     * A number to identify the special material code.
     */
    BM("BM", "Special material identification code"),

    /**
     * A number assigned locally for control purposes.
     */
    BN("BN", "Locally assigned control number"),

    /**
     * Colour assigned by buyer.
     */
    BO("BO", "Buyer's colour"),

    /**
     * Reference number assigned by the buyer to identify an article.
     */
    BP("BP", "Buyer's part number"),

    /**
     * A code assigned to identify a variable measure item.
     */
    BQ("BQ", "Variable measure product code"),

    /**
     * To specify as an item, the financial phase.
     */
    BR("BR", "Financial phase"),

    /**
     * To specify as an item, the contract breakdown.
     */
    BS("BS", "Contract breakdown"),

    /**
     * To specify as an item, the technical phase.
     */
    BT("BT", "Technical phase"),

    /**
     * Number identifying a dye lot.
     */
    BU("BU", "Dye lot number"),

    /**
     * A statement listing activities of one day.
     */
    BV("BV", "Daily statement of activities"),

    /**
     * Periodical statement listing activities within a bilaterally agreed time period.
     */
    BW("BW", "Periodical statement of activities within a bilaterally agreed time period"),

    /**
     * A statement listing activities of a calendar week.
     */
    BX("BX", "Calendar week statement of activities"),

    /**
     * A statement listing activities of a calendar month.
     */
    BY("BY", "Calendar month statement of activities"),

    /**
     * Original equipment number allocated to spare parts by the manufacturer.
     */
    BZ("BZ", "Original equipment number"),

    /**
     * The codes given to certain commodities by an industry.
     */
    CC("CC", "Industry commodity code"),

    /**
     * Code for a group of articles with common characteristics (e.g. used for statistical
     * purposes).
     */
    CG("CG", "Commodity grouping"),

    /**
     * Code for the colour of an article.
     */
    CL("CL", "Colour number"),

    /**
     * Reference number identifying a contract.
     */
    CR("CR", "Contract number"),

    /**
     * Code defined by Customs authorities to an article or a group of articles for Customs
     * purposes.
     */
    CV("CV", "Customs article number"),

    /**
     * Reference number indicating that a change or revision has been applied to a drawing.
     */
    DR("DR", "Drawing revision number"),

    /**
     * Reference number identifying a drawing of an article.
     */
    DW("DW", "Drawing"),

    /**
     * Reference number indicating that a change or revision has been applied to an article's
     * specification.
     */
    EC("EC", "Engineering change level"),

    /**
     * Code defining the material's type, surface, geometric form plus various classifying
     * characteristics.
     */
    EF("EF", "Material code"),
    EMD("EMD", "EMDN (European Medical Device Nomenclature)"),

    /**
     * Number assigned to a manufacturer's product according to the International Article Numbering
     * Association.
     */
    EN("EN", "International Article Numbering Association (EAN)"),

    /**
     * Identification of fish species.
     */
    FS("FS", "Fish species"),

    /**
     * Product group code used within a buyer's internal systems.
     */
    GB("GB", "Buyer's internal product group code"),

    /**
     * National product group code. Administered by a national agency.
     */
    GN("GN", "National product group code"),

    /**
     * The item number is a general specification number.
     */
    GS("GS", "General specification number"),

    /**
     * The item number is part of, or is generated in the context of the Harmonised Commodity
     * Description and Coding System (Harmonised System), as developed and maintained by the
     * World Customs Organization (WCO).
     */
    HS("HS", "Harmonised system"),

    /**
     * A unique number identifying a book.
     */
    IB("IB", "ISBN (International Standard Book Number)"),

    /**
     * The item number has been allocated by the buyer.
     */
    IN("IN", "Buyer's item number"),

    /**
     * A unique number identifying a serial publication.
     */
    IS("IS", "ISSN (International Standard Serial Number)"),

    /**
     * Number given by the buyer to a specific style or form of an article, especially used
     * for garments.
     */
    IT("IT", "Buyer's style number"),

    /**
     * Code given by the buyer to designate the size of an article in textile and shoe industry.
     */
    IZ("IZ", "Buyer's size code"),

    /**
     * The item number is a machine number.
     */
    MA("MA", "Machine number"),

    /**
     * The number given to an article by its manufacturer.
     */
    MF("MF", "Manufacturer's (producer's) article number"),

    /**
     * Reference number assigned by the manufacturer to differentiate variations in similar
     * products in a class or group.
     */
    MN("MN", "Model number"),

    /**
     * Reference number identifying a product or service.
     */
    MP("MP", "Product/service identification number"),

    /**
     * The item number is a batch number.
     */
    NB("NB", "Batch number"),

    /**
     * Reference number of a customer's order.
     */
    ON("ON", "Customer order number"),

    /**
     * Reference number identifying a description associated with a number ultimately used
     * to identify an article.
     */
    PD("PD", "Part number description"),

    /**
     * Reference number identifying a line entry in a customer's order for goods or services.
     */
    PL("PL", "Purchaser's order line number"),

    /**
     * Reference number identifying a customer's order.
     */
    PO("PO", "Purchase order number"),

    /**
     * The item number is a promotional variant number.
     */
    PV("PV", "Promotional variant number"),

    /**
     * The item number qualifies the size of the buyer.
     */
    QS("QS", "Buyer's qualifier for size"),

    /**
     * Reference number identifying a returnable container.
     */
    RC("RC", "Returnable container number"),

    /**
     * Reference number identifying a release from a buyer's purchase order.
     */
    RN("RN", "Release number"),

    /**
     * The item number identifies the production or manufacturing run or sequence in which
     * the item was manufactured, processed or assembled.
     */
    RU("RU", "Run number"),

    /**
     * The item number relates to the year in which the particular model was kept.
     */
    RY("RY", "Record keeping of model year"),

    /**
     * Number assigned to an article by the supplier of that article.
     */
    SA("SA", "Supplier's article number"),

    /**
     * The item number relates to a standard group of other items (mixed) which are grouped
     * together as a single item for identification purposes.
     */
    SG("SG", "Standard group of products (mixed assortment)"),

    /**
     * Reference number of a stock keeping unit.
     */
    SK("SK", "SKU (Stock keeping unit)"),

    /**
     * Identification number of an item which distinguishes this specific item out of a number
     * of identical items.
     */
    SN("SN", "Serial number"),

    /**
     * Plumbing and heating.
     */
    SRS("SRS", "RSK number"),

    /**
     * 5 digit code for product classification managed by the Institut Francais du Libre Service.
     */
    SRT("SRT", "IFLS (Institut Francais du Libre Service) 5 digit product classification code"),

    /**
     * 9 digit code for product classification managed by the Institut Francais du Libre Service.
     */
    SRU("SRU", "IFLS (Institut Francais du Libre Service) 9 digit product classification code"),

    /**
     * A unique number, up to 14-digits, assigned according to the numbering structure of the
     * GS1 system.
     */
    SRV("SRV", "GS1 Global Trade Item Number"),

    /**
     * European system for identification of meter data.
     */
    SRW("SRW", "EDIS (Energy Data Identification System)"),

    /**
     * Unique number given by a slaughterhouse to an animal or a group of animals of the same
     * breed.
     */
    SRX("SRX", "Slaughter number"),

    /**
     * Unique number given by a national authority to identify an animal individually.
     */
    SRY("SRY", "Official animal number"),

    /**
     * The international Harmonized Tariff Schedule (HTS) to classify the article for customs,
     * statistical and other purposes.
     */
    SRZ("SRZ", "Harmonized tariff schedule"),

    /**
     * Article number referring to a sales catalogue of supplier's supplier.
     */
    SS("SS", "Supplier's supplier article number"),

    /**
     * A US Department of Transportation (DOT) code to identify hazardous (dangerous) goods,
     * managed by the Customs and Border Protection (CBP) agency.
     */
    SSA("SSA", "46 Level DOT Code"),

    /**
     * A US code agreed to by the airline industry to identify hazardous (dangerous) goods,
     * managed by the Customs and Border Protection (CBP) agency.
     */
    SSB("SSB", "Airline Tariff 6D"),

    /**
     * A US Customs and Border Protection (CBP) code used to identify hazardous (dangerous)
     * goods.
     */
    SSC("SSC", "Title 49 Code of Federal Regulations"),

    /**
     * A US Department of Transportation/Federal Aviation Administration code used to identify
     * hazardous (dangerous) goods, managed by the Customs and Border Protection (CBP) agency.
     */
    SSD("SSD", "International Civil Aviation Administration code"),

    /**
     * A US Department of Transportation (DOT) code used to identify hazardous (dangerous)
     * goods, managed by the Customs and Border Protection (CBP) agency.
     */
    SSE("SSE", "Hazardous Materials ID DOT"),

    /**
     * A US Customs and Border Protection (CBP) code used to identify hazardous (dangerous)
     * goods.
     */
    SSF("SSF", "Endorsement"),

    /**
     * A department of Defense/Air Force code used to identify hazardous (dangerous) goods,
     * managed by the Customs and Border Protection (CBP) agency.
     */
    SSG("SSG", "Air Force Regulation 71-4"),

    /**
     * The breed of the item (e.g. plant or animal).
     */
    SSH("SSH", "Breed"),

    /**
     * A unique numerical identifier for for chemical compounds, polymers, biological sequences,
     * mixtures and alloys.
     */
    SSI("SSI", "Chemical Abstract Service (CAS) registry number"),

    /**
     * A name or designation to identify an engine model.
     */
    SSJ("SSJ", "Engine model designation"),

    /**
     * A number assigned by agricultural authorities to identify and track meat and meat products.
     */
    SSK("SSK", "Institutional Meat Purchase Specifications (IMPS) Number"),

    /**
     * Identification number affixed to produce in stores to retrieve price information.
     */
    SSL("SSL", "Price Look-Up code (PLU)"),

    /**
     * An International Maritime Organization (IMO) code used to identify hazardous (dangerous)
     * goods.
     */
    SSM("SSM", "International Maritime Organization (IMO) Code"),

    /**
     * A Department of Transportation/Federal Railroad Administration code used to identify
     * hazardous (dangerous) goods.
     */
    SSN("SSN", "Bureau of Explosives 600-A (rail)"),

    /**
     * A UN code used to classify and identify dangerous goods.
     */
    SSO("SSO", "United Nations Dangerous Goods List"),

    /**
     * A code established by the International Code of Botanical Nomenclature (ICBN) used to
     * classify and identify botanical articles and commodities.
     */
    SSP("SSP", "International Code of Botanical Nomenclature (ICBN)"),

    /**
     * A code established by the International Code of Zoological Nomenclature (ICZN) used
     * to classify and identify animals.
     */
    SSQ("SSQ", "International Code of Zoological Nomenclature (ICZN)"),

    /**
     * A code established by the International Code of Nomenclature for Cultivated Plants (ICNCP)
     * used to classify and identify animals.
     */
    SSR("SSR", "International Code of Nomenclature for Cultivated Plants (ICNCP)"),

    /**
     * Identifier assigned to an article by the distributor of that article.
     */
    SSS("SSS", "Distributor's article identifier"),

    /**
     * Product classification system used in the Norwegian market.
     */
    SST("SST", "Norwegian Classification system ENVA"),

    /**
     * Product classification assigned by the supplier.
     */
    SSU("SSU", "Supplier assigned classification"),

    /**
     * Product classification system used in the Mexican market.
     */
    SSV("SSV", "Mexican classification system AMECE"),

    /**
     * Product classification system used in the German market.
     */
    SSW("SSW", "German classification system CCG"),

    /**
     * Product classification system used in the Finnish market.
     */
    SSX("SSX", "Finnish classification system EANFIN"),

    /**
     * Product classification system used in the Canadian market.
     */
    SSY("SSY", "Canadian classification system ICC"),

    /**
     * Product classification system used in the French market.
     */
    SSZ("SSZ", "French classification system IFLS5"),

    /**
     * Number given to a specific style or form of an article, especially used for garments.
     */
    ST("ST", "Style number"),

    /**
     * Product classification system used in the Dutch market.
     */
    STA("STA", "Dutch classification system CBL"),

    /**
     * Product classification system used in the Japanese market.
     */
    STB("STB", "Japanese classification system JICFS"),

    /**
     * Category of product eligible for EU subsidy (applies for certain dairy products with
     * specific level of fat content).
     */
    STC("STC", "European Union dairy subsidy eligibility classification"),

    /**
     * Product classification system used in the Spanish market.
     */
    STD("STD", "GS1 Spain classification system"),

    /**
     * Product classification system used in the Polish market.
     */
    STE("STE", "GS1 Poland classification system"),

    /**
     * A Russian government agency that serves as a national standardization body of the Russian
     * Federation.
     */
    STF("STF", "Federal Agency on Technical Regulating and Metrology of the Russian Federation"),

    /**
     * Product classification system used in the Austrian market.
     */
    STG("STG", "Efficient Consumer Response (ECR) Austria classification system"),

    /**
     * Product classification system used in the Italian market.
     */
    STH("STH", "GS1 Italy classification system"),

    /**
     * Official classification system for public procurement in the European Union.
     */
    STI("STI", "CPV (Common Procurement Vocabulary)"),

    /**
     * International Foodservice Distributors Association (IFDA).
     */
    STJ("STJ", "IFDA (International Foodservice Distributors Association)"),

    /**
     * Pharmacologic - therapeutic classification maintained by the American Hospital Formulary
     * Service (AHFS).
     */
    STK("STK", "AHFS (American Hospital Formulary Service) pharmacologic - therapeutic classification"),

    /**
     * Anatomical Therapeutic Chemical classification system maintained by the World Health
     * Organisation (WHO).
     */
    STL("STL", "ATC (Anatomical Therapeutic Chemical) classification system"),

    /**
     * A five level classification system for medical decvices maintained by the CLADIMED organisation
     * used in the French market.
     */
    STM("STM", "CLADIMED (Classification des Dispositifs Médicaux)"),

    /**
     * Classification system related to the Canadian Medical Device Regulations maintained
     * by Health Canada.
     */
    STN("STN", "CMDR (Canadian Medical Device Regulations) classification system"),

    /**
     * A classification system for medical devices used in the Italian market.
     */
    STO("STO", "CNDM (Classificazione Nazionale dei Dispositivi Medici)"),

    /**
     * A classification system for medicines and devices used in the UK market.
     */
    STP("STP", "UK DM&D (Dictionary of Medicines & Devices) standard coding scheme"),

    /**
     * Standardized material and service classification and dictionary maintained by eClass
     * e.V.
     */
    STQ("STQ", "ECl@ss"),

    /**
     * Classification for in vitro diagnostics medical devices maintained by the European Diagnostic
     * Manufacturers Association.
     */
    STR("STR", "EDMA (European Diagnostic Manufacturers Association) Products Classification"),

    /**
     * A classification system for medical devices.
     */
    STS("STS", "EGAR (European Generic Article Register)"),

    /**
     * Nomenclature system for identification of medical devices officially apprroved by the
     * European Union.
     */
    STT("STT", "GMDN (Global Medical Devices Nomenclature)"),

    /**
     * A drug classification system managed by Medi-Span.
     */
    STU("STU", "GPI (Generic Product Identifier)"),

    /**
     * A classification system used with US healthcare insurance programs.
     */
    STV("STV", "HCPCS (Healthcare Common Procedure Coding System)"),

    /**
     * A patient safety taxonomy maintained by the World Health Organisation.
     */
    STW("STW", "ICPS (International Classification for Patient Safety)"),

    /**
     * A medical dictionary maintained by the International Federation of Pharmaceutical Manufacturers
     * and Associations (IFPMA).
     */
    STX("STX", "MedDRA (Medical Dictionary for Regulatory Activities)"),

    /**
     * Medical product classification system used in the German market.
     */
    STY("STY", "Medical Columbus"),

    /**
     * Product classification system used in the North American market.
     */
    STZ("STZ", "NAPCS (North American Product Classification System)"),

    /**
     * Product and Service classification system used in United Kingdom market.
     */
    SUA("SUA", "NHS (National Health Services) eClass"),

    /**
     * US FDA Product Code Classification Database contains medical device names and associated
     * information developed by the Center for Devices and Radiological Health (CDRH).
     */
    SUB("SUB", "US FDA (Food and Drug Administration) Product Code Classification Database"),

    /**
     * A medical nomenclature system developed between the NHS and the College of American
     * Pathologists.
     */
    SUC("SUC", "SNOMED CT (Systematized Nomenclature of Medicine-Clinical Terms)"),

    /**
     * A standard international nomenclature and computer coding system for medical devices
     * maintained by the Emergency Care Research Institute (ECRI).
     */
    SUD("SUD", "UMDNS (Universal Medical Device Nomenclature System)"),

    /**
     * A unique, 13-digit number assigned according to the numbering structure of the GS1 system
     * and used to identify a type of Reusable Transport Item (RTI).
     */
    SUE("SUE", "GS1 Global Returnable Asset Identifier, non-serialised"),

    /**
     * The International Mobile Station Equipment Identity (IMEI) is a unique number to identify
     * mobile phones. It includes the origin, model and serial number of the device. The structure
     * is specified in 3GPP TS 23.003.
     */
    SUF("SUF", "IMEI"),

    /**
     * Classification of waste as defined by the European Maritime Safety Agency (EMSA).
     */
    SUG("SUG", "Waste Type (EMSA)"),

    /**
     * Classification of ship's stores.
     */
    SUH("SUH", "Ship's store classification type"),

    /**
     * Classification for emergency response procedures related to fire.
     */
    SUI("SUI", "Emergency fire code"),

    /**
     * Classification for emergency response procedures related to spillage.
     */
    SUJ("SUJ", "Emergency spillage code"),

    /**
     * Packing group as defined in the International Marititme Dangerous Goods (IMDG) specification.
     */
    SUK("SUK", "IMDG packing group"),

    /**
     * International Bulk Chemical (IBC) code defined by the International Convention for the
     * Prevention of Pollution from Ships (MARPOL).
     */
    SUL("SUL", "MARPOL Code IBC"),

    /**
     * Subsidiary risk class as defined in the International Maritime Dangerous Goods (IMDG)
     * specification.
     */
    SUM("SUM", "IMDG subsidiary risk class"),

    /**
     * (8012) Additional number to form article groups for packing and/or transportation purposes.
     */
    TG("TG", "Transport group number"),

    /**
     * A unique number assigned to a taxonomic entity, commonly to a species of plants or animals,
     * providing information on their hierarchical classification, scientific name, taxonomic
     * rank, associated synonyms and vernacular names where appropriate, data source information
     * and data quality indicators.
     */
    TSN("TSN", "Taxonomic Serial Number"),

    /**
     * Main hazard class as defined in the International Maritime Dangerous Goods (IMDG) specification.
     */
    TSO("TSO", "IMDG main hazard class"),

    /**
     * The number is part of, or is generated in the context of the Combined Nomenclature classification,
     * as developed and maintained by the European Union (EU).
     */
    TSP("TSP", "EU Combined Nomenclature"),

    /**
     * A code to specify a product's therapeutic classification.
     */
    TSQ("TSQ", "Therapeutic classification number"),

    /**
     * Waste type number according to the European Waste Catalogue (EWC).
     */
    TSR("TSR", "European Waste Catalogue"),

    /**
     * Number assigned to identify a grouping of products based on price.
     */
    TSS("TSS", "Price grouping code"),

    /**
     * Number assigned by ultimate customer to identify relevant article.
     */
    UA("UA", "Ultimate customer's article number"),

    /**
     * Number assigned to a manufacturer's product by the Product Code Council.
     */
    UP("UP", "UPC (Universal product code)"),

    /**
     * Reference number assigned by a vendor/seller identifying a product/service/article.
     */
    VN("VN", "Vendor item number"),

    /**
     * Reference number assigned by a vendor/seller identifying an article.
     */
    VP("VP", "Vendor's (seller's) part number"),

    /**
     * The item number is a specified by the vendor as a supplemental number for the vendor's
     * purposes.
     */
    VS("VS", "Vendor's supplemental item number"),

    /**
     * The item number has been allocated by the vendor as a specification number.
     */
    VX("VX", "Vendor specification number"),

    /**
     * Item type identification mutually agreed between interchanging parties.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    ItemTypeIDCodeType(String code, String name) {
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
