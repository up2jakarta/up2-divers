package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.PackageTypeCodeAdapter;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.CodeList;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 7065 : Package type description code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred7065.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Package Type Code", agency = Agency.UN_ECE, version = "2006")
@Schema(agency = "UN/CEFACT", version = "3.3", date = "2008-08-23")
@XmlJavaTypeAdapter(PackageTypeCodeAdapter.class)
public enum PackageTypeCodeType implements CodeList<PackageTypeCodeType> {

    V_43("43", "Bag, super bulk"),

    /**
     * A type of plastic bag, typically used to wrap promotional pieces, publications, product
     * samples, and/or catalogues.
     */
    V_44("44", "Bag, polybag"),
    V_1A("1A", "Drum, steel"),
    V_1B("1B", "Drum, aluminium"),
    V_1D("1D", "Drum, plywood"),

    /**
     * A packaging container of flexible construction.
     */
    V_1F("1F", "Container, flexible"),
    V_1G("1G", "Drum, fibre"),
    V_1W("1W", "Drum, wooden"),
    V_2C("2C", "Barrel, wooden"),
    V_3A("3A", "Jerrican, steel"),
    V_3H("3H", "Jerrican, plastic"),
    V_4A("4A", "Box, steel"),
    V_4B("4B", "Box, aluminium"),
    V_4C("4C", "Box, natural wood"),
    V_4D("4D", "Box, plywood"),
    V_4F("4F", "Box, reconstituted wood"),
    V_4G("4G", "Box, fibreboard"),
    V_4H("4H", "Box, plastic"),
    V_5H("5H", "Bag, woven plastic"),
    V_5L("5L", "Bag, textile"),
    V_5M("5M", "Bag, paper"),
    V_6H("6H", "Composite packaging, plastic receptacle"),
    V_6P("6P", "Composite packaging, glass receptacle"),

    /**
     * A type of portable container designed to store equipment for carriage in an automobile.
     */
    V_7A("7A", "Case, car"),

    /**
     * A case made of wood for retaining substances or articles.
     */
    V_7B("7B", "Case, wooden"),

    /**
     * A platform or open-ended box, made of wood, on which goods are retained for ease of
     * mechanical handling during transport and storage.
     */
    V_8A("8A", "Pallet, wooden"),

    /**
     * A receptacle, made of wood, on which goods are retained for ease of mechanical handling
     * during transport and storage.
     */
    V_8B("8B", "Crate, wooden"),

    /**
     * Loose or unpacked pieces of wood tied or wrapped together.
     */
    V_8C("8C", "Bundle, wooden"),
    AA("AA", "Intermediate bulk container, rigid plastic"),
    AB("AB", "Receptacle, fibre"),
    AC("AC", "Receptacle, paper"),
    AD("AD", "Receptacle, wooden"),
    AE("AE", "Aerosol"),
    AF("AF", "Pallet, modular, collars 80cms × 60cms"),
    AG("AG", "Pallet, shrinkwrapped"),
    AH("AH", "Pallet, 100cms × 110cms"),
    AI("AI", "Clamshell"),
    AJ("AJ", "Cone"),

    /**
     * A spherical containment vessel for retaining substances or articles.
     */
    AL("AL", "Ball"),
    AM("AM", "Ampoule, non-protected"),
    AP("AP", "Ampoule, protected"),
    AT("AT", "Atomizer"),
    AV("AV", "Capsule"),

    /**
     * A band use to retain multiple articles together.
     */
    B4("B4", "Belt"),
    BA("BA", "Barrel"),
    BB("BB", "Bobbin"),
    BC("BC", "Bottlecrate / bottlerack"),
    BD("BD", "Board"),
    BE("BE", "Bundle"),
    BF("BF", "Balloon, non-protected"),
    BG("BG", "Bag"),
    BH("BH", "Bunch"),
    BI("BI", "Bin"),
    BJ("BJ", "Bucket"),
    BK("BK", "Basket"),
    BL("BL", "Bale, compressed"),
    BM("BM", "Basin"),
    BN("BN", "Bale, non-compressed"),
    BO("BO", "Bottle, non-protected, cylindrical"),
    BP("BP", "Balloon, protected"),
    BQ("BQ", "Bottle, protected cylindrical"),
    BR("BR", "Bar"),
    BS("BS", "Bottle, non-protected, bulbous"),
    BT("BT", "Bolt"),
    BU("BU", "Butt"),
    BV("BV", "Bottle, protected bulbous"),
    BW("BW", "Box, for liquids"),
    BX("BX", "Box"),
    BY("BY", "Board, in bundle/bunch/truss"),
    BZ("BZ", "Bars, in bundle/bunch/truss"),
    CA("CA", "Can, rectangular"),
    CB("CB", "Crate, beer"),
    CC("CC", "Churn"),
    CD("CD", "Can, with handle and spout"),
    CE("CE", "Creel"),
    CF("CF", "Coffer"),
    CG("CG", "Cage"),
    CH("CH", "Chest"),
    CI("CI", "Canister"),
    CJ("CJ", "Coffin"),
    CK("CK", "Cask"),
    CL("CL", "Coil"),
    CM("CM", "Card"),
    CN("CN", "Container, not otherwise specified as transport equipment"),
    CO("CO", "Carboy, non-protected"),
    CP("CP", "Carboy, protected"),
    CQ("CQ", "Cartridge"),
    CR("CR", "Crate"),
    CS("CS", "Case"),
    CT("CT", "Carton"),
    CU("CU", "Cup"),
    CV("CV", "Cover"),
    CW("CW", "Cage, roll"),
    CX("CX", "Can, cylindrical"),
    CY("CY", "Cylinder"),
    CZ("CZ", "Canvas"),
    DA("DA", "Crate, multiple layer, plastic"),
    DB("DB", "Crate, multiple layer, wooden"),
    DC("DC", "Crate, multiple layer, cardboard"),
    DG("DG", "Cage, Commonwealth Handling Equipment Pool  (CHEP)"),
    DH("DH", "Box, Commonwealth Handling Equipment Pool (CHEP), Eurobox"),
    DI("DI", "Drum, iron"),
    DJ("DJ", "Demijohn, non-protected"),
    DK("DK", "Crate, bulk, cardboard"),
    DL("DL", "Crate, bulk, plastic"),
    DM("DM", "Crate, bulk, wooden"),
    DN("DN", "Dispenser"),
    DP("DP", "Demijohn, protected"),
    DR("DR", "Drum"),
    DS("DS", "Tray, one layer no cover, plastic"),
    DT("DT", "Tray, one layer no cover, wooden"),
    DU("DU", "Tray, one layer no cover, polystyrene"),
    DV("DV", "Tray, one layer no cover, cardboard"),
    DW("DW", "Tray, two layers no cover, plastic tray"),
    DX("DX", "Tray, two layers no cover, wooden"),
    DY("DY", "Tray, two layers no cover, cardboard"),
    EC("EC", "Bag, plastic"),
    ED("ED", "Case, with pallet base"),
    EE("EE", "Case, with pallet base, wooden"),
    EF("EF", "Case, with pallet base, cardboard"),
    EG("EG", "Case, with pallet base, plastic"),
    EH("EH", "Case, with pallet base, metal"),
    EI("EI", "Case, isothermic"),
    EN("EN", "Envelope"),

    /**
     * A flexible containment bag made of plastic, typically for the transportation bulk non-hazardous
     * cargoes using standard size shipping containers.
     */
    FB("FB", "Flexibag"),
    FC("FC", "Crate, fruit"),
    FD("FD", "Crate, framed"),

    /**
     * A flexible containment tank made of plastic, typically for the transportation bulk non-hazardous
     * cargoes using standard size shipping containers.
     */
    FE("FE", "Flexitank"),
    FI("FI", "Firkin"),
    FL("FL", "Flask"),
    FO("FO", "Footlocker"),
    FP("FP", "Filmpack"),
    FR("FR", "Frame"),
    FT("FT", "Foodtainer"),

    /**
     * Wheeled flat bedded device on which trays or other regular shaped items are packed for
     * transportation purposes.
     */
    FW("FW", "Cart, flatbed"),
    FX("FX", "Bag, flexible container"),
    GB("GB", "Bottle, gas"),
    GI("GI", "Girder"),

    /**
     * A container with a capacity of one gallon.
     */
    GL("GL", "Container, gallon"),
    GR("GR", "Receptacle, glass"),

    /**
     * Tray containing flat items stacked on top of one another.
     */
    GU("GU", "Tray, containing horizontally stacked flat items"),

    /**
     * A sack made of gunny or burlap, used for transporting coarse commodities, such as grains,
     * potatoes, and other agricultural products.
     */
    GY("GY", "Bag, gunny"),
    GZ("GZ", "Girders, in bundle/bunch/truss"),
    HA("HA", "Basket, with handle, plastic"),
    HB("HB", "Basket, with handle, wooden"),
    HC("HC", "Basket, with handle, cardboard"),
    HG("HG", "Hogshead"),

    /**
     * A purpose shaped device with a hook at the top for hanging items from a rail.
     */
    HN("HN", "Hanger"),
    HR("HR", "Hamper"),
    IA("IA", "Package, display, wooden"),
    IB("IB", "Package, display, cardboard"),
    IC("IC", "Package, display, plastic"),
    ID("ID", "Package, display, metal"),
    IE("IE", "Package, show"),
    IF("IF", "Package, flow"),
    IG("IG", "Package, paper wrapped"),
    IH("IH", "Drum, plastic"),
    IK("IK", "Package, cardboard, with bottle grip-holes"),

    /**
     * Lidded stackable rigid tray compliant with CEN TS 14482:2002.
     */
    IL("IL", "Tray, rigid, lidded stackable (CEN TS 14482:2002)"),
    IN("IN", "Ingot"),
    IZ("IZ", "Ingots, in bundle/bunch/truss"),

    /**
     * A flexible containment bag, widely used for storage, transportation and handling of
     * powder, flake or granular materials. Typically constructed from woven polypropylene
     * (PP) fabric in the form of cubic bags.
     */
    JB("JB", "Bag, jumbo"),
    JC("JC", "Jerrican, rectangular"),
    JG("JG", "Jug"),
    JR("JR", "Jar"),
    JT("JT", "Jutebag"),
    JY("JY", "Jerrican, cylindrical"),
    KG("KG", "Keg"),

    /**
     * A set of articles or implements used for a specific purpose.
     */
    KI("KI", "Kit"),

    /**
     * A collection of bags, cases and/or containers which hold personal belongings for a journey.
     */
    LE("LE", "Luggage"),
    LG("LG", "Log"),
    LT("LT", "Lot"),

    /**
     * A wooden box for the transportation and storage of fruit or vegetables.
     */
    LU("LU", "Lug"),
    LV("LV", "Liftvan"),
    LZ("LZ", "Logs, in bundle/bunch/truss"),

    /**
     * Containment box made of metal for retaining substances or articles.
     */
    MA("MA", "Crate, metal"),
    MB("MB", "Bag, multiply"),
    MC("MC", "Crate, milk"),

    /**
     * A type of containment box made of metal for retaining substances or articles, not otherwise
     * specified as transport equipment.
     */
    ME("ME", "Container, metal"),
    MR("MR", "Receptacle, metal"),
    MS("MS", "Sack, multi-wall"),
    MT("MT", "Mat"),
    MW("MW", "Receptacle, plastic wrapped"),
    MX("MX", "Matchbox"),
    NA("NA", "Not available"),
    NE("NE", "Unpacked or unpackaged"),
    NF("NF", "Unpacked or unpackaged, single unit"),
    NG("NG", "Unpacked or unpackaged, multiple units"),
    NS("NS", "Nest"),
    NT("NT", "Net"),
    NU("NU", "Net, tube, plastic"),
    NV("NV", "Net, tube, textile"),

    /**
     * A two sided cage mounted on wheels with fixing strap. Dimensions: 900 x 770 x 1513 cm
     * (length x width x height).
     */
    O1("O1", "Two sided cage on wheels with fixing strap"),

    /**
     * A low cart for the transportation and storage of groceries, milk, etc.
     */
    O2("O2", "Trolley"),

    /**
     * Oneway pallet with dimensions 80 X 60 cm.
     */
    O3("O3", "Oneway pallet ISO 0 - 1/2 EURO Pallet"),

    /**
     * Oneway pallet with dimensions 80 X 120 cm.
     */
    O4("O4", "Oneway pallet ISO 1 - 1/1 EURO Pallet"),

    /**
     * Oneway pallet with dimensions 100 X 120 cm.
     */
    O5("O5", "Oneway pallet ISO 2 - 2/1 EURO Pallet"),

    /**
     * Pallet with non-standard dimensions.
     */
    O6("O6", "Pallet with exceptional dimensions"),

    /**
     * Wooden pallet with dimensions 40 cm x 80 cm.
     */
    O7("O7", "Wooden pallet  40 cm x 80 cm"),

    /**
     * SRS (Svenska Retursystem) standard synthetic pallet of dimensions 60 cm x 80 cm.
     */
    O8("O8", "Plastic pallet SRS 60 cm x 80 cm"),

    /**
     * SRS (Svenska Retursystem) standard synthetic pallet of dimensions 80 cm x 120 cm.
     */
    O9("O9", "Plastic pallet SRS 80 cm x 120 cm"),

    /**
     * CHEP standard pallet of dimensions 40 centimeters x 60 centimeters.
     */
    OA("OA", "Pallet, CHEP 40 cm x 60 cm"),

    /**
     * CHEP standard pallet of dimensions 80 centimeters x 120 centimeters.
     */
    OB("OB", "Pallet, CHEP 80 cm x 120 cm"),

    /**
     * CHEP standard pallet of dimensions 100 centimeters x 120 centimeters.
     */
    OC("OC", "Pallet, CHEP 100 cm x 120 cm"),

    /**
     * Australian standard pallet of dimensions 116.5 centimeters x 116.5 centimeters.
     */
    OD("OD", "Pallet, AS 4068-1993"),

    /**
     * ISO standard pallet of dimensions 110 centimeters x 110 centimeters, prevalent in Asia
     * - Pacific region.
     */
    OE("OE", "Pallet, ISO T11"),

    /**
     * A pallet equivalent shipping platform of unknown dimensions or unknown weight.
     */
    OF("OF", "Platform, unspecified weight or dimension"),

    /**
     * Standard pallet with dimensions 80 X 60 cm.
     */
    OG("OG", "Pallet ISO 0 - 1/2 EURO Pallet"),

    /**
     * Standard pallet with dimensions 80 X 120 cm.
     */
    OH("OH", "Pallet ISO 1 - 1/1 EURO Pallet"),

    /**
     * Standard pallet with dimensions 100 X 120 cm.
     */
    OI("OI", "Pallet ISO 2 - 2/1 EURO Pallet"),

    /**
     * Standard pallet with dimensions 60 X 40 cm.
     */
    OJ("OJ", "1/4 EURO Pallet"),

    /**
     * A solid piece of a hard substance, such as granite, having one or more flat sides.
     */
    OK("OK", "Block"),

    /**
     * Standard pallet with dimensions 40 X 30 cm.
     */
    OL("OL", "1/8 EURO Pallet"),

    /**
     * A standard pallet with standard dimensions 80 x 120cm made of a synthetic material for
     * hygienic reasons.
     */
    OM("OM", "Synthetic pallet ISO 1"),

    /**
     * A standard pallet with standard dimensions 100 x 120cm made of a synthetic material
     * for hygienic reasons.
     */
    ON("ON", "Synthetic pallet ISO 2"),

    /**
     * Pallet provided by the wholesaler.
     */
    OP("OP", "Wholesaler pallet"),

    /**
     * Pallet with dimensions 80 X 100 cm.
     */
    OQ("OQ", "Pallet 80 X 100 cm"),

    /**
     * Pallet with dimensions 60 X 100 cm.
     */
    OR("OR", "Pallet 60 X 100 cm"),

    /**
     * Pallet need not be returned to the point of expedition.
     */
    OS("OS", "Oneway pallet"),

    /**
     * A standard cardboard container of large dimensions for storing for example vegetables,
     * granules of plastics or other dry products.
     */
    OT("OT", "Octabin"),

    /**
     * A type of containment box that serves as the outer shipping container, not otherwise
     * specified as transport equipment.
     */
    OU("OU", "Container, outer"),

    /**
     * Pallet must be returned to the point of expedition.
     */
    OV("OV", "Returnable pallet"),

    /**
     * A non-rigid container made of fabric, paper, plastic, etc, with an opening at the top
     * which can be closed and which is suitable for use on pallets.
     */
    OW("OW", "Large bag, pallet sized"),

    /**
     * A wheeled pallet with raised rim for the storing and transporting of loads. Dimensions:
     * 81 x 67 x 135 cm (length x width x height).
     */
    OX("OX", "A wheeled pallet with raised rim (81 x 67 x 135)"),

    /**
     * A wheeled pallet with raised rim for the storing and transporting of loads. Dimensions:
     * 81 x 72 x 135 cm (length x width x height).
     */
    OY("OY", "A wheeled pallet with raised rim (81 x 72 x 135)"),

    /**
     * A wheeled pallet with raised rim for the storing and transporting of loads. Dimensions:
     * 81 x 60 x 16 cm (length x width x height).
     */
    OZ("OZ", "A wheeled pallet with raised rim (81 x 60 x 16)"),

    /**
     * Commonwealth Handling Equipment Pool (CHEP) standard pallet of dimensions 60 centimeters
     * x 80 centimeters.
     */
    P1("P1", "CHEP pallet 60 cm x 80 cm"),

    /**
     * A shallow, wide, open container, usually of metal.
     */
    P2("P2", "Pan"),

    /**
     * LPR (La Pallet Rouge) standard pallet of dimensions 60 cm x 80 cm.
     */
    P3("P3", "LPR pallet 60 cm x 80 cm"),

    /**
     * LPR (La Pallet Rouge) standard pallet of dimensions 80 cm x 120 cm.
     */
    P4("P4", "LPR pallet 80 cm x 120 cm"),
    PA("PA", "Packet"),
    PB("PB", "Pallet, box"),
    PC("PC", "Parcel"),
    PD("PD", "Pallet, modular, collars 80cms * 100cms"),
    PE("PE", "Pallet, modular, collars 80cms * 120cms"),
    PF("PF", "Pen"),
    PG("PG", "Plate"),
    PH("PH", "Pitcher"),
    PI("PI", "Pipe"),
    PJ("PJ", "Punnet"),
    PK("PK", "Package"),
    PL("PL", "Pail"),
    PN("PN", "Plank"),
    PO("PO", "Pouch"),

    /**
     * A loose or unpacked article.
     */
    PP("PP", "Piece"),
    PR("PR", "Receptacle, plastic"),
    PT("PT", "Pot"),
    PU("PU", "Tray pack"),
    PV("PV", "Pipes, in bundle/bunch/truss"),
    PX("PX", "Pallet"),
    PY("PY", "Plates, in bundle/bunch/truss"),
    PZ("PZ", "Planks, in bundle/bunch/truss"),
    QA("QA", "Drum, steel, non-removable head"),
    QB("QB", "Drum, steel, removable head"),
    QC("QC", "Drum, aluminium, non-removable head"),
    QD("QD", "Drum, aluminium, removable head"),
    QF("QF", "Drum, plastic, non-removable head"),
    QG("QG", "Drum, plastic, removable head"),
    QH("QH", "Barrel, wooden, bung type"),
    QJ("QJ", "Barrel, wooden, removable head"),
    QK("QK", "Jerrican, steel, non-removable head"),
    QL("QL", "Jerrican, steel, removable head"),
    QM("QM", "Jerrican, plastic, non-removable head"),
    QN("QN", "Jerrican, plastic, removable head"),
    QP("QP", "Box, wooden, natural wood, ordinary"),
    QQ("QQ", "Box, wooden, natural wood, with sift proof walls"),
    QR("QR", "Box, plastic, expanded"),
    QS("QS", "Box, plastic, solid"),
    RD("RD", "Rod"),
    RG("RG", "Ring"),
    RJ("RJ", "Rack, clothing hanger"),
    RK("RK", "Rack"),
    RL("RL", "Reel"),
    RO("RO", "Roll"),
    RT("RT", "Rednet"),
    RZ("RZ", "Rods, in bundle/bunch/truss"),
    SA("SA", "Sack"),
    SB("SB", "Slab"),
    SC("SC", "Crate, shallow"),
    SD("SD", "Spindle"),
    SE("SE", "Sea-chest"),
    SH("SH", "Sachet"),
    SI("SI", "Skid"),
    SK("SK", "Case, skeleton"),
    SL("SL", "Slipsheet"),
    SM("SM", "Sheetmetal"),
    SO("SO", "Spool"),
    SP("SP", "Sheet, plastic wrapping"),
    SS("SS", "Case, steel"),
    ST("ST", "Sheet"),
    SU("SU", "Suitcase"),
    SV("SV", "Envelope, steel"),
    SW("SW", "Shrinkwrapped"),
    SY("SY", "Sleeve"),
    SZ("SZ", "Sheets, in bundle/bunch/truss"),

    /**
     * A loose or unpacked article in the form of a bar, block or piece.
     */
    T1("T1", "Tablet"),
    TB("TB", "Tub"),
    TC("TC", "Tea-chest"),
    TD("TD", "Tube, collapsible"),

    /**
     * A ring made of rubber and/or metal surrounding a wheel.
     */
    TE("TE", "Tyre"),

    /**
     * A specially constructed container for transporting liquids and gases in bulk.
     */
    TG("TG", "Tank container, generic"),
    TI("TI", "Tierce TI"),
    TK("TK", "Tank, rectangular"),
    TL("TL", "Tub, with lid"),
    TN("TN", "Tin"),
    TO("TO", "Tun"),
    TR("TR", "Trunk"),
    TS("TS", "Truss"),

    /**
     * A capacious bag or basket.
     */
    TT("TT", "Bag, tote"),
    TU("TU", "Tube"),
    TV("TV", "Tube, with nozzle"),

    /**
     * A lightweight pallet made from heavy duty corrugated board.
     */
    TW("TW", "Pallet, triwall"),
    TY("TY", "Tank, cylindrical"),
    TZ("TZ", "Tubes, in bundle/bunch/truss"),
    UC("UC", "Uncaged"),

    /**
     * A type of package composed of a single item or object, not otherwise specified as a
     * unit of transport equipment.
     */
    UN("UN", "Unit"),
    VA("VA", "Vat"),
    VG("VG", "Bulk, gas (at 1031 mbar and 15 degree C)"),
    VI("VI", "Vial"),
    VK("VK", "Vanpack"),
    VL("VL", "Bulk, liquid"),

    /**
     * A self-propelled means of conveyance.
     */
    VN("VN", "Vehicle"),
    VO("VO", "Bulk, solid, large particles (\"nodules\")"),
    VP("VP", "Vacuum-packed"),
    VQ("VQ", "Bulk, liquefied gas (at abnormal temperature/pressure)"),
    VR("VR", "Bulk, solid, granular particles (\"grains\")"),

    /**
     * Loose or unpacked scrap metal transported in bulk form.
     */
    VS("VS", "Bulk, scrap metal"),
    VY("VY", "Bulk, solid, fine particles (\"powders\")"),
    WA("WA", "Intermediate bulk container"),
    WB("WB", "Wickerbottle"),
    WC("WC", "Intermediate bulk container, steel"),
    WD("WD", "Intermediate bulk container, aluminium"),
    WF("WF", "Intermediate bulk container, metal"),
    WG("WG", "Intermediate bulk container, steel, pressurised > 10 kpa"),
    WH("WH", "Intermediate bulk container, aluminium, pressurised > 10 kpa"),
    WJ("WJ", "Intermediate bulk container, metal, pressure 10 kpa"),
    WK("WK", "Intermediate bulk container, steel, liquid"),
    WL("WL", "Intermediate bulk container, aluminium, liquid"),
    WM("WM", "Intermediate bulk container, metal, liquid"),
    WN("WN", "Intermediate bulk container, woven plastic, without coat/liner"),
    WP("WP", "Intermediate bulk container, woven plastic, coated"),
    WQ("WQ", "Intermediate bulk container, woven plastic, with liner"),
    WR("WR", "Intermediate bulk container, woven plastic, coated and liner"),
    WS("WS", "Intermediate bulk container, plastic film"),
    WT("WT", "Intermediate bulk container, textile with out coat/liner"),
    WU("WU", "Intermediate bulk container, natural wood, with inner liner"),
    WV("WV", "Intermediate bulk container, textile, coated"),
    WW("WW", "Intermediate bulk container, textile, with liner"),
    WX("WX", "Intermediate bulk container, textile, coated and liner"),
    WY("WY", "Intermediate bulk container, plywood, with inner liner"),
    WZ("WZ", "Intermediate bulk container, reconstituted wood, with inner liner"),
    XA("XA", "Bag, woven plastic, without inner coat/liner"),
    XB("XB", "Bag, woven plastic, sift proof"),
    XC("XC", "Bag, woven plastic, water resistant"),
    XD("XD", "Bag, plastics film"),
    XF("XF", "Bag, textile, without inner coat/liner"),
    XG("XG", "Bag, textile, sift proof"),
    XH("XH", "Bag, textile, water resistant"),
    XJ("XJ", "Bag, paper, multi-wall"),
    XK("XK", "Bag, paper, multi-wall, water resistant"),
    YA("YA", "Composite packaging, plastic receptacle in steel drum"),
    YB("YB", "Composite packaging, plastic receptacle in steel crate box"),
    YC("YC", "Composite packaging, plastic receptacle in aluminium drum"),
    YD("YD", "Composite packaging, plastic receptacle in aluminium crate"),
    YF("YF", "Composite packaging, plastic receptacle in wooden box"),
    YG("YG", "Composite packaging, plastic receptacle in plywood drum"),
    YH("YH", "Composite packaging, plastic receptacle in plywood box"),
    YJ("YJ", "Composite packaging, plastic receptacle in fibre drum"),
    YK("YK", "Composite packaging, plastic receptacle in fibreboard box"),
    YL("YL", "Composite packaging, plastic receptacle in plastic drum"),
    YM("YM", "Composite packaging, plastic receptacle in solid plastic box"),
    YN("YN", "Composite packaging, glass receptacle in steel drum"),
    YP("YP", "Composite packaging, glass receptacle in steel crate box"),
    YQ("YQ", "Composite packaging, glass receptacle in aluminium drum"),
    YR("YR", "Composite packaging, glass receptacle in aluminium crate"),
    YS("YS", "Composite packaging, glass receptacle in wooden box"),
    YT("YT", "Composite packaging, glass receptacle in plywood drum"),
    YV("YV", "Composite packaging, glass receptacle in wickerwork hamper"),
    YW("YW", "Composite packaging, glass receptacle in fibre drum"),
    YX("YX", "Composite packaging, glass receptacle in fibreboard box"),
    YY("YY", "Composite packaging, glass receptacle in expandable plastic pack"),
    YZ("YZ", "Composite packaging, glass receptacle in solid plastic pack"),
    ZA("ZA", "Intermediate bulk container, paper, multi-wall"),
    ZB("ZB", "Bag, large"),
    ZC("ZC", "Intermediate bulk container, paper, multi-wall, water resistant"),
    ZD("ZD", "Intermediate bulk container, rigid plastic, with structural equipment, solids"),
    ZF("ZF", "Intermediate bulk container, rigid plastic, freestanding, solids"),
    ZG("ZG", "Intermediate bulk container, rigid plastic, with structural equipment, pressurised"),
    ZH("ZH", "Intermediate bulk container, rigid plastic, freestanding, pressurised"),
    ZJ("ZJ", "Intermediate bulk container, rigid plastic, with structural equipment, liquids"),
    ZK("ZK", "Intermediate bulk container, rigid plastic, freestanding, liquids"),
    ZL("ZL", "Intermediate bulk container, composite, rigid plastic, solids"),
    ZM("ZM", "Intermediate bulk container, composite, flexible plastic, solids"),
    ZN("ZN", "Intermediate bulk container, composite, rigid plastic, pressurised"),
    ZP("ZP", "Intermediate bulk container, composite, flexible plastic, pressurised"),
    ZQ("ZQ", "Intermediate bulk container, composite, rigid plastic, liquids"),
    ZR("ZR", "Intermediate bulk container, composite, flexible plastic, liquids"),
    ZS("ZS", "Intermediate bulk container, composite"),
    ZT("ZT", "Intermediate bulk container, fibreboard"),
    ZU("ZU", "Intermediate bulk container, flexible"),
    ZV("ZV", "Intermediate bulk container, metal, other than steel"),
    ZW("ZW", "Intermediate bulk container, natural wood"),
    ZX("ZX", "Intermediate bulk container, plywood"),
    ZY("ZY", "Intermediate bulk container, reconstituted wood"),
    ZZ("ZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    PackageTypeCodeType(String code, String name) {
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
