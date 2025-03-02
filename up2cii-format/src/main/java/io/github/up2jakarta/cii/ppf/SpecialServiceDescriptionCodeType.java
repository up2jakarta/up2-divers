package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.ppf.adapters.SpecialServiceDescriptionCodeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 7161 : Special service description code.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred7161.htm}
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@Documented(value = "Special Service Description Code", agency = Agency.UN_ECE, version = "2.3")
@Schema(agency = "UN/CEFACT", version = "3.4", date = "2008-08-23")
@XmlJavaTypeAdapter(SpecialServiceDescriptionCodeAdapter.class)
public enum SpecialServiceDescriptionCodeType implements ChargeReasonCodeType<SpecialServiceDescriptionCodeType> {

    /**
     * The service of providing advertising.
     */
    AA("AA", "Advertising"),

    /**
     * The service of providing telecommunication activities and/or faclities.
     */
    AAA("AAA", "Telecommunication"),

    /**
     * The service of making technical modifications to a product.
     */
    AAC("AAC", "Technical modification"),

    /**
     * The service of producing to order.
     */
    AAD("AAD", "Job-order production"),

    /**
     * The service of providing money for outlays on behalf of a trading partner.
     */
    AAE("AAE", "Outlays"),

    /**
     * The service of providing services outside the premises of the provider.
     */
    AAF("AAF", "Off-premises"),

    /**
     * The service of providing additional processing.
     */
    AAH("AAH", "Additional processing"),

    /**
     * The service of certifying validity.
     */
    AAI("AAI", "Attesting"),

    /**
     * The service of accepting goods or services.
     */
    AAS("AAS", "Acceptance"),

    /**
     * The service to provide a rush delivery.
     */
    AAT("AAT", "Rush delivery"),

    /**
     * The service of providing special construction.
     */
    AAV("AAV", "Special construction"),

    /**
     * The service of providing airport facilities.
     */
    AAY("AAY", "Airport facilities"),

    /**
     * The service allowing a party to use another party's facilities.
     */
    AAZ("AAZ", "Concession"),

    /**
     * The service provided to hold a compulsory inventory.
     */
    ABA("ABA", "Compulsory storage"),

    /**
     * Remove or off-load fuel from vehicle, vessel or craft.
     */
    ABB("ABB", "Fuel removal"),

    /**
     * Service of delivering goods to an aircraft from local storage.
     */
    ABC("ABC", "Into plane"),

    /**
     * The service of providing labour beyond the established limit of working hours.
     */
    ABD("ABD", "Overtime"),

    /**
     * The service of providing specific tooling.
     */
    ABF("ABF", "Tooling"),

    /**
     * Miscellaneous services.
     */
    ABK("ABK", "Miscellaneous"),

    /**
     * The service of providing additional packaging.
     */
    ABL("ABL", "Additional packaging"),

    /**
     * The service of providing additional padding materials required to secure and protect
     * a cargo within a shipping container.
     */
    ABN("ABN", "Dunnage"),

    /**
     * The service of packing items into a container.
     */
    ABR("ABR", "Containerisation"),

    /**
     * The service of packing items into a carton.
     */
    ABS("ABS", "Carton packing"),

    /**
     * The service of hessian wrapping.
     */
    ABT("ABT", "Hessian wrapped"),

    /**
     * The service of packing in polyethylene wrapping.
     */
    ABU("ABU", "Polyethylene wrap packing"),

    /**
     * Miscellaneous treatment service.
     */
    ACF("ACF", "Miscellaneous treatment"),

    /**
     * The service of providing enamelling treatment.
     */
    ACG("ACG", "Enamelling treatment"),

    /**
     * The service of treating with heat.
     */
    ACH("ACH", "Heat treatment"),

    /**
     * The service of providing plating treatment.
     */
    ACI("ACI", "Plating treatment"),

    /**
     * The service of painting.
     */
    ACJ("ACJ", "Painting"),

    /**
     * The service of polishing.
     */
    ACK("ACK", "Polishing"),

    /**
     * The service of priming.
     */
    ACL("ACL", "Priming"),

    /**
     * The service of preservation treatment.
     */
    ACM("ACM", "Preservation treatment"),

    /**
     * Fitting service.
     */
    ACS("ACS", "Fitting"),

    /**
     * The service of consolidating multiple consignments into one shipment.
     */
    ADC("ADC", "Consolidation"),

    /**
     * The service of providing a bill of lading document.
     */
    ADE("ADE", "Bill of lading"),

    /**
     * The service of surrounding a product with an air bag.
     */
    ADJ("ADJ", "Airbag"),

    /**
     * The service of transferring.
     */
    ADK("ADK", "Transfer"),

    /**
     * The service of securing a stack of products on a slipsheet.
     */
    ADL("ADL", "Slipsheet"),

    /**
     * Binding service.
     */
    ADM("ADM", "Binding"),

    /**
     * The service of repairing or replacing a broken returnable package.
     */
    ADN("ADN", "Repair or replacement of broken returnable package"),

    /**
     * A code indicating efficient logistics services.
     */
    ADO("ADO", "Efficient logistics"),

    /**
     * A code indicating that merchandising services are in operation.
     */
    ADP("ADP", "Merchandising"),

    /**
     * A code indicating that product mixing services are in operation.
     */
    ADQ("ADQ", "Product mix"),

    /**
     * A code indicating that other non-specific services are in operation.
     */
    ADR("ADR", "Other services"),

    /**
     * The service of picking up or collection of goods.
     */
    ADT("ADT", "Pick-up"),

    /**
     * The special services provided due to chronic illness.
     */
    ADW("ADW", "Chronic illness"),

    /**
     * A service provided by a buyer when introducing a new product from a suppliers range
     * to the range traded by the buyer.
     */
    ADY("ADY", "New product introduction"),

    /**
     * Direct delivery service.
     */
    ADZ("ADZ", "Direct delivery"),

    /**
     * The service of diverting deliverables.
     */
    AEA("AEA", "Diversion"),

    /**
     * The service is a disconnection.
     */
    AEB("AEB", "Disconnect"),

    /**
     * Distribution service.
     */
    AEC("AEC", "Distribution"),

    /**
     * A service for handling hazardous cargo.
     */
    AED("AED", "Handling of hazardous cargo"),

    /**
     * The service of renting and/or leasing.
     */
    AEF("AEF", "Rents and leases"),

    /**
     * Delivery to a different location than previously contracted.
     */
    AEH("AEH", "Location differential"),

    /**
     * Fuel being put into the aircraft.
     */
    AEI("AEI", "Aircraft refueling"),

    /**
     * Fuel being shipped into a storage system.
     */
    AEJ("AEJ", "Fuel shipped into storage"),

    /**
     * The provision of a cash on delivery (COD) service.
     */
    AEK("AEK", "Cash on delivery"),

    /**
     * A service related to the processing of small orders.
     */
    AEL("AEL", "Small order processing service"),

    /**
     * The provision of clerical or administrative services.
     */
    AEM("AEM", "Clerical or administrative services"),

    /**
     * The service of providing a guarantee.
     */
    AEN("AEN", "Guarantee"),

    /**
     * The service of collection and recycling products.
     */
    AEO("AEO", "Collection and recycling"),

    /**
     * The service of collecting copyright fees.
     */
    AEP("AEP", "Copyright fee collection"),

    /**
     * The service of providing veterinary inspection.
     */
    AES("AES", "Veterinary inspection service"),

    /**
     * Special service when the subject is a pensioner.
     */
    AET("AET", "Pensioner service"),

    /**
     * Special service when the subject holds a medicine free pass.
     */
    AEU("AEU", "Medicine free pass holder"),

    /**
     * The provision of an environmental protection service.
     */
    AEV("AEV", "Environmental protection service"),

    /**
     * The provision of an environmental clean-up service.
     */
    AEW("AEW", "Environmental clean-up service"),

    /**
     * Service of processing a national cheque outside the ordering customer's bank trading
     * area.
     */
    AEX("AEX", "National cheque processing service outside account area"),

    /**
     * Service of processing a national payment to a beneficiary holding an account outside
     * the trading area of the ordering customer's bank.
     */
    AEY("AEY", "National payment service outside account area"),

    /**
     * Service of processing a national payment to a beneficiary holding an account within
     * the trading area of the ordering customer's bank.
     */
    AEZ("AEZ", "National payment service within account area"),

    /**
     * The service of making adjustments.
     */
    AJ("AJ", "Adjustments"),

    /**
     * The service of authenticating.
     */
    AU("AU", "Authentication"),

    /**
     * The provision of cataloguing services.
     */
    CA("CA", "Cataloguing"),

    /**
     * Movement of goods by heavy duty cart or vehicle.
     */
    CAB("CAB", "Cartage"),

    /**
     * The service of certifying.
     */
    CAD("CAD", "Certification"),

    /**
     * The service of providing a certificate of conformance.
     */
    CAE("CAE", "Certificate of conformance"),

    /**
     * The service of providing a certificate of origin.
     */
    CAF("CAF", "Certificate of origin"),

    /**
     * The service of cutting.
     */
    CAI("CAI", "Cutting"),

    /**
     * The service provided by consulates.
     */
    CAJ("CAJ", "Consular service"),

    /**
     * The service of collecting goods by the customer.
     */
    CAK("CAK", "Customer collection"),

    /**
     * Provision of a payroll payment service.
     */
    CAL("CAL", "Payroll payment service"),

    /**
     * Provision of a cash transportation service.
     */
    CAM("CAM", "Cash transportation"),

    /**
     * Provision of a home banking service.
     */
    CAN("CAN", "Home banking service"),

    /**
     * Provision of a service as specified in a bilateral special agreement.
     */
    CAO("CAO", "Bilateral agreement service"),

    /**
     * Provision of an insurance brokerage service.
     */
    CAP("CAP", "Insurance brokerage service"),

    /**
     * Provision of a cheque generation service.
     */
    CAQ("CAQ", "Cheque generation"),

    /**
     * Service of assigning a preferential location for merchandising.
     */
    CAR("CAR", "Preferential merchandising location"),

    /**
     * The service of providing a crane.
     */
    CAS("CAS", "Crane"),

    /**
     * Providing a colour which is different from the default colour.
     */
    CAT("CAT", "Special colour service"),

    /**
     * The provision of sorting services.
     */
    CAU("CAU", "Sorting"),

    /**
     * The service of collecting and recycling batteries.
     */
    CAV("CAV", "Battery collection and recycling"),

    /**
     * The fee the consumer must pay the manufacturer to take back the product.
     */
    CAW("CAW", "Product take back fee"),

    /**
     * Informs the stockholder it is free to distribute the quality controlled passed goods.
     */
    CAX("CAX", "Quality control released"),

    /**
     * Instructs the stockholder to withhold distribution of the goods until the manufacturer
     * has completed a quality control assessment.
     */
    CAY("CAY", "Quality control held"),

    /**
     * Instructs the stockholder to withhold distribution of goods which have failed quality
     * control tests.
     */
    CAZ("CAZ", "Quality control embargo"),

    /**
     * Car loading service.
     */
    CD("CD", "Car loading"),

    /**
     * Cleaning service.
     */
    CG("CG", "Cleaning"),

    /**
     * The service of providing cigarette stamping.
     */
    CS("CS", "Cigarette stamping"),

    /**
     * The service of doing a count and recount.
     */
    CT("CT", "Count and recount"),

    /**
     * The service of providing layout/design.
     */
    DAB("DAB", "Layout/design"),

    /**
     * Allowance given when a specific part of a suppliers assortment is purchased by the
     * buyer.
     */
    DAC("DAC", "Assortment allowance"),

    /**
     * The service of unloading by the driver.
     */
    DAD("DAD", "Driver assigned unloading"),

    /**
     * A special allowance or charge applicable to a specific debtor.
     */
    DAF("DAF", "Debtor bound"),

    /**
     * An allowance offered by a party dealing a certain brand or brands of products.
     */
    DAG("DAG", "Dealer allowance"),

    /**
     * An allowance given by the manufacturer which should be transfered to the consumer.
     */
    DAH("DAH", "Allowance transferable to the consumer"),

    /**
     * An allowance or charge related to the growth of business over a pre-determined period
     * of time.
     */
    DAI("DAI", "Growth of business"),

    /**
     * An allowance related to the introduction of a new product to the range of products
     * traded by a retailer.
     */
    DAJ("DAJ", "Introduction allowance"),

    /**
     * A code indicating special conditions related to a multi- buy promotion.
     */
    DAK("DAK", "Multi-buy promotion"),

    /**
     * An allowance or charge related to the establishment and on-going maintenance of a partnership.
     */
    DAL("DAL", "Partnership"),

    /**
     * An allowance or change related to the handling of returns.
     */
    DAM("DAM", "Return handling"),

    /**
     * Charge levied because the minimum order quantity could not be fulfilled.
     */
    DAN("DAN", "Minimum order not fulfilled charge"),

    /**
     * Allowance for reaching or exceeding an agreed sales threshold at the point of sales.
     */
    DAO("DAO", "Point of sales threshold allowance"),

    /**
     * A special discount related to the purchase of products through a wholesaler.
     */
    DAP("DAP", "Wholesaling discount"),

    /**
     * Fee for the transfer of transferable documentary credits.
     */
    DAQ("DAQ", "Documentary credits transfer commission"),

    /**
     * The service of providing delivery.
     */
    DL("DL", "Delivery"),

    /**
     * The service of providing engraving.
     */
    EG("EG", "Engraving"),

    /**
     * The service of expediting.
     */
    EP("EP", "Expediting"),

    /**
     * The service of guaranteeing exchange rate.
     */
    ER("ER", "Exchange rate guarantee"),

    /**
     * The service of providing fabrication.
     */
    FAA("FAA", "Fabrication"),

    /**
     * The service of load balancing.
     */
    FAB("FAB", "Freight equalization"),

    /**
     * The service of providing freight's extraordinary handling.
     */
    FAC("FAC", "Freight extraordinary handling"),

    /**
     * The service of moving goods, by whatever means, from one place to another.
     */
    FC("FC", "Freight service"),

    /**
     * The service of providing filling/handling.
     */
    FH("FH", "Filling/handling"),

    /**
     * The service of providing financing.
     */
    FI("FI", "Financing"),

    /**
     * The service of grinding.
     */
    GAA("GAA", "Grinding"),

    /**
     * The service of providing a hose.
     */
    HAA("HAA", "Hose"),

    /**
     * Handling service.
     */
    HD("HD", "Handling"),

    /**
     * The service of hoisting and hauling.
     */
    HH("HH", "Hoisting and hauling"),

    /**
     * The service of installing.
     */
    IAA("IAA", "Installation"),

    /**
     * The service of installing and providing warranty.
     */
    IAB("IAB", "Installation and warranty"),

    /**
     * The service of providing delivery inside.
     */
    ID("ID", "Inside delivery"),

    /**
     * The service of inspection.
     */
    IF("IF", "Inspection"),

    /**
     * The service of providing installation and training.
     */
    IR("IR", "Installation and training"),

    /**
     * The service of providing an invoice.
     */
    IS("IS", "Invoicing"),

    /**
     * The service of preparing food in accordance with Jewish law.
     */
    KO("KO", "Koshering"),

    /**
     * The service of counting by the carrier.
     */
    L1("L1", "Carrier count"),

    /**
     * Labelling service.
     */
    LA("LA", "Labelling"),

    /**
     * The service to provide required labour.
     */
    LAA("LAA", "Labour"),

    /**
     * The service of repairing and returning.
     */
    LAB("LAB", "Repair and return"),

    /**
     * The service of legalising.
     */
    LF("LF", "Legalisation"),

    /**
     * The service of mounting.
     */
    MAE("MAE", "Mounting"),

    /**
     * The service of mailing an invoice.
     */
    MI("MI", "Mail invoice"),

    /**
     * The service of mailing an invoice to each location.
     */
    ML("ML", "Mail invoice to each location"),

    /**
     * The service of providing non-returnable containers.
     */
    NAA("NAA", "Non-returnable containers"),

    /**
     * The service of providing outside cable connectors.
     */
    OA("OA", "Outside cable connectors"),

    /**
     * The service of including the invoice with the shipment.
     */
    PA("PA", "Invoice with shipment"),

    /**
     * The service of phosphatizing the steel.
     */
    PAA("PAA", "Phosphatizing (steel treatment)"),

    /**
     * The service of packing.
     */
    PC("PC", "Packing"),

    /**
     * The service of palletizing.
     */
    PL("PL", "Palletizing"),

    /**
     * The service of repacking.
     */
    RAB("RAB", "Repacking"),

    /**
     * The service of repairing.
     */
    RAC("RAC", "Repair"),

    /**
     * The service of providing returnable containers.
     */
    RAD("RAD", "Returnable container"),

    /**
     * The service of restocking.
     */
    RAF("RAF", "Restocking"),

    /**
     * The service of re-delivering.
     */
    RE("RE", "Re-delivery"),

    /**
     * The service of refurbishing.
     */
    RF("RF", "Refurbishing"),

    /**
     * The service of providing rail wagons for hire.
     */
    RH("RH", "Rail wagon hire"),

    /**
     * The service of loading goods.
     */
    RV("RV", "Loading"),

    /**
     * The service of salvaging.
     */
    SA("SA", "Salvaging"),

    /**
     * The service of shipping and handling.
     */
    SAA("SAA", "Shipping and handling"),

    /**
     * The service of special packaging.
     */
    SAD("SAD", "Special packaging"),

    /**
     * The service of stamping.
     */
    SAE("SAE", "Stamping"),

    /**
     * The service of unloading by the consignee.
     */
    SAI("SAI", "Consignee unload"),

    /**
     * The service of shrink-wrapping.
     */
    SG("SG", "Shrink-wrap"),

    /**
     * The service of special handling.
     */
    SH("SH", "Special handling"),

    /**
     * The service of providing a special finish.
     */
    SM("SM", "Special finish"),

    /**
     * The service of setting-up.
     */
    SU("SU", "Set-up"),

    /**
     * The service of providing tanks for hire.
     */
    TAB("TAB", "Tank renting"),

    /**
     * The service of testing.
     */
    TAC("TAC", "Testing"),

    /**
     * The service of providing third party billing for transportation.
     */
    TT("TT", "Transportation - third party billing"),

    /**
     * The service of providing transportation by the vendor.
     */
    TV("TV", "Transportation by vendor"),

    /**
     * The service of delivering goods at the yard.
     */
    V1("V1", "Drop yard"),

    /**
     * The service of delivering goods at the dock.
     */
    V2("V2", "Drop dock"),

    /**
     * The service of storing and handling of goods in a warehouse.
     */
    WH("WH", "Warehousing"),

    /**
     * The service of combining all shipments for the same day.
     */
    XAA("XAA", "Combine all same day shipment"),

    /**
     * The service of providing split pick-up.
     */
    YY("YY", "Split pick-up"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    SpecialServiceDescriptionCodeType(String code, String name) {
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
