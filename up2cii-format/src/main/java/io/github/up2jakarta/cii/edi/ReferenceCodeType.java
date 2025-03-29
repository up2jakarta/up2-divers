package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.ReferenceCodeAdapter;
import io.github.up2jakarta.cii.ppf.PartySchemeIDType;
import io.github.up2jakarta.xml.codelist.Agency;
import io.github.up2jakarta.xml.codelist.Documented;
import io.github.up2jakarta.xml.codelist.Schema;
import io.github.up2jakarta.xml.codelist.SubList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 1153 (ReferenceTypeCode) : Reference code qualifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred1153.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("1153")
@Documented(value = "Reference Type Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.11", date = "2008-08-23")
@XmlJavaTypeAdapter(ReferenceCodeAdapter.class)
public enum ReferenceCodeType implements PartySchemeIDType<ReferenceCodeType> {

    /**
     * [1018] Reference number identifying the acknowledgement of an order.
     */
    AAA("AAA", "Order acknowledgement document identifier"),

    /**
     * [1088] Reference number to identify a proforma invoice.
     */
    AAB("AAB", "Proforma invoice document identifier"),

    /**
     * [1172] Reference number to identify a documentary credit.
     */
    AAC("AAC", "Documentary credit identifier"),

    /**
     * [1318] Reference number to identify an addendum to a contract.
     */
    AAD("AAD", "Contract document addendum identifier"),

    /**
     * Reference number assigned to a goods declaration.
     */
    AAE("AAE", "Goods declaration number"),

    /**
     * A reference number identifying a debit card.
     */
    AAF("AAF", "Debit card number"),

    /**
     * (1332) Reference number assigned by issuing party to an offer.
     */
    AAG("AAG", "Offer number"),

    /**
     * Reference number allocated by the bank to a batch of different underlying interbank
     * transactions.
     */
    AAH("AAH", "Bank's batch interbank transaction reference number"),

    /**
     * Reference number allocated by the bank to one specific interbank transaction.
     */
    AAI("AAI", "Bank's individual interbank transaction reference number"),

    /**
     * Reference number assigned by issuer to a delivery order.
     */
    AAJ("AAJ", "Delivery order number"),

    /**
     * [1035] Reference number assigned by issuing party to a despatch advice.
     */
    AAK("AAK", "Despatch advice number"),

    /**
     * Reference number identifying a specific product drawing.
     */
    AAL("AAL", "Drawing number"),

    /**
     * Reference number assigned to a waybill, see: 1001 = 700.
     */
    AAM("AAM", "Waybill number"),

    /**
     * Reference number assigned by buyer to a delivery schedule.
     */
    AAN("AAN", "Delivery schedule number"),

    /**
     * [1362] Reference number assigned by the consignee to identify a particular consignment.
     */
    AAO("AAO", "Consignment identifier, consignee assigned"),

    /**
     * [1310] Identifier of a shipment which is part of an order.
     */
    AAP("AAP", "Partial shipment identifier"),

    /**
     * [8260] To identify a piece if transport equipment e.g. container or unit load device.
     */
    AAQ("AAQ", "Transport equipment identifier"),

    /**
     * A reference number assigned by a municipality to identify a business.
     */
    AAR("AAR", "Municipality assigned business registry number"),

    /**
     * [1188] Reference number to identify a document evidencing a transport contract.
     */
    AAS("AAS", "Transport contract document identifier"),

    /**
     * Identifies the master label number of any package type.
     */
    AAT("AAT", "Master label number"),

    /**
     * [1128] Reference number to identify a Despatch Note.
     */
    AAU("AAU", "Despatch note document identifier"),

    /**
     * Reference number assigned to an enquiry.
     */
    AAV("AAV", "Enquiry number"),

    /**
     * A reference number identifying the docket.
     */
    AAW("AAW", "Docket number"),

    /**
     * A reference number identifying the civil action.
     */
    AAX("AAX", "Civil action number"),

    /**
     * Reference number assigned by the carriers agent to a transaction.
     */
    AAY("AAY", "Carrier's agent reference number"),

    /**
     * For maritime shipments, this code qualifies a Standard Alpha Carrier Code (SCAC) as
     * issued by the United Stated National Motor Traffic Association Inc.
     */
    AAZ("AAZ", "Standard Carrier Alpha Code (SCAC) number"),

    /**
     * Reference by an importing party to a previous decision made by a Customs administration
     * regarding the valuation of goods.
     */
    ABA("ABA", "Customs valuation decision number"),

    /**
     * Reference issued by a Customs administration authorizing a preferential rate of duty
     * if a product is used for a specified purpose, see: 1001 = 990.
     */
    ABB("ABB", "End use authorization number"),

    /**
     * Reference issued by a Customs administration pertaining to a past or current investigation
     * of goods "dumped" at a price lower than the exporter's domestic market price.
     */
    ABC("ABC", "Anti-dumping case number"),

    /**
     * (7357) Code number of the goods in accordance with the tariff nomenclature system of
     * classification in use where the Customs declaration is made.
     */
    ABD("ABD", "Customs tariff number"),

    /**
     * Unique reference number assigned to a document or a message by the declarant for identification
     * purposes.
     */
    ABE("ABE", "Declarant's reference number"),

    /**
     * A number identifying a repair estimate.
     */
    ABF("ABF", "Repair estimate number"),

    /**
     * Reference issued by Customs pertaining to a pending tariff classification decision requested
     * by an importer or agent.
     */
    ABG("ABG", "Customs decision request number"),

    /**
     * Reference assigned to a sub-house bill of lading.
     */
    ABH("ABH", "Sub-house bill of lading number"),

    /**
     * [1168] Reference number identifying a payment of a duty or tax e.g. under a transit
     * procedure.
     */
    ABI("ABI", "Tax payment identifier"),

    /**
     * Reference number allocated by a government authority to identify a quota.
     */
    ABJ("ABJ", "Quota number"),

    /**
     * Reference number to identify the guarantee or security provided for Customs transit
     * operation (CCC).
     */
    ABK("ABK", "Transit (onward carriage) guarantee (bond) number"),

    /**
     * Reference assigned to a Customs guarantee.
     */
    ABL("ABL", "Customs guarantee number"),

    /**
     * New part number which replaces the existing part number.
     */
    ABM("ABM", "Replacing part number"),

    /**
     * Identification number assigned to a seller's catalogue.
     */
    ABN("ABN", "Seller's catalogue number"),

    /**
     * A unique reference assigned by the originator.
     */
    ABO("ABO", "Originator's reference"),

    /**
     * Reference to the party whose posted bond or security is being declared in order to accept
     * responsibility for a goods declaration and the applicable duties and taxes.
     */
    ABP("ABP", "Declarant's Customs identity number"),

    /**
     * Reference number assigned by the importer to identify a particular shipment for his
     * own purposes.
     */
    ABQ("ABQ", "Importer reference number"),

    /**
     * Reference number of the clearance instructions given by the consignor through different
     * means.
     */
    ABR("ABR", "Export clearance instruction reference number"),

    /**
     * Reference number of the import clearance instructions given by the consignor/consignee
     * through different means.
     */
    ABS("ABS", "Import clearance instruction reference number"),

    /**
     * [1426] Reference number, assigned or accepted by Customs, to identify a goods declaration.
     */
    ABT("ABT", "Goods declaration document identifier, Customs"),

    /**
     * A number that identifies an article.
     */
    ABU("ABU", "Article number"),

    /**
     * To define routing within a plant.
     */
    ABV("ABV", "Intra-plant routing"),

    /**
     * A number that identifies the stock keeping unit.
     */
    ABW("ABW", "Stock keeping unit number"),

    /**
     * The reference used within a given TEI (Text Element Identifier) which is to be deleted.
     */
    ABX("ABX", "Text Element Identifier deletion reference"),

    /**
     * Reference assigned to guarantied capacity on one or more specific flights on specific
     * date(s) to third parties as agents and other airlines.
     */
    ABY("ABY", "Allotment identification (Air)"),

    /**
     * Number of the licence issued for a vehicle by an agency of government.
     */
    ABZ("ABZ", "Vehicle licence number"),

    /**
     * A number assigned to an air cargo list of goods to be transferred.
     */
    AC("AC", "Air cargo transfer manifest"),

    /**
     * Reference assigned to the cargo acceptance order.
     */
    ACA("ACA", "Cargo acceptance order reference number"),

    /**
     * A number that identifies a United States Government agency.
     */
    ACB("ACB", "US government agency number"),

    /**
     * Identifying marks on the outermost unit that is used to transport merchandise.
     */
    ACC("ACC", "Shipping unit identification"),

    /**
     * [1010] Reference number provided in addition to another given reference.
     */
    ACD("ACD", "Additional reference number"),

    /**
     * Reference number identifying a related document.
     */
    ACE("ACE", "Related document number"),

    /**
     * A reference number of an addressee.
     */
    ACF("ACF", "Addressee reference"),

    /**
     * Reference number assigned to an ATA carnet.
     */
    ACG("ACG", "ATA carnet number"),

    /**
     * Identifying marks on packing units.
     */
    ACH("ACH", "Packaging unit identification"),

    /**
     * Identifying marks on packing units contained within an outermost shipping unit.
     */
    ACI("ACI", "Outerpackaging unit identification"),

    /**
     * Number for a material specification given by customer.
     */
    ACJ("ACJ", "Customer material specification number"),

    /**
     * Cross reference issued by financial institution.
     */
    ACK("ACK", "Bank reference"),

    /**
     * A number that identifies the principal reference.
     */
    ACL("ACL", "Principal reference number"),

    /**
     * [1030] Reference number to identify a collection advice document.
     */
    ACN("ACN", "Collection advice document identifier"),

    /**
     * Number attributed to the iron charge for the production of steel products.
     */
    ACO("ACO", "Iron charge number"),

    /**
     * Number attributed to a hot roll coil.
     */
    ACP("ACP", "Hot roll number"),

    /**
     * Number attributed to a cold roll coil.
     */
    ACQ("ACQ", "Cold roll number"),

    /**
     * (8260) Registered identification initials and numbers of railway wagon. Synonym: Rail
     * car number.
     */
    ACR("ACR", "Railway wagon number"),

    /**
     * A number that identifies the unique claims reference of the sender.
     */
    ACT("ACT", "Unique claims reference number of the sender"),

    /**
     * To reference to the unique number that is assigned to each major loss hitting the reinsurance
     * industry.
     */
    ACU("ACU", "Loss/event number"),

    /**
     * Reference number assigned by the ordering party of the estimate order.
     */
    ACV("ACV", "Estimate order reference number"),

    /**
     * Reference number assigned to the message which was previously issued (e.g. in the case
     * of a cancellation, the primary reference of the message to be cancelled will be quoted
     * in this element).
     */
    ACW("ACW", "Reference number to previous message"),

    /**
     * Reference number for banker's acceptance issued by the accepting financial institution.
     */
    ACX("ACX", "Banker's acceptance"),

    /**
     * Reference number assigned by customs to a duty memo.
     */
    ACY("ACY", "Duty memo number"),

    /**
     * Reference assigned to a specific equipment transportation charge.
     */
    ACZ("ACZ", "Equipment transport charge number"),

    /**
     * [7304] Reference number assigned by the buyer to an item.
     */
    ADA("ADA", "Buyer's item number"),

    /**
     * Reference number for certificate of deposit allocated by issuing financial institution.
     */
    ADB("ADB", "Matured certificate of deposit"),

    /**
     * Reference number for loan allocated by lending financial institution.
     */
    ADC("ADC", "Loan"),

    /**
     * Number given to a specific analysis or test operation.
     */
    ADD("ADD", "Analysis number/test number"),

    /**
     * Identification number of an account.
     */
    ADE("ADE", "Account number"),

    /**
     * A number that identifies a treaty.
     */
    ADF("ADF", "Treaty number"),

    /**
     * A number that identifies a catastrophe.
     */
    ADG("ADG", "Catastrophe number"),

    /**
     * A statement reference that identifies a bureau signing.
     */
    ADI("ADI", "Bureau signing (statement reference)"),

    /**
     * First reference of a company/syndicate.
     */
    ADJ("ADJ", "Company / syndicate reference 1"),

    /**
     * Second reference of a company/syndicate.
     */
    ADK("ADK", "Company / syndicate reference 2"),

    /**
     * Reference number assigned to the consignment by the ordering customer.
     */
    ADL("ADL", "Ordering customer consignment reference number"),

    /**
     * Reference number assigned by the shipowner as an authorization number to transport certain
     * goods (such as hazardous goods, cool or reefer goods).
     */
    ADM("ADM", "Shipowner's authorization number"),

    /**
     * Reference number assigned by the principal to the transport order for inland carriage.
     */
    ADN("ADN", "Inland transport order number"),

    /**
     * Reference number assigned by the principal to the work order for a (set of) container(s).
     */
    ADO("ADO", "Container work order reference number"),

    /**
     * A reference number identifying a statement.
     */
    ADP("ADP", "Statement number"),

    /**
     * A number that identifies a unique market.
     */
    ADQ("ADQ", "Unique market reference"),

    /**
     * A number that identifies group accounting.
     */
    ADT("ADT", "Group accounting"),

    /**
     * First reference of a broker.
     */
    ADU("ADU", "Broker reference 1"),

    /**
     * Second reference of a broker.
     */
    ADV("ADV", "Broker reference 2"),

    /**
     * A number that identifies a Lloyd's claims office.
     */
    ADW("ADW", "Lloyd's claims office reference"),

    /**
     * A reference to a secure delivery terms and conditions agreement. A secured delivery
     * agreement is an agreement containing terms and conditions to secure deliveries in case
     * of failure in the production or logistics process of the supplier.
     */
    ADX("ADX", "Secure delivery terms and conditions agreement reference"),

    /**
     * Reference to a report to Customs by a carrier at the point of entry, encompassing both
     * conveyance and consignment information.
     */
    ADY("ADY", "Report number"),

    /**
     * Number assigned by a Customs authority which uniquely identifies a trader (i.e. importer,
     * exporter or declarant) for Customs purposes.
     */
    ADZ("ADZ", "Trader account number"),

    /**
     * A number that identifies an authorization for expense (AFE).
     */
    AE("AE", "Authorization for expense (AFE) number"),

    /**
     * Coded reference number that pertains to the business of a government agency.
     */
    AEA("AEA", "Government agency reference number"),

    /**
     * A number that identifies an assembly.
     */
    AEB("AEB", "Assembly number"),

    /**
     * A number that identifies a symbol.
     */
    AEC("AEC", "Symbol number"),

    /**
     * A number that identifies a commodity.
     */
    AED("AED", "Commodity number"),

    /**
     * Reference number assigned to a Eur 1 certificate.
     */
    AEE("AEE", "Eur 1 certificate number"),

    /**
     * Retrieval number for a process specification defined by customer.
     */
    AEF("AEF", "Customer process specification number"),

    /**
     * Retrieval number for a specification defined by customer.
     */
    AEG("AEG", "Customer specification number"),

    /**
     * Instructions or standards applicable for the whole message or a message line item. These
     * instructions or standards may be published by a neutral organization or authority or
     * another party concerned.
     */
    AEH("AEH", "Applicable instructions or standards"),

    /**
     * Registration number of the Customs declaration lodged for the previous Customs procedure.
     */
    AEI("AEI", "Registration number of previous Customs declaration"),

    /**
     * Reference to a message related to a post-entry.
     */
    AEJ("AEJ", "Post-entry reference"),

    /**
     * A number that identifies a payment order.
     */
    AEK("AEK", "Payment order number"),

    /**
     * Reference number by which a haulier/carrier will announce himself at the container terminal
     * or depot when delivering equipment.
     */
    AEL("AEL", "Delivery number (transport)"),

    /**
     * A predefined and identified sequence of points where goods are collected, agreed between
     * partners, e.g. the party in charge of organizing the transport and the parties where
     * goods will be collected. The same collecting points may be included in different transport
     * routes, but in a different sequence.
     */
    AEM("AEM", "Transport route"),

    /**
     * Number assigned by customer to a unique unit for inventory purposes.
     */
    AEN("AEN", "Customer's unit inventory number"),

    /**
     * Number assigned by seller to identify reservation of specified products.
     */
    AEO("AEO", "Product reservation number"),

    /**
     * Reference number assigned to a project.
     */
    AEP("AEP", "Project number"),

    /**
     * Reference number identifying a drawing list.
     */
    AEQ("AEQ", "Drawing list number"),

    /**
     * Reference number identifying a project specification.
     */
    AER("AER", "Project specification number"),

    /**
     * A number that identifies the primary reference.
     */
    AES("AES", "Primary reference"),

    /**
     * A number that identifies a request for cancellation.
     */
    AET("AET", "Request for cancellation number"),

    /**
     * Reference to a file regarding a control of the supplier carried out on departure of
     * the goods.
     */
    AEU("AEU", "Supplier's control number"),

    /**
     * [1123] Reference number assigned to a shipping note.
     */
    AEV("AEV", "Shipping note number"),

    /**
     * Reference number assigned to an empty container bill, see: 1001 = 708.
     */
    AEW("AEW", "Empty container bill number"),

    /**
     * Reference number assigned to a sea waybill, see: 1001 = 712.
     */
    AEX("AEX", "Non-negotiable maritime transport document number"),

    /**
     * Reference number assigned to a substitute air waybill, see: 1001 = 743.
     */
    AEY("AEY", "Substitute air waybill number"),

    /**
     * (1128) Reference number assigned to a despatch note (post parcels), see: 1001 = 750.
     */
    AEZ("AEZ", "Despatch note (post parcels) number"),

    /**
     * (8028) Identification of a commercial flight by carrier code and number as assigned
     * by the airline (IATA).
     */
    AF("AF", "Airlines flight identification number"),

    /**
     * Reference number assigned to a through bill of lading, see: 1001 = 761.
     */
    AFA("AFA", "Through bill of lading number"),

    /**
     * [1037] Reference number assigned to a cargo manifest.
     */
    AFB("AFB", "Cargo manifest number"),

    /**
     * Reference number assigned to a bordereau, see: 1001 = 787.
     */
    AFC("AFC", "Bordereau number"),

    /**
     * Number (1496 in CST) assigned by the declarant to an item.
     */
    AFD("AFD", "Customs item number"),

    /**
     * Reference number to relevant item within Commodity Control List covering actual products
     * change functionality.
     */
    AFE("AFE", "Export Control Commodity number (ECCN)"),

    /**
     * Reference where marking/label information derives from.
     */
    AFF("AFF", "Marking/label reference"),

    /**
     * A number that identifies a tariff.
     */
    AFG("AFG", "Tariff number"),

    /**
     * Purchase order number specified by the buyer for the assignment to vendor's replenishment
     * orders in a vendor managed inventory program.
     */
    AFH("AFH", "Replenishment purchase order number"),

    /**
     * A number that identifies immediate transportation for in bond movement.
     */
    AFI("AFI", "Immediate transportation no. for in bond movement"),

    /**
     * A number that identifies the transportation exportation number for an in bond movement.
     */
    AFJ("AFJ", "Transportation exportation no. for in bond movement"),

    /**
     * A number that identifies the immediate exportation number for an in bond movement.
     */
    AFK("AFK", "Immediate exportation no. for in bond movement"),

    /**
     * A number that identifies associated invoices.
     */
    AFL("AFL", "Associated invoices"),

    /**
     * A number that identifies the secondary customs reference.
     */
    AFM("AFM", "Secondary Customs reference"),

    /**
     * Reference of the account party.
     */
    AFN("AFN", "Account party's reference"),

    /**
     * Reference of the beneficiary.
     */
    AFO("AFO", "Beneficiary's reference"),

    /**
     * Reference of the second beneficiary.
     */
    AFP("AFP", "Second beneficiary's reference"),

    /**
     * Reference number of the applicant's bank.
     */
    AFQ("AFQ", "Applicant's bank reference"),

    /**
     * Reference number of the issuing bank.
     */
    AFR("AFR", "Issuing bank's reference"),

    /**
     * Reference number of the beneficiary's bank.
     */
    AFS("AFS", "Beneficiary's bank reference"),

    /**
     * Reference number assigned to a direct payment valuation.
     */
    AFT("AFT", "Direct payment valuation number"),

    /**
     * Reference number assigned to a direct payment valuation request.
     */
    AFU("AFU", "Direct payment valuation request number"),

    /**
     * Reference number assigned to a quantity valuation.
     */
    AFV("AFV", "Quantity valuation number"),

    /**
     * Reference number assigned to a quantity valuation request.
     */
    AFW("AFW", "Quantity valuation request number"),

    /**
     * Reference number assigned to a bill of quantities.
     */
    AFX("AFX", "Bill of quantities number"),

    /**
     * Reference number assigned to a payment valuation.
     */
    AFY("AFY", "Payment valuation number"),

    /**
     * Common reference number given to documents concerning a determined period of works.
     */
    AFZ("AFZ", "Situation number"),

    /**
     * A number that identifies an agreement to pay.
     */
    AGA("AGA", "Agreement to pay number"),

    /**
     * Reference number assigned to a party for a particular contract.
     */
    AGB("AGB", "Contract party reference number"),

    /**
     * Reference number of the account party's bank.
     */
    AGC("AGC", "Account party's bank reference"),

    /**
     * Reference number issued by the agent's bank.
     */
    AGD("AGD", "Agent's bank reference"),

    /**
     * Reference number of the agent.
     */
    AGE("AGE", "Agent's reference"),

    /**
     * Reference number of the applicant.
     */
    AGF("AGF", "Applicant's reference"),

    /**
     * Reference number to a dispute notice.
     */
    AGG("AGG", "Dispute number"),

    /**
     * Reference number assigned by a credit rating agency to a debtor.
     */
    AGH("AGH", "Credit rating agency's reference number"),

    /**
     * The reference number of a request.
     */
    AGI("AGI", "Request number"),

    /**
     * A number that identifies a single transaction sequence.
     */
    AGJ("AGJ", "Single transaction sequence number"),

    /**
     * A number that identifies an application reference.
     */
    AGK("AGK", "Application reference number"),

    /**
     * Formal identification of delivery verification certificate which is a formal document
     * from Customs etc. confirming that physical goods have been delivered. It may be needed
     * to support a tax reclaim based on an invoice.
     */
    AGL("AGL", "Delivery verification certificate"),

    /**
     * Number assigned by customs to identify consignment in transit.
     */
    AGM("AGM", "Number of temporary importation document"),

    /**
     * Reference number quoted on the statement sent to the beneficiary for information purposes.
     */
    AGN("AGN", "Reference number quoted on statement"),

    /**
     * The reference provided by the sender of the original message.
     */
    AGO("AGO", "Sender's reference to the original message"),

    /**
     * Owner/operator, non-government issued equipment reference number.
     */
    AGP("AGP", "Company issued equipment ID"),

    /**
     * Airline flight number assigned to a flight originating and terminating within the same
     * country.
     */
    AGQ("AGQ", "Domestic flight number"),

    /**
     * Airline flight number assigned to a flight originating and terminating across national
     * borders.
     */
    AGR("AGR", "International flight number"),

    /**
     * Reference number assigned by a service/processing bureau to an employer.
     */
    AGS("AGS", "Employer identification number of service bureau"),

    /**
     * Identification used for a group of services.
     */
    AGT("AGT", "Service group identification number"),

    /**
     * Reference number assigned to a person as a member of a group of persons or a service
     * scheme.
     */
    AGU("AGU", "Member number"),

    /**
     * Reference number previously assigned to a member.
     */
    AGV("AGV", "Previous member number"),

    /**
     * Reference number assigned to a service scheme or plan.
     */
    AGW("AGW", "Scheme/plan number"),

    /**
     * Reference number previously assigned to a service scheme or plan.
     */
    AGX("AGX", "Previous scheme/plan number"),

    /**
     * Identification used by the receiving party for a member of a service scheme or group
     * of persons.
     */
    AGY("AGY", "Receiving party's member identification"),

    /**
     * Reference number assigned to the payroll of an organisation.
     */
    AGZ("AGZ", "Payroll number"),

    /**
     * Reference number of documentation specifying the technical detail of packaging requirements.
     */
    AHA("AHA", "Packaging specification number"),

    /**
     * Identification issued by an authority, e.g. government, airport authority.
     */
    AHB("AHB", "Authority issued equipment identification"),

    /**
     * Non-revenue producing airline flight for training purposes.
     */
    AHC("AHC", "Training flight number"),

    /**
     * Reference number to identify appropriation and branch chargeable for item.
     */
    AHD("AHD", "Fund code number"),

    /**
     * Reference number to identify a signal.
     */
    AHE("AHE", "Signal code number"),

    /**
     * Reference number according to Major Force Program (US).
     */
    AHF("AHF", "Major force program number"),

    /**
     * Reference number assigned by a shipper to a request/ commitment-to-ship on a pipeline
     * system.
     */
    AHG("AHG", "Nomination number"),

    /**
     * Reference number is the official registration number of the laboratory.
     */
    AHH("AHH", "Laboratory registration number"),

    /**
     * Reference number of a transport contract.
     */
    AHI("AHI", "Transport contract reference number"),

    /**
     * Reference number of the party to be paid.
     */
    AHJ("AHJ", "Payee's reference number"),

    /**
     * Reference number of the party who pays.
     */
    AHK("AHK", "Payer's reference number"),

    /**
     * Reference number of the party to whom a debt is owed.
     */
    AHL("AHL", "Creditor's reference number"),

    /**
     * Reference number of the party who owes an amount of money.
     */
    AHM("AHM", "Debtor's reference number"),

    /**
     * Reference number assigned to a joint venture agreement.
     */
    AHN("AHN", "Joint venture reference number"),

    /**
     * The registration number by which a company/organization is known to the Chamber of Commerce.
     */
    AHO("AHO", "Chamber of Commerce registration number"),

    /**
     * The registration number by which a company/organization is identified with the tax administration.
     */
    AHP("AHP", "Tax registration number"),

    /**
     * Shipping Identification Mark (SIM) allocated to a wool consignment by a shipping company.
     */
    AHQ("AHQ", "Wool identification number"),

    /**
     * Reference or indication of the payment of wool tax.
     */
    AHR("AHR", "Wool tax reference number"),

    /**
     * Registration number allocated to a registered meat packing establishment by the local
     * quarantine and inspection authority.
     */
    AHS("AHS", "Meat processing establishment registration number"),

    /**
     * Coded quarantine/treatment status of a container and its cargo and packing materials,
     * generated by a shipping company based upon declarations presented by a shipper.
     */
    AHT("AHT", "Quarantine/treatment status reference number"),

    /**
     * Reference number assigned by the requestor to a request for quote.
     */
    AHU("AHU", "Request for quote number"),

    /**
     * Number allocated to allow the manual processing of an entity.
     */
    AHV("AHV", "Manual processing authority number"),

    /**
     * Reference assigned to a specific rate.
     */
    AHX("AHX", "Rate note number"),

    /**
     * An identification code of a Freight Forwarder.
     */
    AHY("AHY", "Freight Forwarder number"),

    /**
     * A code associated to a requirement that must be presented to gain the release of goods
     * by Customs.
     */
    AHZ("AHZ", "Customs release code"),

    /**
     * Number assigned to indicate regulatory compliance.
     */
    AIA("AIA", "Compliance code number"),

    /**
     * Number of a bond assigned by the department of transportation.
     */
    AIB("AIB", "Department of transportation bond number"),

    /**
     * Number to identify export establishment.
     */
    AIC("AIC", "Export establishment number"),

    /**
     * Certificate certifying the conformity to predefined definitions.
     */
    AID("AID", "Certificate of conformity"),

    /**
     * Certificate of approval for components which are subject to legal restrictions and must
     * be approved by the government.
     */
    AIE("AIE", "Ministerial certificate of homologation"),

    /**
     * The identification of a previous delivery instruction.
     */
    AIF("AIF", "Previous delivery instruction number"),

    /**
     * Number assigned to a passport.
     */
    AIG("AIG", "Passport number"),

    /**
     * Reference number applicable to different underlying individual transactions.
     */
    AIH("AIH", "Common transaction reference number"),

    /**
     * Bank's reference number allocated by the bank to different underlying individual transactions.
     */
    AII("AII", "Bank's common transaction reference number"),

    /**
     * Customer's reference number allocated by the customer to one specific transaction.
     */
    AIJ("AIJ", "Customer's individual transaction reference number"),

    /**
     * Bank's reference number allocated by the bank to one specific transaction.
     */
    AIK("AIK", "Bank's individual transaction reference number"),

    /**
     * Customer's reference number allocated by the customer to different underlying individual
     * transactions.
     */
    AIL("AIL", "Customer's common transaction reference number"),

    /**
     * Reference number applying to one specific transaction.
     */
    AIM("AIM", "Individual transaction reference number"),

    /**
     * Reference number assigned to a product sourcing agreement.
     */
    AIN("AIN", "Product sourcing agreement number"),

    /**
     * Approval number issued by Customs for cargo to be transhipped under Customs control.
     */
    AIO("AIO", "Customs transhipment number"),

    /**
     * The number assigned by Customs to a preference inquiry.
     */
    AIP("AIP", "Customs preference inquiry number"),

    /**
     * Number to identify packing establishment.
     */
    AIQ("AIQ", "Packing plant number"),

    /**
     * Number giving reference to an original certificate number.
     */
    AIR("AIR", "Original certificate number"),

    /**
     * Number to identify processing plant.
     */
    AIS("AIS", "Processing plant number"),

    /**
     * Number to identify slaughter plant.
     */
    AIT("AIT", "Slaughter plant number"),

    /**
     * Number to identify charge card account.
     */
    AIU("AIU", "Charge card account number"),

    /**
     * [1007] Reference number identifying an event.
     */
    AIV("AIV", "Event reference number"),

    /**
     * A number identifying a transport section.
     */
    AIW("AIW", "Transport section reference number"),

    /**
     * A product number identifying the product which is used for mechanical analysis considered
     * valid for a group of products.
     */
    AIX("AIX", "Referred product for mechanical analysis"),

    /**
     * A product number identifying the product which is used for chemical analysis considered
     * valid for a group of products.
     */
    AIY("AIY", "Referred product for chemical analysis"),

    /**
     * Invoice number into which other invoices are consolidated.
     */
    AIZ("AIZ", "Consolidated invoice number"),

    /**
     * To designate the number which provides a cross reference between parts contained in
     * a drawing and a parts catalogue.
     */
    AJA("AJA", "Part reference indicator in a drawing"),

    /**
     * A reference indicating a citation from the U.S. Code of Federal Regulations (CFR).
     */
    AJB("AJB", "U.S. Code of Federal Regulations (CFR)"),

    /**
     * A number indicating a clause applicable to a purchasing activity.
     */
    AJC("AJC", "Purchasing activity clause number"),

    /**
     * A reference indicating a citation from the U.S. Defense Federal Acquisition Regulation
     * Supplement.
     */
    AJD("AJD", "U.S. Defense Federal Acquisition Regulation Supplement"),

    /**
     * A number indicating a clause applicable to a particular agency.
     */
    AJE("AJE", "Agency clause number"),

    /**
     * A number specifying a circular publication.
     */
    AJF("AJF", "Circular publication number"),

    /**
     * A reference indicating a citation from the U.S. Federal Acquisition Regulation.
     */
    AJG("AJG", "U.S. Federal Acquisition Regulation"),

    /**
     * A reference indicating a citation from U.S. General Services Administration Regulation.
     */
    AJH("AJH", "U.S. General Services Administration Regulation"),

    /**
     * A reference indicating a citation from U.S. Federal Information Resources Management
     * Regulation.
     */
    AJI("AJI", "U.S. Federal Information Resources Management Regulation"),

    /**
     * A reference indicating a paragraph cited as the source of information.
     */
    AJJ("AJJ", "Paragraph"),

    /**
     * A number indicating a citation used for special instructions.
     */
    AJK("AJK", "Special instructions number"),

    /**
     * A number indicating a set of site specific procedures, terms and conditions.
     */
    AJL("AJL", "Site specific procedures, terms, and conditions number"),

    /**
     * A number indicating a master solicitation containing procedures, terms and conditions.
     */
    AJM("AJM", "Master solicitation procedures, terms, and conditions number"),

    /**
     * A reference indicating a citation from the U.S. Department of Veterans Affairs Acquisition
     * Regulation.
     */
    AJN("AJN", "U.S. Department of Veterans Affairs Acquisition Regulation"),

    /**
     * A number indicating an interdepartmental purchase request used by the military.
     */
    AJO("AJO", "Military Interdepartmental Purchase Request (MIPR) number"),

    /**
     * A number specifying a sale to a foreign military.
     */
    AJP("AJP", "Foreign military sales number"),

    /**
     * A reference indicating a priority rating assigned to allocate resources for defense
     * purchases.
     */
    AJQ("AJQ", "Defense priorities allocation system priority rating"),

    /**
     * A number specifying a wage determination.
     */
    AJR("AJR", "Wage determination number"),

    /**
     * A number specifying an agreement between parties.
     */
    AJS("AJS", "Agreement number"),

    /**
     * A number specifying a standard industry classification.
     */
    AJT("AJT", "Standard Industry Classification (SIC) number"),

    /**
     * A number specifying the end item applicable to a subordinate item.
     */
    AJU("AJU", "End item number"),

    /**
     * A number specifying an item listed in a federal supply schedule.
     */
    AJV("AJV", "Federal supply schedule item number"),

    /**
     * A number specifying a technical document.
     */
    AJW("AJW", "Technical document number"),

    /**
     * A reference to an order that specifies a technical change.
     */
    AJX("AJX", "Technical order number"),

    /**
     * A reference to specify a suffix added to the end of a basic identifier.
     */
    AJY("AJY", "Suffix"),

    /**
     * An account number to be charged or credited for transportation.
     */
    AJZ("AJZ", "Transportation account number"),

    /**
     * Reference assigned to the empty container disposition order.
     */
    AKA("AKA", "Container disposition order reference number"),

    /**
     * The first part of the unique identification of a container formed by an alpha code identifying
     * the owner of the container.
     */
    AKB("AKB", "Container prefix"),

    /**
     * Reference known at the address to return equipment to.
     */
    AKC("AKC", "Transport equipment return reference"),

    /**
     * Reference number assigned by the ordering party to the transport equipment survey order.
     */
    AKD("AKD", "Transport equipment survey reference"),

    /**
     * Reference number used by a party to identify its transport equipment survey report.
     */
    AKE("AKE", "Transport equipment survey report number"),

    /**
     * Reference number assigned to the order to stuff goods in transport equipment.
     */
    AKF("AKF", "Transport equipment stuffing order"),

    /**
     * The identification number which uniquely distinguishes one vehicle from another through
     * the lifespan of the vehicle.
     */
    AKG("AKG", "Vehicle Identification Number (VIN)"),

    /**
     * Bill of lading as defined by the government.
     */
    AKH("AKH", "Government bill of lading"),

    /**
     * Ordering customer's second reference number.
     */
    AKI("AKI", "Ordering customer's second reference number"),

    /**
     * Reference number assigned to the direct debit operation.
     */
    AKJ("AKJ", "Direct debit reference"),

    /**
     * Meter reading at the beginning of the delivery.
     */
    AKK("AKK", "Meter reading at the beginning of the delivery"),

    /**
     * Meter reading at the end of the delivery.
     */
    AKL("AKL", "Meter reading at the end of delivery"),

    /**
     * Starting number of a range of purchase order numbers assigned by the buyer to vendor's
     * replenishment orders.
     */
    AKM("AKM", "Replenishment purchase order range start number"),

    /**
     * Reference number of the third bank.
     */
    AKN("AKN", "Third bank's reference"),

    /**
     * A reference number authorizing an action.
     */
    AKO("AKO", "Action authorization number"),

    /**
     * The number identifying a type of funding for a specific purpose (appropriation).
     */
    AKP("AKP", "Appropriation number"),

    /**
     * Number which authorises a change in form, fit or function of a product.
     */
    AKQ("AKQ", "Product change authority number"),

    /**
     * Reference number identifying a particular general cargo (non-containerised or break
     * bulk) consignment.
     */
    AKR("AKR", "General cargo consignment reference number"),

    /**
     * A number which uniquely identifies an item within a catalogue according to a standard
     * numbering system.
     */
    AKS("AKS", "Catalogue sequence number"),

    /**
     * Reference number assigned to the forwarding order by the ordering customer.
     */
    AKT("AKT", "Forwarding order number"),

    /**
     * Reference number known at the address where the transport equipment will be or has been
     * surveyed.
     */
    AKU("AKU", "Transport equipment survey reference number"),

    /**
     * Reference number of the lease contract.
     */
    AKV("AKV", "Lease contract reference"),

    /**
     * Reference number of the transport costs.
     */
    AKW("AKW", "Transport costs reference number"),

    /**
     * Reference number assigned to the order to strip goods from transport equipment.
     */
    AKX("AKX", "Transport equipment stripping order"),

    /**
     * The number of the prior policy.
     */
    AKY("AKY", "Prior policy number"),

    /**
     * Number assigned to a policy.
     */
    AKZ("AKZ", "Policy number"),

    /**
     * A number which uniquely identifies a procurement budget against which commitments or
     * invoices can be allocated.
     */
    ALA("ALA", "Procurement budget number"),

    /**
     * Code to identify the management of domestic inventory.
     */
    ALB("ALB", "Domestic inventory management code"),

    /**
     * Identification number of the previous balance of payments information from customer
     * message.
     */
    ALC("ALC", "Customer reference number assigned to previous balance of payment information"),

    /**
     * Reference number of the previous "Credit advice" message.
     */
    ALD("ALD", "Previous credit advice reference number"),

    /**
     * Reference number assigned to the reporting form.
     */
    ALE("ALE", "Reporting form number"),

    /**
     * Reference number allocated by an authority. This number contains an approval concerning
     * exceptions on the existing dangerous goods regulations.
     */
    ALF("ALF", "Authorization number for exception to dangerous goods regulations"),

    /**
     * Reference number allocated by an authority in order to control the dangerous goods on
     * board of a specific means of transport for dangerous goods security purposes.
     */
    ALG("ALG", "Dangerous goods security number"),

    /**
     * Licence number allocated by an authority as to the permission of carrying dangerous
     * goods by a specific means of transport.
     */
    ALH("ALH", "Dangerous goods transport licence number"),

    /**
     * Number to identify the previous rental agreement number.
     */
    ALI("ALI", "Previous rental agreement number"),

    /**
     * Number to identify the reason for the next rental agreement.
     */
    ALJ("ALJ", "Next rental agreement reason number"),

    /**
     * The invoice number assigned by a consignee.
     */
    ALK("ALK", "Consignee's invoice number"),

    /**
     * A number identifying a batch of messages.
     */
    ALL("ALL", "Message batch number"),

    /**
     * A reference number identifying a previous delivery schedule.
     */
    ALM("ALM", "Previous delivery schedule number"),

    /**
     * A reference to a re-count of physically held inventory.
     */
    ALN("ALN", "Physical inventory recount reference number"),

    /**
     * A reference number to a receiving advice.
     */
    ALO("ALO", "Receiving advice number"),

    /**
     * A reference number identifying a returnable container.
     */
    ALP("ALP", "Returnable container reference number"),

    /**
     * A reference number to a returns notice.
     */
    ALQ("ALQ", "Returns notice number"),

    /**
     * A reference number identifying a sales forecast.
     */
    ALR("ALR", "Sales forecast number"),

    /**
     * A reference number identifying a sales report.
     */
    ALS("ALS", "Sales report number"),

    /**
     * A reference number identifying a previous tax control number.
     */
    ALT("ALT", "Previous tax control number"),

    /**
     * Identifies the equipment required to conduct maintenance.
     */
    ALU("ALU", "AGERD (Aerospace Ground Equipment Requirement Data) number"),

    /**
     * Registered capital reference of a company.
     */
    ALV("ALV", "Registered capital reference"),

    /**
     * Code identifying the standard number of the inspection document supplied.
     */
    ALW("ALW", "Standard number of inspection document"),

    /**
     * (7242) A reference used to identify a model.
     */
    ALX("ALX", "Model"),

    /**
     * A financial management reference.
     */
    ALY("ALY", "Financial management reference"),

    /**
     * A reference assigned by a consignor to a notification document which indicates the availability
     * of goods for collection.
     */
    ALZ("ALZ", "NOTIfication for COLlection number (NOTICOL)"),

    /**
     * Number to identify a previous request for a recording or reading of a measuring device.
     */
    AMA("AMA", "Previous request for metered reading reference number"),

    /**
     * Number to identify the next rental agreement.
     */
    AMB("AMB", "Next rental agreement number"),

    /**
     * Number to identify a request for a recording or reading of a measuring device to be
     * taken.
     */
    AMC("AMC", "Reference number of a request for metered reading"),

    /**
     * A number which uniquely identifies a request to hasten an action.
     */
    AMD("AMD", "Hastening number"),

    /**
     * A number which uniquely identifies a request for data about repairs.
     */
    AME("AME", "Repair data request number"),

    /**
     * A number which identifies a request for consumption data.
     */
    AMF("AMF", "Consumption data request number"),

    /**
     * Reference number allocated to a discrete set of criteria.
     */
    AMG("AMG", "Profile number"),

    /**
     * Number assigned to a case.
     */
    AMH("AMH", "Case number"),

    /**
     * A number which identifies the level of quality assurance and control required by the
     * government for an article.
     */
    AMI("AMI", "Government quality assurance and control level Number"),

    /**
     * A number which uniquely identifies a payment plan.
     */
    AMJ("AMJ", "Payment plan reference"),

    /**
     * Number identifying the replaced meter unit.
     */
    AMK("AMK", "Replaced meter unit number"),

    /**
     * Ending number of a range of purchase order numbers assigned by the buyer to vendor's
     * replenishment orders.
     */
    AML("AML", "Replenishment purchase order range end number"),

    /**
     * A unique reference number assigned by the insurer.
     */
    AMM("AMM", "Insurer assigned reference number"),

    /**
     * An excise entry number assigned by the Canadian Customs.
     */
    AMN("AMN", "Canadian excise entry number"),

    /**
     * Identifies the premium rate table.
     */
    AMO("AMO", "Premium rate table"),

    /**
     * Financial institution through which the advising bank is to advise the documentary credit.
     */
    AMP("AMP", "Advise through bank's reference"),

    /**
     * A bond surety code assigned by the United States Department of Transportation (DOT).
     */
    AMQ("AMQ", "US, Department of Transportation bond surety code"),

    /**
     * An establishment indicator assigned by the United States Food and Drug Administration.
     */
    AMR("AMR", "US, Food and Drug Administration establishment indicator"),

    /**
     * A number known as the United States Federal Communications Commission (FCC) import condition
     * number applying to certain types of regulated communications equipment.
     */
    AMS("AMS", "US, Federal Communications Commission (FCC) import condition number"),

    /**
     * Identifier assigned to an entity by a tax authority for Goods and Services Tax (GST)
     * related purposes.
     */
    AMT("AMT", "Goods and Services Tax identification number"),

    /**
     * Provides the identification of the reference which allows cross referencing of items
     * between different areas of integrated logistics support.
     */
    AMU("AMU", "Integrated logistic support cross reference number"),

    /**
     * Number assigned to a department within an organization.
     */
    AMV("AMV", "Department number"),

    /**
     * Identification of a catalogue maintained by a buyer.
     */
    AMW("AMW", "Buyer's catalogue number"),

    /**
     * Reference number of the party who is responsible for the financial settlement.
     */
    AMX("AMX", "Financial settlement party's reference number"),

    /**
     * The version number assigned to a standard.
     */
    AMY("AMY", "Standard's version number"),

    /**
     * Number to identify a pipeline.
     */
    AMZ("AMZ", "Pipeline number"),

    /**
     * Reference number of the account servicing bank.
     */
    ANA("ANA", "Account servicing bank's reference number"),

    /**
     * A reference to a payment request for completed units.
     */
    ANB("ANB", "Completed units payment request reference"),

    /**
     * A reference to a request for payment in advance.
     */
    ANC("ANC", "Payment in advance request reference"),

    /**
     * Identifies the parent file in a structure of related files.
     */
    AND("AND", "Parent file"),

    /**
     * Identifies the sub file in a structure of related files.
     */
    ANE("ANE", "Sub file"),

    /**
     * Reference number identifying a layer convention for a file in a Computer Aided Design
     * (CAD) environment.
     */
    ANF("ANF", "CAD file layer convention"),

    /**
     * Reference number identifying a technical regulation.
     */
    ANG("ANG", "Technical regulation"),

    /**
     * Reference number indicating that the file is a plot file.
     */
    ANH("ANH", "Plot file"),

    /**
     * Reference number identifying a journal recording details about conversion operations
     * between file formats.
     */
    ANI("ANI", "File conversion journal"),

    /**
     * A number which uniquely identifies an authorization.
     */
    ANJ("ANJ", "Authorization number"),

    /**
     * Reference number assigned by a third party.
     */
    ANK("ANK", "Reference number assigned by third party"),

    /**
     * A reference number identifying a deposit.
     */
    ANL("ANL", "Deposit reference number"),

    /**
     * Reference number of the named bank.
     */
    ANM("ANM", "Named bank's reference"),

    /**
     * Reference number of the drawee.
     */
    ANN("ANN", "Drawee's reference"),

    /**
     * Reference number of the case of need party.
     */
    ANO("ANO", "Case of need party's reference"),

    /**
     * Reference number of the collecting bank.
     */
    ANP("ANP", "Collecting bank's reference"),

    /**
     * Reference number of the remitting bank.
     */
    ANQ("ANQ", "Remitting bank's reference"),

    /**
     * Reference number of the principal's bank.
     */
    ANR("ANR", "Principal's bank reference"),

    /**
     * Reference number of the presenting bank.
     */
    ANS("ANS", "Presenting bank's reference"),

    /**
     * Reference number of the consignee.
     */
    ANT("ANT", "Consignee's reference"),

    /**
     * Reference number of the financial transaction.
     */
    ANU("ANU", "Financial transaction reference number"),

    /**
     * The reference number of a credit instruction.
     */
    ANV("ANV", "Credit reference number"),

    /**
     * Authorization number of the receiving bank.
     */
    ANW("ANW", "Receiving bank's authorization number"),

    /**
     * Reference allocated by a clearing procedure.
     */
    ANX("ANX", "Clearing reference"),

    /**
     * Reference number of the sending bank.
     */
    ANY("ANY", "Sending bank's reference number"),

    /**
     * Reference of the documentary payment.
     */
    AOA("AOA", "Documentary payment reference"),

    /**
     * Reference of an accounting file.
     */
    AOD("AOD", "Accounting file reference"),

    /**
     * File reference number assigned by the sender.
     */
    AOE("AOE", "Sender's file reference number"),

    /**
     * File reference number assigned by the receiver.
     */
    AOF("AOF", "Receiver's file reference number"),

    /**
     * Reference number assigned to a source document for internal usage.
     */
    AOG("AOG", "Source document internal reference"),

    /**
     * Reference number of the principal.
     */
    AOH("AOH", "Principal's reference"),

    /**
     * The reference number of a debit instruction.
     */
    AOI("AOI", "Debit reference number"),

    /**
     * A calendar reference number.
     */
    AOJ("AOJ", "Calendar"),

    /**
     * A work shift reference number.
     */
    AOK("AOK", "Work shift"),

    /**
     * A structure reference that identifies the breakdown of work for a project.
     */
    AOL("AOL", "Work breakdown structure"),

    /**
     * A structure reference that identifies the breakdown of an organisation.
     */
    AOM("AOM", "Organisation breakdown structure"),

    /**
     * A reference assigned to a specific work task charge.
     */
    AON("AON", "Work task charge number"),

    /**
     * A reference to identify a functional group performing work.
     */
    AOO("AOO", "Functional work group"),

    /**
     * A reference to identify a team performing work.
     */
    AOP("AOP", "Work team"),

    /**
     * Section of an organisation.
     */
    AOQ("AOQ", "Department"),

    /**
     * A reference number for a statement of work.
     */
    AOR("AOR", "Statement of work"),

    /**
     * A reference for a detailed package of work.
     */
    AOS("AOS", "Work package"),

    /**
     * A reference for a planning package of work.
     */
    AOT("AOT", "Planning package"),

    /**
     * A cost control account reference.
     */
    AOU("AOU", "Cost account"),

    /**
     * Reference number for an order to do work.
     */
    AOV("AOV", "Work order"),

    /**
     * A number assigned for transportation purposes.
     */
    AOW("AOW", "Transportation Control Number (TCN)"),

    /**
     * Identifies a reference to a constraint notation.
     */
    AOX("AOX", "Constraint notation"),

    /**
     * Identifies a reference to the ICC (International Chamber of Commerce) ETERMS(tm) repository
     * of electronic commerce trading terms and conditions.
     */
    AOY("AOY", "ETERMS reference"),

    /**
     * Identifies a version number of an implementation.
     */
    AOZ("AOZ", "Implementation version number"),

    /**
     * Reference number assigned by accounts receivable department to the account of a specific
     * debtor.
     */
    AP("AP", "Accounts receivable number"),

    /**
     * Identifies a legal reference which is deemed incorporated by reference.
     */
    APA("APA", "Incorporated legal reference"),

    /**
     * A reference number given to a payment instalment to identify a specific instance of
     * payment of a debt which can be paid at specified intervals.
     */
    APB("APB", "Payment instalment reference number"),

    /**
     * Reference number issued by the owner of the equipment.
     */
    APC("APC", "Equipment owner reference number"),

    /**
     * To identify the number assigned to the claim by the ceding company.
     */
    APD("APD", "Cedent's claim number"),

    /**
     * To identify the number assigned to the claim by the reinsurer.
     */
    APE("APE", "Reinsurer's claim number"),

    /**
     * A reference number identifying a response to a price/sales catalogue.
     */
    APF("APF", "Price/sales catalogue response reference number"),

    /**
     * A reference number identifying a general purpose message.
     */
    APG("APG", "General purpose message reference number"),

    /**
     * A reference number identifying an invoicing data sheet.
     */
    APH("APH", "Invoicing data sheet reference number"),

    /**
     * A reference number identifying an inventory report.
     */
    API("API", "Inventory report reference number"),

    /**
     * The reference number which identifies a formula for determining a ceiling.
     */
    APJ("APJ", "Ceiling formula reference number"),

    /**
     * The reference number which identifies a price variation formula.
     */
    APK("APK", "Price variation formula reference number"),

    /**
     * Reference to the account servicing bank's message.
     */
    APL("APL", "Reference to account servicing bank's message"),

    /**
     * Reference identifying a party sequence number.
     */
    APM("APM", "Party sequence number"),

    /**
     * Reference identifying a request made by the purchaser.
     */
    APN("APN", "Purchaser's request reference"),

    /**
     * Reference identifying a request made by a contractor.
     */
    APO("APO", "Contractor request reference"),

    /**
     * Reference number assigned to an accident.
     */
    APP("APP", "Accident reference number"),

    /**
     * A reference number identifying a commercial account summary.
     */
    APQ("APQ", "Commercial account summary reference number"),

    /**
     * A reference which identifies a specific breakdown of a contract.
     */
    APR("APR", "Contract breakdown reference"),

    /**
     * A reference number used to identify a contractor.
     */
    APS("APS", "Contractor registration number"),

    /**
     * The identification number of the coefficient which is applicable.
     */
    APT("APT", "Applicable coefficient identification number"),

    /**
     * The number of a special budget account.
     */
    APU("APU", "Special budget account number"),

    /**
     * Reference of the authorisation for repair.
     */
    APV("APV", "Authorisation for repair reference"),

    /**
     * Reference assigned by a manufacturer to their repair rates.
     */
    APW("APW", "Manufacturer defined repair rates reference"),

    /**
     * A control number assigned by the original submitter.
     */
    APX("APX", "Original submitter log number"),

    /**
     * A Data Maintenance Request (DMR) original submitter's reference log number for the parent
     * DMR.
     */
    APY("APY", "Original submitter, parent Data Maintenance Request (DMR) log number"),

    /**
     * A Data Maintenance Request (DMR) original submitter's reference log number for a child
     * DMR.
     */
    APZ("APZ", "Original submitter, child Data Maintenance Request (DMR) log number"),

    /**
     * The reference log number assigned by an entry point assessment group for the DMR.
     */
    AQA("AQA", "Entry point assessment log number"),

    /**
     * The reference log number assigned by an entry point assessment group for the parent
     * Data Maintenance Request (DMR).
     */
    AQB("AQB", "Entry point assessment log number, parent DMR"),

    /**
     * The reference log number assigned by an entry point assessment group for a child Data
     * Maintenance Request (DMR).
     */
    AQC("AQC", "Entry point assessment log number, child DMR"),

    /**
     * The tag assigned to a data structure.
     */
    AQD("AQD", "Data structure tag"),

    /**
     * The reference log number assigned by the central secretariat for the Data Maintenance
     * Request (DMR).
     */
    AQE("AQE", "Central secretariat log number"),

    /**
     * The reference log number assigned by the central secretariat for the parent Data Maintenance
     * Request (DMR).
     */
    AQF("AQF", "Central secretariat log number, parent Data Maintenance Request (DMR)"),

    /**
     * The reference log number assigned by the central secretariat for the child Data Maintenance
     * Request (DMR).
     */
    AQG("AQG", "Central secretariat log number, child Data Maintenance Request (DMR)"),

    /**
     * The reference log number assigned to a Data Maintenance Request (DMR) changed in international
     * assessment.
     */
    AQH("AQH", "International assessment log number"),

    /**
     * The reference log number assigned to a Data Maintenance Request (DMR) changed in international
     * assessment that is a parent to the current DMR.
     */
    AQI("AQI", "International assessment log number, parent Data Maintenance Request (DMR)"),

    /**
     * The reference log number assigned to a Data Maintenance Request (DMR) changed in international
     * assessment that is a child to the current DMR.
     */
    AQJ("AQJ", "International assessment log number, child Data Maintenance Request (DMR)"),

    /**
     * (1125) The reference number for a status report.
     */
    AQK("AQK", "Status report number"),

    /**
     * Reference number for a message design group.
     */
    AQL("AQL", "Message design group number"),

    /**
     * An entry number assigned by the United States (US) customs service.
     */
    AQM("AQM", "US Customs Service (USCS) entry code"),

    /**
     * The number designating the beginning of the job sequence.
     */
    AQN("AQN", "Beginning job sequence number"),

    /**
     * The number that identifies the sender's clause.
     */
    AQO("AQO", "Sender's clause number"),

    /**
     * Dun and Bradstreet Canada's 8 digit Standard Industrial Classification (SIC) code identifying
     * activities of the company.
     */
    AQP("AQP", "Dun and Bradstreet Canada's 8 digit Standard Industrial Classification (SIC) code"),

    /**
     * The French industry code for the main activity of a company.
     */
    AQQ("AQQ", "Activite Principale Exercee (APE) identifier"),

    /**
     * Dun and Bradstreet United States' 8 digit Standard Industrial Classification (SIC) code
     * identifying activities of the company.
     */
    AQR("AQR", "Dun and Bradstreet US 8 digit Standard Industrial Classification (SIC) code"),

    /**
     * A European industry classification code used to identify the activity of a company.
     */
    AQS("AQS", "Nomenclature Activity Classification Economy (NACE) identifier"),

    /**
     * A French industry classification code assigned by the French government to identify
     * the activity of a company.
     */
    AQT("AQT", "Norme Activite Francaise (NAF) identifier"),

    /**
     * Reference number identifying the type of registered contractor activity.
     */
    AQU("AQU", "Registered contractor activity type"),

    /**
     * A German industry classification code issued by Statistic Bundes Amt (SBA) to identify
     * the activity of a company.
     */
    AQV("AQV", "Statistic Bundes Amt (SBA) identifier"),

    /**
     * Reference number of an entity assigned by a state or province.
     */
    AQW("AQW", "State or province assigned entity identification"),

    /**
     * A number used to identify a public but not publicly traded company.
     */
    AQX("AQX", "Institute of Security and Future Market Development (ISFMD) serial number"),

    /**
     * A number assigned to identify a file.
     */
    AQY("AQY", "File identification number"),

    /**
     * A number identifying a bankruptcy procedure.
     */
    AQZ("AQZ", "Bankruptcy procedure number"),

    /**
     * A business identification number which is assigned by a national government.
     */
    ARA("ARA", "National government business identification number"),

    /**
     * A previously assigned Data Universal Number System (DUNS) number.
     */
    ARB("ARB", "Prior Data Universal Number System (DUNS) number"),

    /**
     * Identifies the reference number assigned by the Companies Registry Office (CRO).
     */
    ARC("ARC", "Companies Registry Office (CRO) number"),

    /**
     * A number assigned by the government to a business in Costa Rica.
     */
    ARD("ARD", "Costa Rican judicial number"),

    /**
     * A number assigned by the government to a business in some Latin American countries.
     */
    ARE("ARE", "Numero de Identificacion Tributaria (NIT)"),

    /**
     * A number assigned by the government to a business in some Latin American countries.
     * Note that "Patron" is a Spanish word, it is not a person who gives financial or other
     * support.
     */
    ARF("ARF", "Patron number"),

    /**
     * A number assigned by the government to a business in some Latin American countries.
     */
    ARG("ARG", "Registro Informacion Fiscal (RIF) number"),

    /**
     * A number assigned by the government to a business in some Latin American countries.
     */
    ARH("ARH", "Registro Unico de Contribuyente (RUC) number"),

    /**
     * A number assigned to a business by TSR.
     */
    ARI("ARI", "Tokyo SHOKO Research (TSR) business identifier"),

    /**
     * An identity card number assigned to a person.
     */
    ARJ("ARJ", "Personal identity card number"),

    /**
     * An identification number known as a SIREN assigned to a business in France.
     */
    ARK("ARK", "Systeme Informatique pour le Repertoire des ENtreprises (SIREN) number"),

    /**
     * An identification number known as a SIRET assigned to a business location in France.
     */
    ARL("ARL", "Systeme Informatique pour le Repertoire des ETablissements (SIRET) number"),

    /**
     * A number assigned to identify a publication issue.
     */
    ARM("ARM", "Publication issue number"),

    /**
     * A number assigned to the original filing.
     */
    ARN("ARN", "Original filing number"),

    /**
     * [1212] To identify a page number.
     */
    ARO("ARO", "Document page identifier"),

    /**
     * A number assigned at the time of registration of a public filing.
     */
    ARP("ARP", "Public filing registration number"),

    /**
     * A federal tax identification number assigned by the Mexican tax authority.
     */
    ARQ("ARQ", "Regiristo Federal de Contribuyentes"),

    /**
     * An identification number assigned to an individual by the social security administration.
     */
    ARR("ARR", "Social security number"),

    /**
     * The number of a document volume.
     */
    ARS("ARS", "Document volume number"),

    /**
     * A number assigned to identify a book.
     */
    ART("ART", "Book number"),

    /**
     * A reference assigned by the stock exchange to a company.
     */
    ARU("ARU", "Stock exchange company identifier"),

    /**
     * An account to which an amount is to be posted.
     */
    ARV("ARV", "Imputation account"),

    /**
     * A reference which identifies a specific financial phase.
     */
    ARW("ARW", "Financial phase reference"),

    /**
     * A reference which identifies a specific technical phase.
     */
    ARX("ARX", "Technical phase reference"),

    /**
     * A previous reference number used to identify a contractor.
     */
    ARY("ARY", "Prior contractor registration number"),

    /**
     * A number identifying a stock adjustment.
     */
    ARZ("ARZ", "Stock adjustment number"),

    /**
     * A reference number assigned to an official exemption from a law or obligation.
     */
    ASA("ASA", "Dispensation reference"),

    /**
     * A reference to a specific investment.
     */
    ASB("ASB", "Investment reference number"),

    /**
     * A number that identifies an assuming company.
     */
    ASC("ASC", "Assuming company"),

    /**
     * A reference to the chapter in a budget.
     */
    ASD("ASD", "Budget chapter"),

    /**
     * A security number allocated for duty free products.
     */
    ASE("ASE", "Duty free products security number"),

    /**
     * Authorisation number allocated for the receipt of duty free products.
     */
    ASF("ASF", "Duty free products receipt authorisation number"),

    /**
     * Reference identifying a party information message.
     */
    ASG("ASG", "Party information message reference"),

    /**
     * A reference to a formal statement.
     */
    ASH("ASH", "Formal statement reference"),

    /**
     * A reference number identifying a proof of delivery which is generated by the goods recipient.
     */
    ASI("ASI", "Proof of delivery reference number"),

    /**
     * A reference number identifying a supplier's credit claim.
     */
    ASJ("ASJ", "Supplier's credit claim reference number"),

    /**
     * Reference identifying the picture of an actual product.
     */
    ASK("ASK", "Picture of actual product"),

    /**
     * Reference identifying a picture of a generic product.
     */
    ASL("ASL", "Picture of a generic product"),

    /**
     * Code specifying an identification assigned to an entity with whom one conducts trade.
     */
    ASM("ASM", "Trading partner identification number"),

    /**
     * Code specifying an identification number previously assigned to a trading partner.
     */
    ASN("ASN", "Prior trading partner identification number"),

    /**
     * Code used for authentication purposes.
     */
    ASO("ASO", "Password"),

    /**
     * A number uniquely identifying a formal report.
     */
    ASP("ASP", "Formal report number"),

    /**
     * Account number of fund.
     */
    ASQ("ASQ", "Fund account number"),

    /**
     * The number of a file or portfolio kept for safe custody on behalf of clients.
     */
    ASR("ASR", "Safe custody number"),

    /**
     * A reference number identifying a master account.
     */
    ASS("ASS", "Master account number"),

    /**
     * The reference number identifying a group.
     */
    AST("AST", "Group reference number"),

    /**
     * A number used to identify the transmission of an accounting book entry.
     */
    ASU("ASU", "Accounting transmission number"),

    /**
     * The number of a product data file.
     */
    ASV("ASV", "Product data file number"),

    /**
     * Brazilian taxpayer number.
     */
    ASW("ASW", "Cadastro Geral do Contribuinte (CGC)"),

    /**
     * Number assigned by a government agency to identify a foreign resident.
     */
    ASX("ASX", "Foreign resident identification number"),

    /**
     * Identity number of the Compact Disk Read Only Memory (CD-ROM).
     */
    ASY("ASY", "CD-ROM"),

    /**
     * Identifies the physical medium.
     */
    ASZ("ASZ", "Physical medium"),

    /**
     * Reference number of a financial cancellation.
     */
    ATA("ATA", "Financial cancellation reference number"),

    /**
     * A number assigned by a Customs authority allowing the purchase of goods free of tax
     * because they are to be exported immediately after the purchase.
     */
    ATB("ATB", "Purchase for export Customs agreement number"),

    /**
     * A reference number identifying the legal decision.
     */
    ATC("ATC", "Judgment number"),

    /**
     * A reference number identifying a secretariat.
     */
    ATD("ATD", "Secretariat number"),

    /**
     * Message reference number of the previous banking status message being responded to.
     */
    ATE("ATE", "Previous banking status message reference"),

    /**
     * Reference number of the latest received banking status message.
     */
    ATF("ATF", "Last received banking status message reference"),

    /**
     * Reference allocated by the bank to a documentary procedure.
     */
    ATG("ATG", "Bank's documentary procedure reference"),

    /**
     * Reference allocated by a customer to a documentary procedure.
     */
    ATH("ATH", "Customer's documentary procedure reference"),

    /**
     * Number of the safe deposit box.
     */
    ATI("ATI", "Safe deposit box number"),

    /**
     * Number of the receiving Bankgiro.
     */
    ATJ("ATJ", "Receiving Bankgiro number"),

    /**
     * Number of the sending Bankgiro.
     */
    ATK("ATK", "Sending Bankgiro number"),

    /**
     * Reference of the Bankgiro.
     */
    ATL("ATL", "Bankgiro reference"),

    /**
     * Number of a guarantee.
     */
    ATM("ATM", "Guarantee number"),

    /**
     * To identify the number of an instrument used to remit funds to a beneficiary.
     */
    ATN("ATN", "Collection instrument number"),

    /**
     * To identify the reference number of a giro payment having been converted to a Postgiro
     * account.
     */
    ATO("ATO", "Converted Postgiro number"),

    /**
     * Number used in the financial management process to align cost allocations.
     */
    ATP("ATP", "Cost centre alignment number"),

    /**
     * An identification number assigned by the Dutch Chamber of Commerce to a business in
     * the Netherlands.
     */
    ATQ("ATQ", "Kamer Van Koophandel (KVK) number"),

    /**
     * An identification number assigned by the Luxembourg National Bank to a business in Luxembourg.
     */
    ATR("ATR", "Institut Belgo-Luxembourgeois de Codification (IBLC) number"),

    /**
     * A reference identifying an external object.
     */
    ATS("ATS", "External object reference"),

    /**
     * Authorisation number for exceptional transport (using specific equipment, out of gauge,
     * materials and/or specific routing).
     */
    ATT("ATT", "Exceptional transport authorisation number"),

    /**
     * Tax identification number in Argentina.
     */
    ATU("ATU", "Clave Unica de Identificacion Tributaria (CUIT)"),

    /**
     * Tax identification number in Chile.
     */
    ATV("ATV", "Registro Unico Tributario (RUT)"),

    /**
     * Reference number assigned to a bundle of flat rack containers.
     */
    ATW("ATW", "Flat rack container bundle identification number"),

    /**
     * Reference number assigned to an order to accept transport equipment that is to be delivered
     * by an inland carrier to a specified facility.
     */
    ATX("ATX", "Transport equipment acceptance order reference"),

    /**
     * Reference number assigned to an order to release transport equipment which is to be
     * picked up by an inland carrier from a specified facility.
     */
    ATY("ATY", "Transport equipment release order reference"),

    /**
     * (1099) Reference number assigned by a port authority to the stay of a vessel in the
     * port.
     */
    ATZ("ATZ", "Ship's stay reference number"),

    /**
     * A number assigned by a requestor to an offer incoming following request for quote.
     */
    AU("AU", "Authorization to meet competition number"),

    /**
     * Identifies the reference pertaining to the place of positioning.
     */
    AUA("AUA", "Place of positioning reference"),

    /**
     * The reference to a party.
     */
    AUB("AUB", "Party reference"),

    /**
     * The identification of the issued prescription.
     */
    AUC("AUC", "Issued prescription identification"),

    /**
     * A reference identifying a collection.
     */
    AUD("AUD", "Collection reference"),

    /**
     * Reference identifying a travel service.
     */
    AUE("AUE", "Travel service"),

    /**
     * Reference identifying a consignment stock contract.
     */
    AUF("AUF", "Consignment stock contract"),

    /**
     * Letter of credit reference issued by importer.
     */
    AUG("AUG", "Importer's letter of credit reference"),

    /**
     * The identification of the prescription that has been carried into effect.
     */
    AUH("AUH", "Performed prescription identification"),

    /**
     * A reference number identifying an image.
     */
    AUI("AUI", "Image reference"),

    /**
     * A reference number assigned to a proposed purchase order.
     */
    AUJ("AUJ", "Proposed purchase order reference number"),

    /**
     * Reference number assigned to an application for financial support.
     */
    AUK("AUK", "Application for financial support reference number"),

    /**
     * Reference number of a manufacturing quality agreement.
     */
    AUL("AUL", "Manufacturing quality agreement number"),

    /**
     * Reference identifying the software editor.
     */
    AUM("AUM", "Software editor reference"),

    /**
     * Reference identifying the software.
     */
    AUN("AUN", "Software reference"),

    /**
     * Reference allocated to the software by a quality assurance agency.
     */
    AUO("AUO", "Software quality reference"),

    /**
     * A reference number to identify orders which have been, or shall be consolidated.
     */
    AUP("AUP", "Consolidated orders' reference"),

    /**
     * Binding ruling number issued by customs.
     */
    AUQ("AUQ", "Customs binding ruling number"),

    /**
     * Non-binding ruling number issued by customs.
     */
    AUR("AUR", "Customs non-binding ruling number"),

    /**
     * A reference to the route of the delivery.
     */
    AUS("AUS", "Delivery route reference"),

    /**
     * A reference identifying a supplier within a net area.
     */
    AUT("AUT", "Net area supplier reference"),

    /**
     * Reference to a time series.
     */
    AUU("AUU", "Time series reference"),

    /**
     * Reference to a connecting point to a central grid.
     */
    AUV("AUV", "Connecting point to central grid"),

    /**
     * Number identifying a marketing plan.
     */
    AUW("AUW", "Marketing plan identification number (MPIN)"),

    /**
     * The previous reference number assigned to an entity.
     */
    AUX("AUX", "Entity reference number, previous"),

    /**
     * A code specifying an international standard industrial classification.
     */
    AUY("AUY", "International Standard Industrial Classification (ISIC) code"),

    /**
     * Pre-approval ruling number issued by Customs.
     */
    AUZ("AUZ", "Customs pre-approval ruling number"),

    /**
     * Reference number assigned by accounts payable department to the account of a specific
     * creditor.
     */
    AV("AV", "Account payable number"),

    /**
     * Identifies the reference given to the individual transaction by the financial institution
     * that is the transaction's point of entry into the interbank transaction chain.
     */
    AVA("AVA", "First financial institution's transaction reference"),

    /**
     * A reference to a product characteristics directory.
     */
    AVB("AVB", "Product characteristics directory"),

    /**
     * A number, assigned by a supplier, to reference a customer.
     */
    AVC("AVC", "Supplier's customer reference number"),

    /**
     * Reference number assigned to a request for an inventory report.
     */
    AVD("AVD", "Inventory report request number"),

    /**
     * Reference to a metering point.
     */
    AVE("AVE", "Metering point"),

    /**
     * Number assigned by the travel supplier to identify the passenger reservation.
     */
    AVF("AVF", "Passenger reservation number"),

    /**
     * Veterinary licence number allocated by a national authority to a slaughterhouse.
     */
    AVG("AVG", "Slaughterhouse approval number"),

    /**
     * Veterinary licence number allocated by a national authority to a meat cutting plant.
     */
    AVH("AVH", "Meat cutting plant approval number"),

    /**
     * A reference identifying a travel service to a customer.
     */
    AVI("AVI", "Customer travel service identifier"),

    /**
     * Number identifying the classification of goods covered by an export licence.
     */
    AVJ("AVJ", "Export control classification number"),

    /**
     * Third reference of a broker.
     */
    AVK("AVK", "Broker reference 3"),

    /**
     * Code identifying that the reference number given applies to the consignment information
     * segment group in the referred message .
     */
    AVL("AVL", "Consignment information"),

    /**
     * Code identifying that the reference number given applies to the goods item information
     * segment group in the referred message.
     */
    AVM("AVM", "Goods item information"),

    /**
     * Code identifying that the reference number given applies to the dangerous goods information
     * segment group in the referred message.
     */
    AVN("AVN", "Dangerous Goods information"),

    /**
     * Number identifying the permit to not use pilotage services.
     */
    AVO("AVO", "Pilotage services exemption number"),

    /**
     * A number assigned to an individual.
     */
    AVP("AVP", "Person registration number"),

    /**
     * Approval Number of the place where goods are packaged.
     */
    AVQ("AVQ", "Place of packing approval number"),

    /**
     * Reference to a specific related original mandate given by the relevant party for underlying
     * business or action in case of reference or mandate change.
     */
    AVR("AVR", "Original Mandate Reference"),

    /**
     * Reference to a specific mandate given by the relevant party for underlying business
     * or action.
     */
    AVS("AVS", "Mandate Reference"),

    /**
     * Reference to the station where a reservation was made.
     */
    AVT("AVT", "Reservation station indentifier"),

    /**
     * Unique identifier assigned to a shipment of goods linking trade, tracking and transport
     * information.
     */
    AVU("AVU", "Unique goods shipment identifier"),

    /**
     * A reference to an agreement between one or more contracting authorities and one or more
     * economic operators, the purpose of which is to establish the terms governing contracts
     * to be awarded during a given period, in particular with regard to price and, where appropriate,
     * the quantity envisaged.
     */
    AVV("AVV", "Framework Agreement Number"),

    /**
     * Contains the hash value of a related document.
     */
    AVW("AVW", "Hash value"),

    /**
     * Number assigned by customs referencing receipt of an Entry Summary Declaration.
     */
    AVX("AVX", "Movement reference number"),

    /**
     * Number assigned by an authority to an economic operator.
     */
    AVY("AVY", "Economic Operators Registration and Identification Number (EORI)"),

    /**
     * Number assigned by a national customs authority to an Entry Summary Declaration.
     */
    AVZ("AVZ", "Local Reference Number"),

    /**
     * Number assigned by a buyer to rate a product.
     */
    AWA("AWA", "Rate code number"),

    /**
     * Reference number assigned to an air waybill, see: 1001 = 740.
     */
    AWB("AWB", "Air waybill number"),

    /**
     * Number of the amendment of the documentary credit.
     */
    AWC("AWC", "Documentary credit amendment number"),

    /**
     * Reference number of the advising bank.
     */
    AWD("AWD", "Advising bank's reference"),

    /**
     * A number identifying a cost centre.
     */
    AWE("AWE", "Cost centre"),

    /**
     * A reference assigned to a work item quantity determination.
     */
    AWF("AWF", "Work item quantity determination"),

    /**
     * A number identifying an internal data process.
     */
    AWG("AWG", "Internal data process number"),

    /**
     * A reference identifying a category of work.
     */
    AWH("AWH", "Category of work reference"),

    /**
     * Number assigned to a policy form.
     */
    AWI("AWI", "Policy form number"),

    /**
     * Reference to an area of a net.
     */
    AWJ("AWJ", "Net area"),

    /**
     * Reference of the service provider.
     */
    AWK("AWK", "Service provider"),

    /**
     * Reference to the position of an error in a message.
     */
    AWL("AWL", "Error position"),

    /**
     * Reference identifying the service category.
     */
    AWM("AWM", "Service category reference"),

    /**
     * Reference of a connected location.
     */
    AWN("AWN", "Connected location"),

    /**
     * Reference of a related party.
     */
    AWO("AWO", "Related party"),

    /**
     * Code identifying the reference of the latest accounting entry record.
     */
    AWP("AWP", "Latest accounting entry record reference"),

    /**
     * Accounting entry to which this item is related.
     */
    AWQ("AWQ", "Accounting entry"),

    /**
     * The original reference of a document.
     */
    AWR("AWR", "Document reference, original"),

    /**
     * Nationally set Hygienic Certificate number, such as sanitary, epidemiologic.
     */
    AWS("AWS", "Hygienic Certificate number, national"),

    /**
     * Reference number assigned by Customs to a ‘shipment of excise goods’.
     */
    AWT("AWT", "Administrative Reference Code"),

    /**
     * Reference number assigned to a pick-up sheet.
     */
    AWU("AWU", "Pick-up sheet number"),

    /**
     * A sequence of digits used to call from one telephone line to another in a public telephone
     * network.
     */
    AWV("AWV", "Phone number"),

    /**
     * A reference number indicating the fund number used by the buyer.
     */
    AWW("AWW", "Buyer's fund number"),

    /**
     * A reference number identifying a company trading account.
     */
    AWX("AWX", "Company trading account number"),

    /**
     * A reference number identifying goods in stock which have been reserved for a party.
     */
    AWY("AWY", "Reserved goods identifier"),

    /**
     * A reference number identifying a previously transmitted cargo/goods handling and movement
     * message.
     */
    AWZ("AWZ", "Handling and movement reference number"),

    /**
     * A reference number identifying a previously transmitted instruction to despatch message.
     */
    AXA("AXA", "Instruction to despatch reference number"),

    /**
     * A reference number identifying a previously communicated instruction for return message.
     */
    AXB("AXB", "Instruction for returns number"),

    /**
     * A reference number identifying a previously communicated metered services consumption
     * report.
     */
    AXC("AXC", "Metered services consumption report number"),

    /**
     * A reference number to a previously sent order status enquiry.
     */
    AXD("AXD", "Order status enquiry number"),

    /**
     * A reference number identifying a previous firm booking.
     */
    AXE("AXE", "Firm booking reference number"),

    /**
     * A reference number identifying a previously communicated product inquiry.
     */
    AXF("AXF", "Product inquiry number"),

    /**
     * A reference number identifying a split delivery.
     */
    AXG("AXG", "Split delivery number"),

    /**
     * A reference number identifying the relationship between a service provider and a service
     * client, e.g., treatment of a patient in a hospital, usage by a member of a library facility,
     * etc.
     */
    AXH("AXH", "Service relation number"),

    /**
     * Reference number identifying a logistic unit.
     */
    AXI("AXI", "Serial shipping container code"),

    /**
     * A reference number identifying a test specification.
     */
    AXJ("AXJ", "Test specification number"),

    /**
     * [1125] A reference number identifying a transport status report.
     */
    AXK("AXK", "Transport status report number"),

    /**
     * A reference number of the tooling contract.
     */
    AXL("AXL", "Tooling contract number"),

    /**
     * The reference number which identifies a formula.
     */
    AXM("AXM", "Formula reference number"),

    /**
     * A reference number identifying a pre-agreement.
     */
    AXN("AXN", "Pre-agreement number"),

    /**
     * Number assigned by a governing body (or their agents) to a product which certifies compliance
     * with a standard.
     */
    AXO("AXO", "Product certification number"),

    /**
     * Reference number identifying a consignment contract.
     */
    AXP("AXP", "Consignment contract number"),

    /**
     * Number assigned by the issuer to his product specification.
     */
    AXQ("AXQ", "Product specification reference number"),

    /**
     * A reference number identifying a payroll deduction advice.
     */
    AXR("AXR", "Payroll deduction advice reference"),

    /**
     * The party identification number used in the European Union's Trade Control and Expert
     * System (TRACES).
     */
    AXS("AXS", "TRACES party identification"),

    /**
     * Meter reading at the beginning of an invoicing period.
     */
    BA("BA", "Beginning meter reading actual"),

    /**
     * Reference number assigned by buyer to a contract.
     */
    BC("BC", "Buyer's contract number"),

    /**
     * Number assigned by a submitter of a bid to his bid.
     */
    BD("BD", "Bid number"),

    /**
     * Meter reading at the beginning of an invoicing period where an actual reading is not
     * available.
     */
    BE("BE", "Beginning meter reading estimated"),

    /**
     * [1039] Reference number assigned to a house bill of lading.
     */
    BH("BH", "House bill of lading number"),

    /**
     * Reference number assigned to a bill of lading, see: 1001 = 705.
     */
    BM("BM", "Bill of lading number"),

    /**
     * [1016] Reference number assigned by a carrier of its agent to identify a specific consignment
     * such as a booking reference number when cargo space is reserved prior to loading.
     */
    BN("BN", "Consignment identifier, carrier assigned"),

    /**
     * Reference number assigned by the order issuer to a blanket order.
     */
    BO("BO", "Blanket order number"),

    /**
     * A number that identifies a broker or sales office.
     */
    BR("BR", "Broker or sales office number"),

    /**
     * [7338] Reference number assigned by manufacturer to a series of similar products or
     * goods produced under similar conditions.
     */
    BT("BT", "Batch number/lot number"),

    /**
     * Registration number of producer of batteries and accumulators.
     */
    BTP("BTP", "Battery and accumulator producer registration number"),

    /**
     * The batch/lot/package number a product is blended with.
     */
    BW("BW", "Blended with number"),

    /**
     * Code issued by IATA to identify agent locations for CASS billing purposes.
     */
    CAS("CAS", "IATA Cargo Agent CASS Address number"),

    /**
     * Reference to a balanced matching of entries.
     */
    CAT("CAT", "Matching of entries, balanced"),

    /**
     * Reference to a flagging of entries.
     */
    CAU("CAU", "Entry flagging"),

    /**
     * Reference to an unbalanced matching of entries.
     */
    CAV("CAV", "Matching of entries, unbalanced"),

    /**
     * Internal reference to a document.
     */
    CAW("CAW", "Document reference, internal"),

    /**
     * Value Added Tax identification number according to European regulation.
     */
    CAX("CAX", "European Value Added Tax identification"),

    /**
     * The reference to a cost accounting document.
     */
    CAY("CAY", "Cost accounting document"),

    /**
     * A number, assigned by a grid operator, to reference a customer.
     */
    CAZ("CAZ", "Grid operator's customer reference number"),

    /**
     * Reference giving access to all the details associated with the ticket.
     */
    CBA("CBA", "Ticket control number"),

    /**
     * A reference number identifying the grouping of purchase orders into one shipment.
     */
    CBB("CBB", "Order shipment grouping reference"),

    /**
     * [1113] Reference number assigned to a credit note.
     */
    CD("CD", "Credit note number"),

    /**
     * Company selling obligations to a third party.
     */
    CEC("CEC", "Ceding company"),

    /**
     * Reference number identifying the letter of debit document.
     */
    CED("CED", "Debit letter number"),

    /**
     * Reference of an order given by the consignee after departure of the means of transport.
     */
    CFE("CFE", "Consignee's further order"),

    /**
     * Veterinary licence number allocated by a national authority to an animal farm.
     */
    CFF("CFF", "Animal farm licence number"),

    /**
     * Reference of an order given by the consignor after departure of the means of transport.
     */
    CFO("CFO", "Consignor's further order"),

    /**
     * A number that identifies a consignee's order.
     */
    CG("CG", "Consignee's order number"),

    /**
     * Number identifying a catalogue for customer's usage.
     */
    CH("CH", "Customer catalogue number"),

    /**
     * Unique number assigned to one specific cheque.
     */
    CK("CK", "Cheque number"),

    /**
     * Number assigned by checking party to one specific check action.
     */
    CKN("CKN", "Checking number"),

    /**
     * Reference number assigned by issuer to a credit memo.
     */
    CM("CM", "Credit memo number"),

    /**
     * Reference number assigned to a road consignment note, see: 1001 = 730.
     */
    CMR("CMR", "Road consignment note number"),

    /**
     * Reference number assigned by carrier to a consignment.
     */
    CN("CN", "Carrier's reference number"),

    /**
     * [1070] Indication that a charges note has been established and attached to a transport
     * contract document or not.
     */
    CNO("CNO", "Charges note document attachment indicator"),

    /**
     * A number that identifies a call off order.
     */
    COF("COF", "Call off order number"),

    /**
     * Reference number identifying the conditions of purchase relevant to a purchase.
     */
    CP("CP", "Condition of purchase document number"),

    /**
     * Reference number assigned by the customer to a transaction.
     */
    CR("CR", "Customer reference number"),

    /**
     * [8028] To identify a journey of a means of transport, for example voyage number, flight
     * number, trip number.
     */
    CRN("CRN", "Transport means journey identifier"),

    /**
     * Reference number identifying the conditions of sale relevant to a sale.
     */
    CS("CS", "Condition of sale document number"),

    /**
     * Team number assigned to a group that is responsible for working a particular transaction.
     */
    CST("CST", "Team assignment number"),

    /**
     * [1296] Reference number of a contract concluded between parties.
     */
    CT("CT", "Contract number"),

    /**
     * [1140] Reference number assigned by the consignor to identify a particular consignment.
     */
    CU("CU", "Consignment identifier, consignor assigned"),

    /**
     * Reference number assigned by the party operating or controlling the transport container
     * to a transaction or consignment.
     */
    CV("CV", "Container operators reference number"),

    /**
     * (7070) Reference number identifying a package or carton within a consignment.
     */
    CW("CW", "Package number"),

    /**
     * Number issued by a party concerned given to a contract on cooperation of two or more
     * parties.
     */
    CZ("CZ", "Cooperation contract number"),

    /**
     * Number assigned by authorities to a party to approve deferment of payment of tax or
     * duties.
     */
    DA("DA", "Deferment approval number"),

    /**
     * Reference number assigned by issuer to a debit account.
     */
    DAN("DAN", "Debit account number"),

    /**
     * Reference number assigned to a debtor.
     */
    DB("DB", "Buyer's debtor number"),

    /**
     * Reference number assigned by issuer to a distributor invoice.
     */
    DI("DI", "Distributor invoice number"),

    /**
     * [1117] Reference number assigned by issuer to a debit note.
     */
    DL("DL", "Debit note number"),

    /**
     * [1004] Reference number identifying a specific document.
     */
    DM("DM", "Document identifier"),

    /**
     * [1033] Reference number assigned by the issuer to a delivery note.
     */
    DQ("DQ", "Delivery note number"),

    /**
     * Number of the cargo receipt submitted when cargo is delivered to a marine terminal.
     */
    DR("DR", "Dock receipt number"),

    /**
     * Meter reading at the end of an invoicing period.
     */
    EA("EA", "Ending meter reading actual"),

    /**
     * Reference number assigned by issuer to an embargo permit.
     */
    EB("EB", "Embargo permit number"),

    /**
     * Number assigned by the exporter to his export declaration number submitted to an authority.
     */
    ED("ED", "Export declaration"),

    /**
     * Meter reading at the end of an invoicing period where an actual reading is not available.
     */
    EE("EE", "Ending meter reading estimated"),

    /**
     * Registration number of producer of electrical and electronic equipment.
     */
    EEP("EEP", "Electrical and electronic equipment producer registration number"),

    /**
     * Number issued by an authority to identify an employer.
     */
    EI("EI", "Employer's identification number"),

    /**
     * Number assigned to specific goods or a family of goods in a classification of embargo
     * measures.
     */
    EN("EN", "Embargo number"),

    /**
     * Number assigned by the manufacturer to specific equipment.
     */
    EQ("EQ", "Equipment number"),

    /**
     * Number of the Equipment Interchange Receipt issued for full or empty equipment received.
     */
    ER("ER", "Container/equipment receipt number"),

    /**
     * Reference to a party exporting goods.
     */
    ERN("ERN", "Exporter's reference number"),

    /**
     * (1041) Number assigned to excess transport.
     */
    ET("ET", "Excess transportation number"),

    /**
     * [1208] Reference number to identify an export licence or permit.
     */
    EX("EX", "Export permit identifier"),

    /**
     * Tax payer's number. Number assigned to individual persons as well as to corporates by
     * a public institution; this number is different from the VAT registration number.
     */
    FC("FC", "Fiscal number"),

    /**
     * [1460] Reference number assigned by the freight forwarder to identify a particular consignment.
     */
    FF("FF", "Consignment identifier, freight forwarder assigned"),

    /**
     * Number assigned by the file issuer or sender to identify a specific line.
     */
    FI("FI", "File line identifier"),

    /**
     * Number given to a usual sender which has regular expeditions of the same goods, to the
     * same destination, defining all general conditions of the transport.
     */
    FLW("FLW", "Flow reference number"),

    /**
     * Reference number assigned by issuing party to a freight bill.
     */
    FN("FN", "Freight bill number"),

    /**
     * Exchange of two currencies at an agreed rate.
     */
    FO("FO", "Foreign exchange"),

    /**
     * A number that identifies the final sequence.
     */
    FS("FS", "Final sequence number"),

    /**
     * Identifier to specify the territory of a State where any goods introduced are generally
     * regarded, insofar as import duties and taxes are concerned, as being outside the Customs
     * territory and are not subject to usual Customs control (CCC).
     */
    FT("FT", "Free zone identifier"),

    /**
     * Number given to a version of an identified file.
     */
    FV("FV", "File version number"),

    /**
     * Reference number identifying a foreign exchange contract.
     */
    FX("FX", "Foreign exchange contract number"),

    /**
     * Number to identify a standardization description (e.g. ISO 9375).
     */
    GA("GA", "Standard's number"),

    /**
     * Number assigned to a specific government/public contract.
     */
    GC("GC", "Government contract number"),

    /**
     * Number to identify a specific parameter within a standardization description (e.g. M5
     * for screws or DIN A4 for paper).
     */
    GD("GD", "Standard's code number"),

    /**
     * Number of the declaration of incoming goods out of a vessel.
     */
    GDN("GDN", "General declaration number"),

    /**
     * A number that identifies a government reference.
     */
    GN("GN", "Government reference number"),

    /**
     * Number specifying the goods classification under the Harmonised Commodity Description
     * and Coding System of the Customs Co-operation Council (CCC).
     */
    HS("HS", "Harmonised system number"),

    /**
     * Reference number assigned to a house waybill, see: 1001 = 703.
     */
    HWB("HWB", "House waybill number"),

    /**
     * Number identifying the company-internal vending department/unit.
     */
    IA("IA", "Internal vendor number"),

    /**
     * Customs assigned number that is used to control the movement of imported cargo prior
     * to its formal Customs clearing.
     */
    IB("IB", "In bond number"),

    /**
     * Code issued by IATA identify each IATA Cargo Agent whose name is entered on the Cargo
     * Agency List.
     */
    ICA("ICA", "IATA cargo agent code number"),

    /**
     * A number that identifies an insurance certificate reference.
     */
    ICE("ICE", "Insurance certificate reference number"),

    /**
     * A number that identifies an insurance contract reference.
     */
    ICO("ICO", "Insurance contract reference number"),

    /**
     * Inspection report number given to the initial sample inspection.
     */
    II("II", "Initial sample inspection report number"),

    /**
     * Number assigned to an order for internal handling/follow up.
     */
    IL("IL", "Internal order number"),

    /**
     * A number that identifies an intermediary broker.
     */
    INB("INB", "Intermediary broker"),

    /**
     * Number assigned by the interchange sender to identify one specific interchange. This
     * number points to the actual interchange.
     */
    INN("INN", "Interchange number new"),

    /**
     * Number assigned by the interchange sender to identify one specific interchange. This
     * number points to the previous interchange.
     */
    INO("INO", "Interchange number old"),

    /**
     * [1107] Reference number to identify an import licence or permit.
     */
    IP("IP", "Import permit identifier"),

    /**
     * A number added at the end of an invoice number.
     */
    IS("IS", "Invoice number suffix"),

    /**
     * Number assigned by a seller, supplier etc. to identify a customer within his enterprise.
     */
    IT("IT", "Internal customer number"),

    /**
     * [1334] Reference number to identify an invoice.
     */
    IV("IV", "Invoice document identifier"),

    /**
     * [1043] Identifies a piece of work.
     */
    JB("JB", "Job number"),

    /**
     * A number that identifies the ending job sequence.
     */
    JE("JE", "Ending job sequence number"),

    /**
     * The serial number on a shipping label.
     */
    LA("LA", "Shipping label serial number"),

    /**
     * [4092] Identifier assigned to the loading authorisation granted by the forwarding location
     * e.g. railway or airport, when the consignment is subject to traffic limitations.
     */
    LAN("LAN", "Loading authorisation identifier"),

    /**
     * Lower number in a range of numbers.
     */
    LAR("LAR", "Lower number in range"),

    /**
     * Type of cash management system offered by financial institutions to provide for collection
     * of customers 'receivables'.
     */
    LB("LB", "Lockbox"),

    /**
     * Reference number identifying the letter of credit document.
     */
    LC("LC", "Letter of credit number"),

    /**
     * [1156] To identify a line of a document.
     */
    LI("LI", "Document line identifier"),

    /**
     * The reference that identifies the load planning number.
     */
    LO("LO", "Load planning number"),

    /**
     * Reference to the office where a reservation was made.
     */
    LRC("LRC", "Reservation office identifier"),

    /**
     * The serial number on a bar code label.
     */
    LS("LS", "Bar coded label serial number"),

    /**
     * The number assigned to a ship notice or manifest.
     */
    MA("MA", "Ship notice/manifest number"),

    /**
     * Reference number assigned to a master bill of lading, see: 1001 = 704.
     */
    MB("MB", "Master bill of lading number"),

    /**
     * Reference number assigned by the manufacturer to his product or part.
     */
    MF("MF", "Manufacturer's part number"),

    /**
     * Number identifying a unique meter unit.
     */
    MG("MG", "Meter unit number"),

    /**
     * Reference number assigned by manufacturer for a given production quantity of products.
     */
    MH("MH", "Manufacturing order number"),

    /**
     * A number that identifies the message recipient.
     */
    MR("MR", "Message recipient"),

    /**
     * Identifies the party designated by the importer to receive certain customs correspondence
     * in lieu of its being mailed directly to the importer.
     */
    MRN("MRN", "Mailing reference number"),

    /**
     * A number that identifies the message sender.
     */
    MS("MS", "Message sender"),

    /**
     * A number that identifies a manufacturer's material safety data sheet.
     */
    MSS("MSS", "Manufacturer's material safety data sheet number"),

    /**
     * Reference number assigned to a master air waybill, see: 1001 = 741.
     */
    MWB("MWB", "Master air waybill number"),

    /**
     * Reference to materials designated as hazardous for purposes of transportation in North
     * American commerce.
     */
    NA("NA", "North American hazardous goods classification number"),

    /**
     * Nota Fiscal is a registration number for shipments / deliveries within Brazil, issued
     * by the local tax authorities and mandated for each shipment.
     */
    NF("NF", "Nota Fiscal"),

    /**
     * Reference number identifying the current invoice.
     */
    OH("OH", "Current invoice number"),

    /**
     * Reference number identifying a previously issued invoice.
     */
    OI("OI", "Previous invoice number"),

    /**
     * [1022] Identifier assigned by the buyer to an order.
     */
    ON("ON", "Order document identifier, buyer assigned"),

    /**
     * Reference to the order previously sent.
     */
    OP("OP", "Original purchase order"),

    /**
     * Customs number assigned to imported merchandise that has been left unclaimed and subsequently
     * moved to a Customs bonded warehouse for storage.
     */
    OR("OR", "General order number"),

    /**
     * Originated company account number (ACH transfer), check, draft or wire.
     */
    PB("PB", "Payer's financial institution account number"),

    /**
     * Number assigned by the manufacturer to a specified article or batch to identify the
     * manufacturing date etc. for subsequent reference.
     */
    PC("PC", "Production code"),

    /**
     * Number assigned by a vendor to a special promotion activity.
     */
    PD("PD", "Promotion deal number"),

    /**
     * A number that identifies a plant.
     */
    PE("PE", "Plant number"),

    /**
     * Reference number assigned by the client to the contract of the prime contractor.
     */
    PF("PF", "Prime contractor contract number"),

    /**
     * A number that identifies the version of a price list.
     */
    PI("PI", "Price list version number"),

    /**
     * [1014] Reference number assigned to a packing list.
     */
    PK("PK", "Packing list number"),

    /**
     * Reference number assigned to a price list.
     */
    PL("PL", "Price list number"),

    /**
     * Reference number assigned by the seller to an order response.
     */
    POR("POR", "Purchase order response number"),

    /**
     * Reference number assigned by a buyer for a revision of a purchase order.
     */
    PP("PP", "Purchase order change number"),

    /**
     * Reference number assigned to a payment.
     */
    PQ("PQ", "Payment reference"),

    /**
     * Reference number assigned by the seller to a quote.
     */
    PR("PR", "Price quote number"),

    /**
     * A number added at the end of a purchase order number.
     */
    PS("PS", "Purchase order number suffix"),

    /**
     * Reference number of a purchase order previously sent to the supplier.
     */
    PW("PW", "Prior purchase order number"),

    /**
     * Receiving company account number (ACH transfer), check, draft or wire.
     */
    PY("PY", "Payee's financial institution account number"),

    /**
     * A number that identifies a remittance advice.
     */
    RA("RA", "Remittance advice number"),

    /**
     * International Western and Eastern European route code used in all rail organizations
     * and specified in the international tariffs (rail tariffs) known by the customers.
     */
    RC("RC", "Rail/road routing code"),

    /**
     * Reference number assigned to a rail consignment note, see: 1001 = 720.
     */
    RCN("RCN", "Railway consignment note number"),

    /**
     * Reference number assigned to identify a release of a set of rules, conventions, conditions,
     * etc.
     */
    RE("RE", "Release number"),

    /**
     * [1150] Reference number assigned to identify a consignment upon its arrival at its destination.
     */
    REN("REN", "Consignment receipt identifier"),

    /**
     * Reference number given to an export shipment.
     */
    RF("RF", "Export reference number"),

    /**
     * ODFI (ACH transfer).
     */
    RR("RR", "Payer's financial institution transit routing No.(ACH transfers)"),

    /**
     * RDFI Transit routing number (ACH transfer).
     */
    RT("RT", "Payee's financial institution transit routing No."),

    /**
     * Identification number of a sales person.
     */
    SA("SA", "Sales person number"),

    /**
     * A number that identifies a sales region.
     */
    SB("SB", "Sales region number"),

    /**
     * A number that identifies a sales department.
     */
    SD("SD", "Sales department number"),

    /**
     * Identification number of an item which distinguishes this specific item out of an number
     * of identical items.
     */
    SE("SE", "Serial number"),

    /**
     * Reference to a seat allocated to a passenger.
     */
    SEA("SEA", "Allocated seat"),

    /**
     * A number that identifies a ship from location.
     */
    SF("SF", "Ship from"),

    /**
     * Number of the latest schedule of a previous period (ODETTE DELINS).
     */
    SH("SH", "Previous highest schedule number"),

    /**
     * A number that identifies the SID (shipper's identification) number for a shipment.
     */
    SI("SI", "SID (Shipper's identifying number for shipment)"),

    /**
     * A number that identifies a sales office.
     */
    SM("SM", "Sales office number"),

    /**
     * [9308] The identification number of a seal affixed to a piece of transport equipment.
     */
    SN("SN", "Transport equipment seal identifier"),

    /**
     * A number that identifies a scan line.
     */
    SP("SP", "Scan line"),

    /**
     * (1492) A temporary reference number identifying a particular piece of equipment within
     * a series of pieces of equipment.
     */
    SQ("SQ", "Equipment sequence number"),

    /**
     * [1065] Reference number assigned to a shipment.
     */
    SRN("SRN", "Shipment reference number"),

    /**
     * Reference number assigned to a transaction by the seller.
     */
    SS("SS", "Sellers reference number"),

    /**
     * International UIC code assigned to every European rail station (CIM convention).
     */
    STA("STA", "Station reference number"),

    /**
     * Number assigned by the seller to a swap order (see definition of DE 1001, code 229).
     */
    SW("SW", "Swap order number"),

    /**
     * Number assigned by the issuer to his specification.
     */
    SZ("SZ", "Specification number"),

    /**
     * A cargo list/description issued by a motor carrier of freight.
     */
    TB("TB", "Trucker's bill of lading"),

    /**
     * Reference assigned to a consignment by the terminal operator.
     */
    TCR("TCR", "Terminal operator's consignment reference"),

    /**
     * Reference number identifying a telex message.
     */
    TE("TE", "Telex message number"),

    /**
     * An extra number assigned to goods or a container which functions as a reference number
     * or as an authorization number to get the goods or container released from a certain
     * party.
     */
    TF("TF", "Transfer number"),

    /**
     * Reference number assigned to a TIR carnet.
     */
    TI("TI", "TIR carnet number"),

    /**
     * Reference number identifying a transport instruction.
     */
    TIN("TIN", "Transport instruction number"),

    /**
     * Number assigned by the tax authorities to a party indicating its tax exemption authorization.
     * This number could relate to a specified business type, a specified local area or a class
     * of products.
     */
    TL("TL", "Tax exemption licence number"),

    /**
     * Reference applied to a transaction between two or more parties over a defined life cycle;
     * e.g. number applied by importer or broker to obtain release from Customs, may then used
     * to control declaration through final accounting (synonyms: declaration, entry number).
     */
    TN("TN", "Transaction reference number"),

    /**
     * Reference number identifying a test report document relevant to the product.
     */
    TP("TP", "Test report number"),

    /**
     * Upper number in a range of numbers.
     */
    UAR("UAR", "Upper number of range"),

    /**
     * The originator's reference number as forwarded in a sequence of parties involved.
     */
    UC("UC", "Ultimate customer's reference number"),

    /**
     * [1202] Unique reference identifying a particular consignment of goods. Synonym: UCR,
     * UCRN.
     */
    UCN("UCN", "Unique consignment reference number"),

    /**
     * [7124] United Nations Dangerous Goods Identifier (UNDG) is the unique serial number
     * assigned within the United Nations to substances and articles contained in a list of
     * the dangerous goods most commonly carried.
     */
    UN("UN", "United Nations Dangerous Goods identifier"),

    /**
     * The originator's order number as forwarded in a sequence of parties involved.
     */
    UO("UO", "Ultimate customer's order number"),

    /**
     * A string of characters used to identify a name of a resource on the worldwide web.
     */
    URI("URI", "Uniform Resource Identifier"),

    /**
     * Unique number assigned by the relevant tax authority to identify a party for use in
     * relation to Value Added Tax (VAT).
     */
    VA("VA", "VAT registration number"),

    /**
     * Number assigned by the vendor to a contract.
     */
    VC("VC", "Vendor contract number"),

    /**
     * Reference number identifying the documentation of a transport equipment gross mass (weight)
     * verification.
     */
    VGR("VGR", "Transport equipment gross mass verification reference number"),

    /**
     * (8123) Reference identifying a vessel.
     */
    VM("VM", "Vessel identifier"),

    /**
     * Reference number assigned by supplier to a buyer's purchase order.
     */
    VN("VN", "Order number (vendor)"),

    /**
     * (8028) Reference number assigned to the voyage of the vessel.
     */
    VON("VON", "Voyage number"),

    /**
     * Reference number identifying the order for obtaining a Verified Gross Mass (weight)
     * of a packed transport equipment as per SOLAS Chapter VI, Regulation 2, paragraphs 4-6.
     */
    VOR("VOR", "Transport equipment gross mass verification order reference"),

    /**
     * Number assigned by vendor to another manufacturer's product.
     */
    VP("VP", "Vendor product number"),

    /**
     * A number that identifies a vendor's identification.
     */
    VR("VR", "Vendor ID number"),

    /**
     * The suffix for a vendor order number.
     */
    VS("VS", "Vendor order number suffix"),

    /**
     * (8213) Reference identifying a motor vehicle used for transport. Normally is the vehicle
     * registration number.
     */
    VT("VT", "Motor vehicle identification number"),

    /**
     * Reference number identifying a voucher.
     */
    VV("VV", "Voucher number"),

    /**
     * Entry number under which imported merchandise was placed in a Customs bonded warehouse.
     */
    WE("WE", "Warehouse entry number"),

    /**
     * A number identifying a weight agreement.
     */
    WM("WM", "Weight agreement number"),

    /**
     * A number assigned to a shaft sunk into the ground.
     */
    WN("WN", "Well number"),

    /**
     * A number identifying a warehouse receipt.
     */
    WR("WR", "Warehouse receipt number"),

    /**
     * A number identifying a warehouse storage location.
     */
    WS("WS", "Warehouse storage location number"),

    /**
     * The number on a rail waybill.
     */
    WY("WY", "Rail waybill number"),

    /**
     * Company registration and place as legally required.
     */
    XA("XA", "Company/place registration number"),

    /**
     * Reference used to identify and control a carrier and consignment from initial entry
     * into a country until release of the cargo by Customs.
     */
    XC("XC", "Cargo control number"),

    /**
     * Where a consignment is deconsolidated and/or transferred to the control of another carrier
     * or freight forwarder (e.g. housebill, abstract) this references the previous (e.g. master)
     * cargo control number.
     */
    XP("XP", "Previous cargo control number"),

    /**
     * Number based on party agreement.
     */
    ZZZ("ZZZ", "Mutually defined reference number"),
    ;

    private final String name;
    private final String code;

    ReferenceCodeType(String code, String name) {
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
