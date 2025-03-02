package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.edi.adapters.PartyRoleCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * Based on UN/CEFACT 3035 : Party function code qualifier.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred3035.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@Documented(value = "Party Role Code", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.10", date = "2008-08-23")
@XmlJavaTypeAdapter(PartyRoleCodeAdapter.class)
public enum PartyRoleCodeType implements CodeList<PartyRoleCodeType> {

    /**
     * Party to be billed in accordance with AAR Accounting rule 11.
     */
    AA("AA", "Party to be billed (AAR Accounting rule 11)"),

    /**
     * Third party who arranged the purchase of merchandise on behalf of the actual buyer.
     */
    AB("AB", "Buyer's agent/representative"),

    /**
     * Any natural or legal person who makes a declaration to an official body on behalf of
     * another natural or legal person, where legally permitted (CCC).
     */
    AE("AE", "Declarant's agent/representative"),

    /**
     * Natural or legal person responsible for the satisfactory performance of a Customs transit
     * operation. Source: CCC.
     */
    AF("AF", "Transit principal"),

    /**
     * (3196) Party authorized to act on behalf of another party. Synonym: Representative.
     */
    AG("AG", "Agent"),

    /**
     * Agent acting on behalf of the transit principal (CCC).
     */
    AH("AH", "Transit principal's agent/representative"),

    /**
     * Person who has been chosen for a job.
     */
    AI("AI", "Successful job applicant"),

    /**
     * The party which has issued all mutually agreed codes used in the message.
     */
    AJ("AJ", "Party issuing mutually agreed codes"),

    /**
     * Party to whom acknowledgement should be sent.
     */
    AK("AK", "Acknowledgement recipient"),

    /**
     * (3340) Party accepting liability for goods held or moving (e.g. transit) under a Customs
     * authorization and
     * - when applicable - a guarantee.
     */
    AL("AL", "Principal responsible party"),

    /**
     * Employee of a company or firm authorized to act on behalf of that company or firm e.g.
     * to make a Customs declaration.
     */
    AM("AM", "Authorized official"),

    /**
     * Person or company which is authorised by the relevant Customs authority to import goods
     * without payment all taxes or specific taxes at the point of entry into the country.
     */
    AN("AN", "Approved importer"),

    /**
     * Party account is assigned to.
     */
    AO("AO", "Account of"),

    /**
     * (3336) Party accepting goods, products, services, etc.
     */
    AP("AP", "Accepting party"),

    /**
     * Person or company approved by the relevant authority in the country to pack and export
     * specific goods under Customs supervision.
     */
    AQ("AQ", "Approved consignor"),

    /**
     * Exporter authorized/approved by Customs for special Customs procedures e.g. simplified
     * procedure.
     */
    AR("AR", "Authorized exporter"),

    /**
     * Identifies the financial institution servicing the account(s).
     */
    AS("AS", "Account servicing financial institution"),

    /**
     * Importer authorized/approved by Customs for special Customs procedures e.g. simplified
     * procedure.
     */
    AT("AT", "Authorized importer"),

    /**
     * Trader authorized/approved by Customs for special transit procedures e.g. simplified
     * procedure.
     */
    AU("AU", "Authorized trader (transit)"),

    /**
     * Party that has delegated the authority to take a certain action on behalf of a company
     * or agency.
     */
    AV("AV", "Authorizing official"),

    /**
     * (3234) Financial institution which is requested to issue the documentary credit.
     */
    AW("AW", "Applicant's bank"),

    /**
     * Party which certifies that a document is authentic.
     */
    AX("AX", "Authenticating party"),

    /**
     * Animal being investigated.
     */
    AY("AY", "Animal being investigated"),

    /**
     * [3320] Financial institution which issues the documentary credit, if the applicant's
     * bank is not acting as the issuing bank.
     */
    AZ("AZ", "Issuing bank"),

    /**
     * Identifies an additional bank which must be informed of certain aspects of the message.
     */
    B1("B1", "Contact bank 1"),

    /**
     * Identifies an additional bank which must be informed of certain aspects of the message.
     */
    B2("B2", "Contact bank 2"),

    /**
     * Party acting as a booking office for transport and forwarding services.
     */
    BA("BA", "Booking agent"),

    /**
     * [3421]To identify a bank employed by the buyer to make a payment.
     */
    BB("BB", "Buyer bank identification"),

    /**
     * Financial institution to whom a negotiable documentary credit is directed.
     */
    BC("BC", "Negotiating bank"),

    /**
     * [3350] A financial institution which reimburses documentary credit.
     */
    BD("BD", "Documentary credit reimbursing bank"),

    /**
     * (3260) The ultimate recipient of the funds. Normally the account owner who is reimbursed
     * by the payer.
     */
    BE("BE", "Beneficiary"),

    /**
     * (3422) Identifies the account servicer for the beneficiary or the payee.
     */
    BF("BF", "Beneficiary's bank"),

    /**
     * A party that keeps a person in service for payment.
     */
    BG("BG", "Employer"),

    /**
     * Previous employer of a person(s).
     */
    BH("BH", "Previous employer"),

    /**
     * Financial institution designated by buyer to make payment.
     */
    BI("BI", "Buyer's financial institution"),

    /**
     * Party to which the goods or container(s) is (are) to be released.
     */
    BJ("BJ", "Release to party"),

    /**
     * Party acting as financial institution.
     */
    BK("BK", "Financial institution"),

    /**
     * Party to receive B/L.
     */
    BL("BL", "Bill of lading recipient"),

    /**
     * [3136] Party which is the object of an insurance contract.
     */
    BM("BM", "Insured"),

    /**
     * Party which benefits from insurance coverage.
     */
    BN("BN", "Insurance beneficiary"),

    /**
     * Party acting in the name of the seller as broker or as sales office.
     */
    BO("BO", "Broker or sales office"),

    /**
     * Party at the building site responsible for the purchasing of goods and services for
     * that particular site.
     */
    BP("BP", "Building site purchaser"),

    /**
     * Identifies the bank on which the cheque should be drawn, as instructed by the ordering
     * customer.
     */
    BQ("BQ", "Cheque drawn bank"),

    /**
     * Party receiving goods and relevant invoice.
     */
    BS("BS", "Bill and ship to"),

    /**
     * Party receiving invoice excluding freight costs.
     */
    BT("BT", "Party to be billed for other than freight (bill to)"),

    /**
     * Party carrying out service bureau processing work, (e.g. a payroll bureau).
     */
    BU("BU", "Service bureau"),

    /**
     * Member of a group (e.g. of a group of persons or a service scheme).
     */
    BV("BV", "Member"),

    /**
     * A person who acquires something temporarily with the promise or intention of returning.
     */
    BW("BW", "Borrower"),

    /**
     * Party at the building site responsible for engineering matters for that particular
     * site.
     */
    BX("BX", "Building site engineer"),

    /**
     * [3002] Party to which merchandise or services are sold.
     */
    BY("BY", "Buyer"),

    /**
     * Party at the building site responsible for forwarding the received goods on that particular
     * site.
     */
    BZ("BZ", "Building site forwarder"),

    /**
     * A person taking responsibility on behalf of party no. 1.
     */
    C1("C1", "In care of party no. 1"),

    /**
     * A person taking responsibility on behalf of party no. 2.
     */
    C2("C2", "In care of party no. 2"),

    /**
     * [3126] Party undertaking or arranging transport of goods between named points.
     */
    CA("CA", "Carrier"),

    /**
     * Agent or representative or a professional Customs clearing agent who deals directly
     * with Customs on behalf of the importer or exporter (CCC).
     */
    CB("CB", "Customs broker"),

    /**
     * Party who claims goods or insurance.
     */
    CC("CC", "Claimant"),

    /**
     * Bank of the agent.
     */
    CD("CD", "Agent's bank"),

    /**
     * Company which cedes something to someone.
     */
    CE("CE", "Ceding company"),

    /**
     * Party to whom the possession of specified property (e.g. container) has been conveyed
     * for a period of time in return for rental payments.
     */
    CF("CF", "Container operator/lessee"),

    /**
     * [3052] Party authorized to act for or on behalf of carrier.
     */
    CG("CG", "Carrier's agent"),

    /**
     * Owner or operator of a transportation conveyance to which goods in a given transaction
     * will be transferred.
     */
    CH("CH", "Connecting carrier"),

    /**
     * Party who provides extra treatment to goods on commission base.
     */
    CI("CI", "Commission processor"),

    /**
     * Previous member of a group of persons or a service scheme.
     */
    CJ("CJ", "Previous member"),

    /**
     * Party from whose premises empty equipment will be or has been despatched.
     */
    CK("CK", "Empty equipment despatch party"),

    /**
     * Party from whose premises container will be or has been despatched.
     */
    CL("CL", "Container location party"),

    /**
     * Identification of customs authority relevant to the transaction or shipment.
     */
    CM("CM", "Customs"),

    /**
     * [3132] Party to which goods are consigned.
     */
    CN("CN", "Consignee"),

    /**
     * Identification of a financial institution servicing the top account of a cash pool.
     */
    CNX("CNX", "Cash pool top account servicing financial institution"),

    /**
     * Identification of a financial institution servicing the level account of a cash pool.
     */
    CNY("CNY", "Cash pool level account servicing financial institution"),

    /**
     * Identification of a financial institution servicing the sub-account of a cash pool.
     */
    CNZ("CNZ", "Cash pool sub-account servicing financial institution"),

    /**
     * Identification of the Head Office within a company.
     */
    CO("CO", "Corporate office"),

    /**
     * Business in which a financial interest is held.
     */
    COA("COA", "Entity in which a financial interest is held"),

    /**
     * Identifies an intermediate parent company.
     */
    COB("COB", "Intermediate level parent company"),

    /**
     * A party responsible for transshipment.
     */
    COC("COC", "Transshipment party"),

    /**
     * Party sending a request for a quotation.
     */
    COD("COD", "Quotation requesting party"),

    /**
     * The party which maintains the codes used in the message.
     */
    COE("COE", "Party maintaining the codes used in the message"),

    /**
     * The party which maintains the identifiers used in the message.
     */
    COF("COF", "Party maintaining the identifiers used in the message"),

    /**
     * An individual responsible for sending something to a destination.
     */
    COG("COG", "Dispatcher"),

    /**
     * An entity responsible for the submission of a sample.
     */
    COH("COH", "Submitter of sample"),

    /**
     * The institution providing the service.
     */
    COI("COI", "Institutional provider"),

    /**
     * Health care provider that has primary responsibility for patient.
     */
    COJ("COJ", "Primary health care provider"),

    /**
     * Physician assisting in surgery.
     */
    COK("COK", "Assistant surgeon"),

    /**
     * Health care provider that admitted the patient.
     */
    COL("COL", "Admitting health care provider"),

    /**
     * Health care provider that referred patient to current provider of services.
     */
    COM("COM", "Referring health care provider"),

    /**
     * Health care provider that supervised the rendering of a service.
     */
    CON("CON", "Supervising health care provider"),

    /**
     * Identifies the party providing the financing.
     */
    COO("COO", "Party providing financing"),

    /**
     * Party designated to escort the transported goods.
     */
    COP("COP", "Convoying party"),

    /**
     * Identifies the nominated bank.
     */
    COQ("COQ", "Nominated bank"),

    /**
     * Identifies a family member.
     */
    COR("COR", "Family member"),

    /**
     * Identifies another party who participates in an activity.
     */
    COS("COS", "Co-participant"),

    /**
     * Party which is involved in an activity.
     */
    COT("COT", "Involved party"),

    /**
     * Identifies the entity who assigns.
     */
    COU("COU", "Assigner"),

    /**
     * An individual who is registered as a principal for an entity.
     */
    COV("COV", "Registered principal"),

    /**
     * (3470) Freight payer is a third party acting on behalf of the consignor.
     */
    COW("COW", "Freight payer on behalf of the consignor"),

    /**
     * (3470) Freight payer is a third party acting on behalf of the consignee.
     */
    COX("COX", "Freight payer on behalf of the consignee"),

    /**
     * Party responsible for performing disinfection operations.
     */
    COY("COY", "Party responsible for disinfection"),

    /**
     * Party responsible for performing refueling operations.
     */
    COZ("COZ", "Party responsible for refueling"),

    /**
     * Party acting for or on behalf of seller in matters concerning compliance.
     */
    CP("CP", "Party to receive certificate of compliance"),

    /**
     * [3190] Identifies the financial institution used by the issuing bank to advise the
     * documentary credit.
     */
    CPA("CPA", "Advising bank"),

    /**
     * Identifies the financial institution through which the reimbursement is to be effected.
     */
    CPB("CPB", "Reimbursing bank"),

    /**
     * Identifies the financial institution through which the advising bank is to advise.
     */
    CPC("CPC", "Advise through bank"),

    /**
     * Party, other than the ordering party, which has to pay the charges concerning the destination
     * operations.
     */
    CPD("CPD", "Charges payer at destination"),

    /**
     * [3408] Name of the Master of a means of transport such as vessel.
     */
    CPE("CPE", "Transport means master name"),

    /**
     * Charterer of the means of transport.
     */
    CPF("CPF", "Means of transport charterer"),

    /**
     * Party to whom excise must be paid.
     */
    CPG("CPG", "Excise party"),

    /**
     * Party receiving a copy of a report.
     */
    CPH("CPH", "Copy report to"),

    /**
     * A healthcare party related to the subject.
     */
    CPI("CPI", "Related healthcare party"),

    /**
     * Party providing clinical information.
     */
    CPJ("CPJ", "Clinical information provider"),

    /**
     * Party requesting a service.
     */
    CPK("CPK", "Service requester"),

    /**
     * Party who admitted a patient.
     */
    CPL("CPL", "Patient admitted by"),

    /**
     * The party who receives the discharged patient.
     */
    CPM("CPM", "Patient discharged to"),

    /**
     * The party hosting the patient.
     */
    CPN("CPN", "Patient hosted by"),

    /**
     * Contact person for the prescriber.
     */
    CPO("CPO", "Prescriber's contact person"),

    /**
     * Party to which the cheque will be ordered, when different from the beneficiary.
     */
    CQ("CQ", "Cheque order"),

    /**
     * Party to whose premises empty equipment will be or has been returned.
     */
    CR("CR", "Empty equipment return party"),

    /**
     * Party consolidating various consignments, payments etc.
     */
    CS("CS", "Consolidator"),

    /**
     * The party to be identified at a later time as the consignee.
     */
    CT("CT", "Consignee to be specified"),

    /**
     * The company to which containers have to be returned.
     */
    CU("CU", "Container return company"),

    /**
     * Party to which the vessel shall be delivered.
     */
    CV("CV", "Consignee of vessel"),

    /**
     * Owner of equipment (container, etc.).
     */
    CW("CW", "Equipment owner"),

    /**
     * Party authorized to act on behalf of the consignee.
     */
    CX("CX", "Consignee's agent"),

    /**
     * IATA cargo agent entitled to commission.
     */
    CY("CY", "Commissionable agent"),

    /**
     * [3336] Party which, by contract with a carrier, consigns or sends goods with the carrier,
     * or has them conveyed by him. Synonym: shipper, sender.
     */
    CZ("CZ", "Consignor"),

    /**
     * Financial institution with whom the documentary credit is available.
     */
    DA("DA", "Available with bank (documentary credits)"),

    /**
     * The affiliate of a retailer or distributor.
     */
    DB("DB", "Distributor branch"),

    /**
     * Party that splits up a large consignment composed of separate consignments of goods.
     * The smaller consignments of goods were grouped together into that large consignment
     * for carriage as a larger unit in order to obtain a reduced rate.
     */
    DC("DC", "Deconsolidator"),

    /**
     * Party, other than the ordering party, which has to pay the charges concerning the despatch
     * operations.
     */
    DCP("DCP", "Despatch charge payer"),

    /**
     * Organisation or person owning a prescription database.
     */
    DCQ("DCQ", "Prescription database owner"),

    /**
     * The doctor who issued the original prescription.
     */
    DCR("DCR", "Original prescriber"),

    /**
     * A person employed on a temporary basis.
     */
    DCS("DCS", "Temporary employee"),

    /**
     * A party who designs.
     */
    DCT("DCT", "Designer"),

    /**
     * Party to whom the quotation is to be or has been delivered.
     */
    DCU("DCU", "Quotation delivered to"),

    /**
     * A party who develops.
     */
    DCV("DCV", "Developer"),

    /**
     * The party performing a test.
     */
    DCW("DCW", "Test execution party"),

    /**
     * Party to whom a refund is given.
     */
    DCX("DCX", "Party to receive refund"),

    /**
     * Party authorised to issue a prescription.
     */
    DCY("DCY", "Authorised issuer of prescription"),

    /**
     * Organisation or person authorised to dispense medicine.
     */
    DCZ("DCZ", "Authorised dispenser of medicine"),

    /**
     * Bank of the documentary credit account party.
     */
    DD("DD", "Documentary credit account party's bank"),

    /**
     * The party or person taking responsibility for a report.
     */
    DDA("DDA", "Report responsible party"),

    /**
     * The party who does the initial sending.
     */
    DDB("DDB", "Initial sender"),

    /**
     * The party authorising the issuer of the original prescription.
     */
    DDC("DDC", "The party authorising the original prescription"),

    /**
     * A party who applies for something.
     */
    DDD("DDD", "Applicant"),

    /**
     * A party physically reading the meter.
     */
    DDE("DDE", "Meter reader"),

    /**
     * Code specifying a party who serves as a business entity's primary contact for matters
     * related to electronic business.
     */
    DDF("DDF", "Primary electronic business contact"),

    /**
     * Code specifying a party who serves as a business entity's alternate contact for matters
     * related to electronic business.
     */
    DDG("DDG", "Alternate electronic business contact"),

    /**
     * Code specifying a party who serves as a business entity's primary contact for matters
     * related to doing business with the government.
     */
    DDH("DDH", "Primary government business contact"),

    /**
     * Code specifying a party who serves as a business entity's alternate contact for matters
     * related to doing business with the government.
     */
    DDI("DDI", "Alternate government business contact"),

    /**
     * Code specifying a party who serves as a business entity's contact for matters related
     * to the past performance of that entity.
     */
    DDJ("DDJ", "Past performance contact"),

    /**
     * A party responsible for balancing supply and consumption.
     */
    DDK("DDK", "Balance responsible party"),

    /**
     * A group of persons conveyed by a means of transport, other than the crew.
     */
    DDL("DDL", "Group of passengers"),

    /**
     * A party operating a grid.
     */
    DDM("DDM", "Grid operator"),

    /**
     * Identifies the financial institution that is the point of entry into the interbank
     * transaction chain.
     */
    DDN("DDN", "First financial institution in the transaction chain"),

    /**
     * Party responsible for the management of the location.
     */
    DDO("DDO", "Location manager"),

    /**
     * Party responsible for leading the group.
     */
    DDP("DDP", "Group leader"),

    /**
     * A party supplying energy to a party connected to the grid at an accounting point. In
     * case of a surplus, the energy supplier may also take back energy.
     */
    DDQ("DDQ", "Energy Supplier"),

    /**
     * Identification of freight forwarder giving services to the consignor.
     */
    DDR("DDR", "Consignor's freight forwarder"),

    /**
     * Identification of freight forwarder giving services to the consignee.
     */
    DDS("DDS", "Consignee's freight forwarder"),

    /**
     * The movement of a crew member from one country to another via the territory of an intermediate
     * country for which no entry is intended.
     */
    DDT("DDT", "In transit crew member"),

    /**
     * The movement of a passenger from one country to another via the territory of an intermediate
     * country for which no entry is intended.
     */
    DDU("DDU", "In transit passenger"),

    /**
     * A party who can be brought to rights, legally and financially, for any imbalance between
     * energy bought and consumed for all associated metering points.
     */
    DDV("DDV", "Energy consumption imbalance responsible party"),

    /**
     * A party who can be brought to rights, legally and financially, for any imbalance between
     * energy sold and produced for all associated metering points.
     */
    DDW("DDW", "Energy production imbalance responsible party"),

    /**
     * A party that is responsible for settlement of the difference between planned and realised
     * quantities.
     */
    DDX("DDX", "Imbalance settlement responsible party"),

    /**
     * A party managing the allocation of transmission capacity.
     */
    DDY("DDY", "Transmission capacity allocator"),

    /**
     * A party responsible for registering the technical specifications of metering points
     * and the parties linked to them.
     */
    DDZ("DDZ", "Metering point administrator"),

    /**
     * Party depositing goods, financial payments or documents.
     */
    DE("DE", "Depositor"),

    /**
     * A party responsible for aggregation of metered data.
     */
    DEA("DEA", "Metered data aggregator"),

    /**
     * A party responsible for the operation of a meter, including installing  maintaining,
     * testing, certifying and decommissioning.
     */
    DEB("DEB", "Meter operator"),

    /**
     * A party that contracts for the right to consume or produce electricity at a metering
     * point.
     */
    DEC("DEC", "Party connected to grid"),

    /**
     * A party that maintains profiles.
     */
    DED("DED", "Profile maintenance party"),

    /**
     * A person who hides on a conveyance in order to obtain free passage.
     */
    DEE("DEE", "Stowaway"),

    /**
     * Person whose job is to cut up and/or mince meat.
     */
    DEF("DEF", "Meat cutter"),

    /**
     * A marine carrier that transports goods for more than one shipping line between named
     * points.
     */
    DEG("DEG", "Consortium Carrier (maritime)"),

    /**
     * A carrier that does not operate the vessel.
     */
    DEH("DEH", "Non-vessel operating carrier"),

    /**
     * The operator of a means of transport, e.g. the captain of a vessel.
     */
    DEI("DEI", "Means of transport operator"),

    /**
     * Address where cargo is loaded into the transport equipment e.g. container. Synonyms;
     * vanning address / place of vanning.
     */
    DEJ("DEJ", "Stuffing address"),

    /**
     * Party responsible for mooring the vessel at the berth in the port. Synonym: Boatmen.
     */
    DEK("DEK", "Mooring service provider"),

    /**
     * Party responsible for the pilotage of the vessel.
     */
    DEL("DEL", "Pilotage service provider"),

    /**
     * Party responsible for towing the vessel to/from the berth in the port.
     */
    DEM("DEM", "Berth towage service provider"),

    /**
     * Party authorised to act in the name and on behalf of another person.
     */
    DEN("DEN", "Agent/representative, direct representation"),

    /**
     * Party authorised to act in its own name but on behalf of another person.
     */
    DEO("DEO", "Agent/representative, indirect representation"),

    /**
     * A party which handles the loading and unloading of marine vessels from several terminals.
     */
    DEP("DEP", "Stevedore"),

    /**
     * Party responsible for the shipment of goods.
     */
    DEQ("DEQ", "Shipper"),

    /**
     * A data pool that supports the functionality required by a data source such as data
     * loading, publication, notification, registration, etc.
     */
    DER("DER", "Source data pool"),

    /**
     * The owner of a brand.
     */
    DES("DES", "Brand owner"),

    /**
     * Cockpit crew and personnel inside cockpit.
     */
    DET("DET", "Cockpit crew"),

    /**
     * Crew members operating in passenger cabin.
     */
    DEU("DEU", "Cabin crew"),

    /**
     * Personnel of the airline operations management department positioned outside the cockpit.
     */
    DEV("DEV", "Airline operations management, not in cockpit"),

    /**
     * Employees of the carrier, cargo groomers, or special cargo handlers, that are not authorized
     * to ride in the cockpit.
     */
    DEW("DEW", "Cargo non-cockpit crew and/or non-crew personnel"),

    /**
     * Pilots currently not in charge of flying the aircraft and not present in the cockpit.
     */
    DEX("DEX", "Pilots seated outside cockpit"),

    /**
     * A party recognized under relevant legislation to undertake official verification.
     */
    DEY("DEY", "Commercial verifier"),

    /**
     * A person authorized under relevant legislation for the purpose of issuing official
     * assurances.
     */
    DEZ("DEZ", "Authorized issuer"),

    /**
     * [3198] Party at whose request the applicant's bank/issuing bank is to issue a documentary
     * credit.
     */
    DF("DF", "Documentary credit applicant"),

    /**
     * Bank that is authorized to receive the deposit for duties, taxes, and fees.
     */
    DFA("DFA", "Bank for deposit of duties/taxes/fees"),

    /**
     * The company security officer as described in IMO Circular MSC 1130.
     */
    DFB("DFB", "Company security officer (IMO Circular MSC 1130)"),

    /**
     * The party providing distillation services.
     */
    DFC("DFC", "Distiller"),

    /**
     * The bonded (insured) party that moves goods within port limits.
     */
    DFD("DFD", "Drayman/lighterman"),

    /**
     * The party who offers items for exhibit or show.
     */
    DFE("DFE", "Exhibitor"),

    /**
     * A commercial establishment that feeds livestock.
     */
    DFF("DFF", "Feedlot"),

    /**
     * A person appointed to some position of responsibility or authority in the government.
     */
    DFG("DFG", "Government official"),

    /**
     * The party requesting inspection, grading, or other government service.
     */
    DFH("DFH", "Government service requestor"),

    /**
     * The party who grows crops.
     */
    DFI("DFI", "Crop grower"),

    /**
     * The responsible party as identified and defined in the International Ship and Port
     * Facility Security Code (ISPS).
     */
    DFJ("DFJ", "ISPS Responsible Party"),

    /**
     * The party to whom the License, Permit, Certificate, or Other required document (LPCO)
     * is issued.
     */
    DFK("DFK", "LPCO authorized party"),

    /**
     * The registered operator of the property.
     */
    DFL("DFL", "Operator of property, registered"),

    /**
     * An organization that is accredited to certify organic growth processes.
     */
    DFM("DFM", "Organic growth certifier, accredited"),

    /**
     * The party holding the Certificate of Financial Responsibility (COFR).
     */
    DFN("DFN", "Party holding Certificate of Financial Responsibility"),

    /**
     * The individual (person) designated as the port facility security officer responsible
     * for port security requirements.
     */
    DFO("DFO", "Port facility security officer"),

    /**
     * The owner of the vehicle.
     */
    DFP("DFP", "Vehicle owner"),

    /**
     * Organization recogized for issuing security certificates.
     */
    DFQ("DFQ", "Security certificate issuer, recognized"),

    /**
     * The person who is appointed as Ship Security Officer (SSO).
     */
    DFR("DFR", "Ship Security Officer"),

    /**
     * The person who issues veterinary certificates.
     */
    DFS("DFS", "Certificate issuer, veterinary"),

    /**
     * The party putting together various components or parts into a product or commodity.
     */
    DFT("DFT", "Assembler"),

    /**
     * The party is a competitor.
     */
    DFU("DFU", "Competitor"),

    /**
     * Party holding exclusive rights to a protected intellectual property.
     */
    DFV("DFV", "Right holder"),

    /**
     * Party approved by Customs as complying with WCO or equivalent supply-chain security
     * standards.
     */
    DFW("DFW", "Authorised Economic Operator (AEO)"),

    /**
     * Party who manufactures equipment.
     */
    DFX("DFX", "Manufacturer of equipment"),

    /**
     * Crew for another vessel.
     */
    DFY("DFY", "Crew other ship"),

    /**
     * The party responsible for article information.
     */
    DFZ("DFZ", "Article information responsible party"),

    /**
     * Party in whose favour the documentary credit is to be issued and the party that must
     * comply with the credit's terms and conditions.
     */
    DG("DG", "Documentary credit beneficiary"),

    /**
     * The party responsible for price information.
     */
    DGA("DGA", "Price information responsible party"),

    /**
     * Party to whom the invoice is sent and who processes the invoice on behalf of the invoicee.
     * Note, the invoicee is legally responsible for the invoice and can be different to the
     * processing party.
     */
    DGB("DGB", "Invoice processing party"),

    /**
     * A party providing logistic services for another party (e.g re-packing suppliers products).
     */
    DGC("DGC", "Logistic service provider"),

    /**
     * Party which provides fattening service.
     */
    DGD("DGD", "Fattener"),

    /**
     * Party which provides breeding service.
     */
    DGE("DGE", "Breeder"),

    /**
     * A party responsible for establishing the calorific value for a set of Metering points.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGF("DGF", "Calorific Value Responsible"),

    /**
     * A party with reserve-providing units or reserve-providing groups able to provide balancing
     * services to one or more LFC Operators.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGG("DGG", "Balancing Service Provider"),

    /**
     * A party responsible for keeping a register of consents for a domain. The Consent Administrator
     * makes this information available on request for entitled parties in the sector.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGH("DGH", "Consent Administrator"),

    /**
     * A party offering energy-related services to the Party Connected to Grid, but not directly
     * active in the energy value chain or the physical infrastructure itself. The ESCO may
     * provide insight services as well as energy management services.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGI("DGI", "Energy Service Company (ESCO)"),

    /**
     * A party that aggregates resources for usage by a service provider for energy market
     * services.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGJ("DGJ", "Resource Aggregator"),

    /**
     * A role that manages a resource and provides production/consumption schedules for it,
     * if required.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGK("DGK", "Resource Provider"),

    /**
     * A party responsible for storing and distributing validated measured data.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGL("DGL", "Metered Data Administrator"),

    /**
     * The person who purchases goods and services for personal use.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGM("DGM", "Consumer"),

    /**
     * The party engaged in agriculture business, field crop growing, cattle rearing and other
     * productions (hides, milk, wool, etc).
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGN("DGN", "Farmer"),

    /**
     * The party who collects waste in order to produce materials that can be used again.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGO("DGO", "Recycler"),

    /**
     * The party who sells goods to the public in relatively small quantities for use or consumption
     * rather than for resale.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGP("DGP", "Retailer"),

    /**
     * The party related to another party.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGQ("DGQ", "Second Party"),

    /**
     * The party providing waste disposal services.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGR("DGR", "Waste Disposal Provider"),

    /**
     * The party who processes animals skins with tanning agents.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGS("DGS", "Tanner"),

    /**
     * The party who provides a service.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGT("DGT", "Service Provider"),

    /**
     * The party who uses a machine in order to clean cotton fibre, flax fibre etc.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGU("DGU", "Ginner"),

    /**
     * The party who makes thread by spinning.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGV("DGV", "Spinner"),

    /**
     * The party who weaves fabric.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    DGW("DGW", "Weaver"),

    /**
     * Party which is responsible for the payment settlement of the documentary credit with
     * the applicant's bank/issuing bank, if different from the documentary credit applicant.
     */
    DH("DH", "Documentary credit account party"),

    /**
     * Party to whom the documentary credit can be transferred.
     */
    DI("DI", "Documentary credit second beneficiary"),

    /**
     * Party related to documentary credit transaction.
     */
    DJ("DJ", "Party according to documentary credit transaction"),

    /**
     * Financial institution with which the beneficiary of the documentary credit maintains
     * an account.
     */
    DK("DK", "Documentary credit beneficiary's bank"),

    /**
     * Company offering a financial service whereby a firm sells or transfers title to its
     * accounts receivable to the factoring company.
     */
    DL("DL", "Factor"),

    /**
     * Party to whom documents are to be presented.
     */
    DM("DM", "Party to whom documents are to be presented"),

    /**
     * Owner of the operation.
     */
    DN("DN", "Owner of operation"),

    /**
     * [1370] Party which should receive a specified document.
     */
    DO("DO", "Document recipient"),

    /**
     * [3144] Party to which goods should be delivered, if not identical with consignee, such
     * as the place where a container is to be, or has been, positioned.
     */
    DP("DP", "Delivery party"),

    /**
     * Person acting on delegation of powers of the owner.
     */
    DQ("DQ", "Owner's agent"),

    /**
     * Person who drives a means of transport.
     */
    DR("DR", "Driver"),

    /**
     * Party distributing goods, financial payments or documents.
     */
    DS("DS", "Distributor"),

    /**
     * [3140] Party which makes a declaration to an official body or - where legally permitted
     * - in whose name, or on whose behalf, a declaration to an official body is made.
     */
    DT("DT", "Declarant"),

    /**
     * Person commissioned by the owner to represent him in certain circumstances.
     */
    DU("DU", "Owner's representative"),

    /**
     * Party commissioned by the owner to follow through the execution of all works.
     */
    DV("DV", "Project management office"),

    /**
     * (3290) Party on whom drafts must be drawn.
     */
    DW("DW", "Drawee"),

    /**
     * Party representing the contractor to advise and supervise engineering aspects of the
     * works.
     */
    DX("DX", "Engineer (construction)"),

    /**
     * Party commissioned by the owner to advise and supervise engineering aspects of the
     * works.
     */
    DY("DY", "Engineer, resident (construction)"),

    /**
     * A designer who prepares plans for buildings, ships, etc. and supervises their construction.
     */
    DZ("DZ", "Architect"),

    /**
     * Designer of the construction project.
     */
    EA("EA", "Architect-designer"),

    /**
     * Party controlling the conformity of works to legal and regulation rules.
     */
    EB("EB", "Building inspectorate"),

    /**
     * Party exchanging currencies or goods.
     */
    EC("EC", "Exchanger"),

    /**
     * Party providing professional engineering services.
     */
    ED("ED", "Engineer, consultant"),

    /**
     * The location where the goods are examined by customs before clearance.
     */
    EE("EE", "Location of goods for customs examination before clearance"),

    /**
     * Party responsible for technical coordination of works.
     */
    EF("EF", "Project coordination office"),

    /**
     * Party responsible for topographical measurements.
     */
    EG("EG", "Surveyor, topographical"),

    /**
     * Party responsible for quantity measurements.
     */
    EH("EH", "Engineer, measurement"),

    /**
     * Party controlling the quality of goods and workmanship for the project.
     */
    EI("EI", "Controller, quality"),

    /**
     * Party responsible for the quantification and valuation of the works on behalf of the
     * contractor.
     */
    EJ("EJ", "Surveyor, quantity"),

    /**
     * Party responsible to the owner for the quantification and valuation of the works.
     */
    EK("EK", "Surveyor (professional), quantity"),

    /**
     * Party responsible for a project, e.g. a construction project.
     */
    EL("EL", "Project"),

    /**
     * Party being informed about invoice issue (via EDI).
     */
    EM("EM", "Party to receive electronic memo of invoice"),

    /**
     * Firm answering an invitation to tender.
     */
    EN("EN", "Tenderer"),

    /**
     * Party who owns equipment.
     */
    EO("EO", "Owner of equipment"),

    /**
     * The party which drops off equipment.
     */
    EP("EP", "Equipment drop-off party"),

    /**
     * Party responsible for the empty container.
     */
    EQ("EQ", "Empty container responsible party"),

    /**
     * Party, designated by owner of containers, responsible for their collection as agreed
     * between the owner and customer/ consignee.
     */
    ER("ER", "Empty container return agent"),

    /**
     * Leader representing a grouping of co-contractors.
     */
    ES("ES", "Contractor, lead"),

    /**
     * Member of a grouping of co-contractors.
     */
    ET("ET", "Co-contractor"),

    /**
     * Single contractor for the whole construction project, working by his own or with subcontractors.
     */
    EU("EU", "Contractor, general"),

    /**
     * Firm carrying out a part of the works for a contractor.
     */
    EV("EV", "Subcontractor"),

    /**
     * Subcontractor benefiting from direct payments.
     */
    EW("EW", "Subcontractor with direct payment"),

    /**
     * [3030] Party who makes, or on whose behalf the export declaration is made, and who
     * is the owner of the goods or has similar rights of disposal over them at the time when
     * the declaration is accepted.
     */
    EX("EX", "Exporter"),

    /**
     * Subcontractor authorized by the owner after having been proposed.
     */
    EY("EY", "Subcontractor, nominated"),

    /**
     * Operator of essential services e.g. water, sewerage system, power.
     */
    EZ("EZ", "Operator, essential services"),

    /**
     * Operator of a communication channel.
     */
    FA("FA", "Operator, communication channel"),

    /**
     * Party nominated to act as transport company or carrier for the goods.
     */
    FB("FB", "Nominated freight company"),

    /**
     * Firm or grouping of co-contractors which has been awarded the contract.
     */
    FC("FC", "Contractor, main"),

    /**
     * Parent company, e.g. holding company.
     */
    FD("FD", "Buyer's parent company"),

    /**
     * A party which evaluates another party for credit rating.
     */
    FE("FE", "Credit rating agency"),

    /**
     * Factoring company engaged by another factoring company to assist the letter with the
     * services provided to the clients (sellers).
     */
    FF("FF", "Factor, correspondent"),

    /**
     * Buying party as officially registered with government.
     */
    FG("FG", "Buyer as officially registered"),

    /**
     * Selling party as officially registered with government.
     */
    FH("FH", "Seller as officially registered"),

    /**
     * Party that is to receive a copy of a message.
     */
    FI("FI", "Copy message to"),

    /**
     * Organisation representing employees.
     */
    FJ("FJ", "Trade Union"),

    /**
     * Employee organisation who previously represented an employee .
     */
    FK("FK", "Previous Trade Union"),

    /**
     * A person conveyed by a means of transport, other than the crew.
     */
    FL("FL", "Passenger"),

    /**
     * A person manning a means of transport.
     */
    FM("FM", "Crew member"),

    /**
     * [3363] The issuer of a tariff, e.g. a freight tariff.
     */
    FN("FN", "Tariff issuer"),

    /**
     * A party which inspects something.
     */
    FO("FO", "Party performing inspection"),

    /**
     * Party responsible for the payment of freight.
     */
    FP("FP", "Freight/charges payer"),

    /**
     * The container survey agency that will survey the containers.
     */
    FQ("FQ", "Container survey agent"),

    /**
     * Party where the message comes from.
     */
    FR("FR", "Message from"),

    /**
     * Party who has the authority to make definite a contract action.
     */
    FS("FS", "Party authorized to make definite a contract action"),

    /**
     * [3450] Party responsible for either the transfer or repatriation of the funds relating
     * to a transaction.
     */
    FT("FT", "Financial settlement party"),

    /**
     * The office responsible for providing information regarding hazardous material.
     */
    FU("FU", "Hazardous material office"),

    /**
     * The party responsible for providing government furnished property.
     */
    FV("FV", "Party providing government furnished property"),

    /**
     * [3170] Party arranging forwarding of goods.
     */
    FW("FW", "Freight forwarder"),

    /**
     * Current receiver of the goods in a multi-step transportation process (indirect flow)
     * involving at least one grouping centre.
     */
    FX("FX", "Current receiver"),

    /**
     * Current sender of the goods in a multi-step transportation process (indirect flow)
     * involving at least one grouping centre.
     */
    FY("FY", "Current sender"),

    /**
     * A party in charge of groupage, including degroupage and regroupage.
     */
    FZ("FZ", "Grouping centre"),

    /**
     * A road carrier moving cargo.
     */
    GA("GA", "Road carrier"),

    /**
     * Name of the Chamber of Commerce of the town where the company is registered.
     */
    GB("GB", "Chamber of commerce"),

    /**
     * [3024] Party responsible for the keeping of goods.
     */
    GC("GC", "Goods custodian"),

    /**
     * Party or person who has produced the produce.
     */
    GD("GD", "Producer"),

    /**
     * Name of the tribunal where the company is registered.
     */
    GE("GE", "Registration tribunal"),

    /**
     * An identification code of a participant or user that books slots (space) on a ship,
     * more likely on a long term basis on a series of sailings. He pays for the space whether
     * he uses it or not.
     */
    GF("GF", "Slot charter party"),

    /**
     * A person who applied for a job.
     */
    GH("GH", "Applicant for job"),

    /**
     * Person is a spouse.
     */
    GI("GI", "Spouse"),

    /**
     * Person is a mother.
     */
    GJ("GJ", "Mother"),

    /**
     * Person is a father.
     */
    GK("GK", "Father"),

    /**
     * A person who is registered in a social security scheme.
     */
    GL("GL", "Socially insured person"),

    /**
     * To specifically identify the party in charge of inventory control.
     */
    GM("GM", "Inventory controller"),

    /**
     * Party or person who has or will apply a process.
     */
    GN("GN", "Processor"),

    /**
     * The party which owns the goods.
     */
    GO("GO", "Goods owner"),

    /**
     * Party or person who has undertaken or will undertake packing.
     */
    GP("GP", "Packer"),

    /**
     * Party or person who has undertaken or will undertake a slaughter.
     */
    GQ("GQ", "Slaughterer"),

    /**
     * [3026] Party entitled to authorize release of goods from custodian.
     */
    GR("GR", "Goods releasing party"),

    /**
     * Party authorised to represent the consignor.
     */
    GS("GS", "Consignor's representative"),

    /**
     * A carrier moving cargo, including containers, via rail.
     */
    GT("GT", "Rail carrier"),

    /**
     * A code identifying the party which created a specific article number.
     */
    GU("GU", "Originator of article number"),

    /**
     * A code used to identify the organization which is responsible for the procurement.
     */
    GV("GV", "Procurement responsibility for order"),

    /**
     * Code indicating the fact that the party identified carries out all operations within
     * that company's activities.
     */
    GW("GW", "Party fulfilling all operations"),

    /**
     * Party controlling a central catalogue.
     */
    GX("GX", "Central catalogue party"),

    /**
     * Party reporting inventory information.
     */
    GY("GY", "Inventory reporting party"),

    /**
     * Party which may be in a position to supply products or services should the main usual
     * supplier be unable to do so.
     */
    GZ("GZ", "Substitute supplier"),

    /**
     * Party which delivers consignments to a terminal.
     */
    HA("HA", "Party which delivers consignments to the terminal"),

    /**
     * Party which picks up consignments from a terminal.
     */
    HB("HB", "Party which picks up consignments from the terminal"),

    /**
     * Freight forwarder to whom transit consignments are addressed, and from whom they are
     * to be on-forwarded.
     */
    HC("HC", "Transit freight forwarder"),

    /**
     * The party who will perform inspection and acceptance.
     */
    HD("HD", "Inspection and acceptance party"),

    /**
     * The office that provides transportation information.
     */
    HE("HE", "Transportation office"),

    /**
     * The office responsible for the administration of a contract.
     */
    HF("HF", "Contract administration office"),

    /**
     * A party who conducts investigations.
     */
    HG("HG", "Investigator"),

    /**
     * The office responsible for conducting audits.
     */
    HH("HH", "Audit office"),

    /**
     * The party requesting an action.
     */
    HI("HI", "Requestor"),

    /**
     * The office that reviews sensitive information for foreign disclosure.
     */
    HJ("HJ", "Foreign disclosure information office"),

    /**
     * The party within an organization for whom the material is marked to be delivered.
     */
    HK("HK", "Mark-for party"),

    /**
     * The party to whom reports are to be submitted.
     */
    HL("HL", "Party to receive reports"),

    /**
     * Party identification of an alternative manufacturer for a product.
     */
    HM("HM", "Alternative manufacturer"),

    /**
     * The party who is performing a service.
     */
    HN("HN", "Service performer"),

    /**
     * An association of shippers.
     */
    HO("HO", "Shipper's association"),

    /**
     * To identify the final recipient of the message.
     */
    HP("HP", "Final message recipient"),

    /**
     * Identifies the owner of the account.
     */
    HQ("HQ", "Account owner"),

    /**
     * Identifies the shipping line service organization.
     */
    HR("HR", "Shipping line service"),

    /**
     * Party to whom payment is due.
     */
    HS("HS", "Creditor"),

    /**
     * Institution through which funds will be paid.
     */
    HT("HT", "Clearing house"),

    /**
     * Bank which instructed the sender to act on the transaction(s).
     */
    HU("HU", "Ordering bank"),

    /**
     * Identifies the financial party that receives the funds.
     */
    HV("HV", "Receiver of funds"),

    /**
     * Identifies the party that sends the funds.
     */
    HW("HW", "Sender of funds"),

    /**
     * Party from whom payment is due.
     */
    HX("HX", "Debtor"),

    /**
     * The bank which presents documents to the drawee.
     */
    HY("HY", "Presenting bank"),

    /**
     * Team responsible for performing work.
     */
    HZ("HZ", "Work team"),

    /**
     * A financial institution between the ordered bank and the beneficiary's bank.
     */
    I1("I1", "Intermediary bank 1"),

    /**
     * A financial institution between the ordered bank and the beneficiary's bank.
     */
    I2("I2", "Intermediary bank 2"),

    /**
     * A person intervening between parties to produce agreement or reconciliation.
     */
    IB("IB", "Intermediary/broker"),

    /**
     * The intermediate consignee.
     */
    IC("IC", "Intermediate consignee"),

    /**
     * A code used to identify a party who replaces the previous party for the manufacture
     * of an article.
     */
    ID("ID", "Replacing manufacturer"),

    /**
     * Identifies the non-resident third party company with whom the financial account is
     * held.
     */
    IE("IE", "Non-resident third party company with whom financial account is held"),

    /**
     * Identifies the non-resident group company with whom the financial account is held.
     */
    IF("IF", "Non-resident group company with whom financial account is held"),

    /**
     * The ultimate non-resident recipient of the funds. Normally the account owner who is
     * reimbursed by the payer.
     */
    IG("IG", "Non-resident beneficiary"),

    /**
     * The ultimate resident recipient of the funds. Normally the account owner who is reimbursed
     * by the payer.
     */
    IH("IH", "Resident beneficiary"),

    /**
     * [3028] Party issuing an invoice.
     */
    II("II", "Invoice issuer"),

    /**
     * Identifies the non-resident party originating the instruction.
     */
    IJ("IJ", "Non-resident instructing party"),

    /**
     * Identifies the resident party originating the instruction.
     */
    IL("IL", "Resident instructing party"),

    /**
     * [3020] Party who makes - or on whose behalf a Customs clearing agent or other authorized
     * person makes - an import declaration. This may include a person who has possession
     * of the goods or to whom the goods are consigned.
     */
    IM("IM", "Importer"),

    /**
     * [3070] A person or company offering insurance policies for premiums.
     */
    IN("IN", "Insurer"),

    /**
     * A company engaged in the business of insurance.
     */
    IO("IO", "Insurance company"),

    /**
     * [3360] A party which adjusts losses on behalf of an insurer.
     */
    IP("IP", "Insurance claim adjuster"),

    /**
     * Domestic party acting as financial institution.
     */
    IQ("IQ", "Domestic financial institution"),

    /**
     * Non-domestic party acting as financial institution.
     */
    IR("IR", "Non-domestic financial institution"),

    /**
     * Party (at buyer) to receive certified inspection report.
     */
    IS("IS", "Party to receive certified inspection report"),

    /**
     * A party who possesses the site on which an installation shall be made.
     */
    IT("IT", "Installation on site"),

    /**
     * Non-resident party who makes the payment or against whom a claim exists.
     */
    IU("IU", "Non-resident debtor"),

    /**
     * [3006] Party to whom an invoice is issued.
     */
    IV("IV", "Invoicee"),

    /**
     * Non-resident party receiving the payment or against whom a liability exists.
     */
    IW("IW", "Non-resident creditor"),

    /**
     * The supplier's team responsible for performing the work.
     */
    IX("IX", "Supplier work team"),

    /**
     * A code to identify the party who rents the rights to use the goodwill and facilities
     * of an enterprise.
     */
    IY("IY", "Tenant manager"),

    /**
     * A code to identify the party who has been legally mandated to sell off an enterprise.
     */
    IZ("IZ", "Party mandated to liquidate an enterprise"),

    /**
     * Code identifying the party as a certified accountant.
     */
    JA("JA", "Certified accountant"),

    /**
     * Party that will collect or has collected the goods.
     */
    JB("JB", "Goods collection party"),

    /**
     * Identifies the party at the final place of positioning.
     */
    JC("JC", "Party at final place of positioning"),

    /**
     * Identifies the office where customs clearance procedures take place.
     */
    JD("JD", "Customs office of clearance"),

    /**
     * Identification of the party from whom customs documents are to be picked up.
     */
    JE("JE", "Party from whom customs documents are to be picked up"),

    /**
     * Identification of the party from whom non-customs documents are to be picked up.
     */
    JF("JF", "Party from whom non-customs documents are to be picked up"),

    /**
     * Identification of the party to whom customs documents are to be delivered.
     */
    JG("JG", "Party to receive customs documents"),

    /**
     * Identification of the party to whom non-customs documents are to be delivered.
     */
    JH("JH", "Party to receive non-customs documents"),

    /**
     * Party responsible to take care of transported living animals.
     */
    LA("LA", "Party designated to provide living animal care"),

    /**
     * A code used to identify a party who participates in production.
     */
    LB("LB", "Co-producer"),

    /**
     * A code to identify the party who is responsible for declaring the Value Added Tax (VAT)
     * on the sale of goods or services.
     */
    LC("LC", "Party declaring the Value Added Tax (VAT)"),

    /**
     * A code to identify the party who is eligible to recover the Value Added Tax (VAT) on
     * the sale of goods or services.
     */
    LD("LD", "Party recovering the Value Added Tax (VAT)"),

    /**
     * To identify the person who is the subject of the claim.
     */
    LE("LE", "Person on claim"),

    /**
     * The identification of the buyer's corporate office.
     */
    LF("LF", "Buyer's corporate office"),

    /**
     * The identification of the supplier's corporate office.
     */
    LG("LG", "Supplier's corporate office"),

    /**
     * The party responsible for settling or paying a debt.
     */
    LH("LH", "Liquidator"),

    /**
     * An individual with coordination responsibilities for a specific account.
     */
    LI("LI", "Account coordinator"),

    /**
     * An individual responsible for an inspection team.
     */
    LJ("LJ", "Inspection leader"),

    /**
     * A person receiving or registered to receive medical treatment.
     */
    LK("LK", "Patient"),

    /**
     * Person accompanying the patient.
     */
    LL("LL", "Patient companion"),

    /**
     * The party who executes a medical treatment.
     */
    LM("LM", "Medical treatment executant"),

    /**
     * Party lending goods or equipment.
     */
    LN("LN", "Lender"),

    /**
     * The party who prescribes a medical treatment.
     */
    LO("LO", "Medical treatment prescriber"),

    /**
     * Party responsible for the loading when other than carrier.
     */
    LP("LP", "Loading party"),

    /**
     * A party which authorises the payment of a debt.
     */
    LQ("LQ", "Debt payment authorisation party"),

    /**
     * Identification of an administration centre.
     */
    LR("LR", "Administration centre"),

    /**
     * A centre which services and repairs products.
     */
    LS("LS", "Product services and repairs centre"),

    /**
     * Party is a secretariat.
     */
    LT("LT", "Secretariat"),

    /**
     * Party acts as an entry point for technical assessment.
     */
    LU("LU", "Entry point technical assessment group"),

    /**
     * Party responsible for assigning a status.
     */
    LV("LV", "Party assigning a status"),

    /**
     * Party for whom item is ultimately intended.
     */
    MA("MA", "Party for whom item is ultimately intended"),

    /**
     * A party responsible for keeping a register of meters and related characteristics.
     */
    MAD("MAD", "Meter administrator"),

    /**
     * A party responsible for the establishment and validation of metered data received from
     * the Metered Data Collector.
     */
    MDR("MDR", "Metered data responsible"),

    /**
     * [3513] Party who manufactures the goods.
     */
    MF("MF", "Manufacturer of goods"),

    /**
     * Party designated to execute re-icing, selected in the official list of mandatories
     * competent for this kind of operation.
     */
    MG("MG", "Party designated to execute re-icing"),

    /**
     * A party issuing a planning schedule/material release.
     */
    MI("MI", "Planning schedule/material release issuer"),

    /**
     * Operator of a market, e.g . In the utilities sector, the unique power exchange of trades
     * for the actual delivery of energy that can also establish area prices for settlement
     * and reconciliation.
     */
    MOP("MOP", "Market operator"),

    /**
     * A party acting as a particular production unit of a manufacturer.
     */
    MP("MP", "Manufacturing unit"),

    /**
     * A party to receive a message or messages.
     */
    MR("MR", "Message recipient"),

    /**
     * [3522] Issuer of a document and/or sender of a message.
     */
    MS("MS", "Document/message issuer/sender"),

    /**
     * A party which is designated to execute sanitary procedures.
     */
    MT("MT", "Party designated to execute sanitary procedures"),

    /**
     * [3376] The second party which is to be notified.
     */
    N2("N2", "Notify party no. 2"),

    /**
     * [3180] Party to be notified. Synonym: Notify party No. 1.
     */
    NI("NI", "Notify party"),

    /**
     * Party who offers facilities for berthing of vessels, handling and storage of break
     * bulk cargo.
     */
    OA("OA", "Break bulk berth operator"),

    /**
     * Party who issued an order.
     */
    OB("OB", "Ordered by"),

    /**
     * The party responsible for all party data.
     */
    OC("OC", "Party data responsible party"),

    /**
     * A party making repairs to equipment.
     */
    OD("OD", "Equipment repair party"),

    /**
     * Party owning a property.
     */
    OE("OE", "Owner of property"),

    /**
     * Party on behalf of which an action is executed.
     */
    OF("OF", "On behalf of"),

    /**
     * Surveyor hired by the owner or lessor of the item.
     */
    OG("OG", "Owner or lessor's surveyor"),

    /**
     * Surveyor hired by the lessee of the item.
     */
    OH("OH", "Lessee's surveyor"),

    /**
     * Third party inspecting goods or equipment.
     */
    OI("OI", "Outside inspection agency"),

    /**
     * Another party besides the two principals.
     */
    OJ("OJ", "Third party"),

    /**
     * Identifies a sub-entity within the receiver's organization.
     */
    OK("OK", "Receiver's sub-entity"),

    /**
     * Party to be approached in case of difficulty.
     */
    OL("OL", "Case of need party"),

    /**
     * Any bank, other than the remitting bank, involved in processing the collection.
     */
    OM("OM", "Collecting bank"),

    /**
     * The bank to which the principal has entrusted the handling of a collection.
     */
    ON("ON", "Remitting bank"),

    /**
     * The owner of goods under consignment which are moving under a negotiable transport
     * document and will only be released upon receipt of the original transport document.
     */
    OO("OO", "Order of the shipper party"),

    /**
     * (3174) The party which operates property or a unit of equipment.
     */
    OP("OP", "Operator of property or equipment"),

    /**
     * The party entrusting the handling of a collection to a bank.
     */
    OQ("OQ", "Collection principal"),

    /**
     * Identifies the bank servicing the account for the ordering customer or payer.
     */
    OR("OR", "Ordered bank"),

    /**
     * The original supplier of the goods.
     */
    OS("OS", "Original shipper"),

    /**
     * Third party testing goods, equipment or services.
     */
    OT("OT", "Outside test agency"),

    /**
     * Identifies the financial institution on the sending side which services the account
     * owner's bank account(s).
     */
    OU("OU", "Account owner's servicing bank on the sending side"),

    /**
     * Party owning the means of transport. No synonym of carrier (= CA).
     */
    OV("OV", "Transport means owner"),

    /**
     * Identifies the financial institution on the receiving side which services the account
     * owner's bank account(s).
     */
    OW("OW", "Account owner's servicing bank on the receiving side"),

    /**
     * The account, or branch of the sender, or another financial institution, through which
     * the sender will reimburse the receiver.
     */
    OX("OX", "Sender's correspondent bank"),

    /**
     * Identifies the originator of the instruction.
     */
    OY("OY", "Ordering customer"),

    /**
     * The branch of the receiver, or another financial institution, at which the funds will
     * be made available to the receiver.
     */
    OZ("OZ", "Receiver's correspondent bank"),

    /**
     * First party to contact.
     */
    P1("P1", "Contact party 1"),

    /**
     * Second party to contact.
     */
    P2("P2", "Contact party 2"),

    /**
     * Third party to contact.
     */
    P3("P3", "Contact party 3"),

    /**
     * Fourth party to contact.
     */
    P4("P4", "Contact party 4"),

    /**
     * Party to whom the inspection report should be sent.
     */
    PA("PA", "Party to receive inspection report"),

    /**
     * A party responsible for maintaining party information.
     */
    @Deprecated(since = "D23A", forRemoval = false)
    PAD("PAD", "Party Administrator"),

    /**
     * Financial institution designated to make payment.
     */
    PB("PB", "Paying financial institution"),

    /**
     * Party the purchaser within the actual message is selling the ordered goods or services
     * to.
     */
    PC("PC", "Actual purchaser's customer"),

    /**
     * Purchasing department of buyer.
     */
    PD("PD", "Purchaser's department buyer"),

    /**
     * Identifies the credit party when other than the beneficiary.
     */
    PE("PE", "Payee"),

    /**
     * Party to whom the freight bill should be sent.
     */
    PF("PF", "Party to receive freight bill"),

    /**
     * Party responsible for the whole project if other than the buyer.
     */
    PG("PG", "Prime contractor"),

    /**
     * Institution chosen by the payer to execute financial transactions on his behalf.
     */
    PH("PH", "Payer's financial institution"),

    /**
     * Receiving company name/ID (ACH transfers).
     */
    PI("PI", "Payee's company name/ID"),

    /**
     * Second party designated by a first party to receive certain correspondence in lieu
     * of it being mailed directly to this first party.
     */
    PJ("PJ", "Party to receive correspondence"),

    /**
     * Party to contact.
     */
    PK("PK", "Contact party"),

    /**
     * Party being informed about invoice issue (via paper).
     */
    PM("PM", "Party to receive paper memo of invoice"),

    /**
     * The party is to be the recipient of the shipping notice.
     */
    PN("PN", "Party to receive shipping notice"),

    /**
     * To be used only if ordering party and buyer are not identical.
     */
    PO("PO", "Ordering party"),

    /**
     * A governmental commission in charge of the traffic and regulations of a port.
     */
    POA("POA", "Port Authority"),

    /**
     * A party which certifies something.
     */
    PQ("PQ", "Certifying party"),

    /**
     * [3308] Party responsible for making a payment.
     */
    PR("PR", "Payer"),

    /**
     * Party to send cheque, draft or wire for payment.
     */
    PS("PS", "Payer's company name/ID (Check, Draft or Wire)"),

    /**
     * A party which is named to be the recipient of test reports.
     */
    PT("PT", "Party to receive test report"),

    /**
     * [3282] Party where goods are to be, or have been, taken over by a carrier such as the
     * place where a container is picked-up.
     */
    PW("PW", "Despatch party"),

    /**
     * A party which is named to be the recipient of all documents.
     */
    PX("PX", "Party to receive all documents"),

    /**
     * Party or contact designated on behalf of carrier or his agent to establish the actual
     * figures for quantities, weight, volume and/or (cube) measurements of goods or containers
     * which are to appear in the transport contract and on which charges will be based.
     */
    PY("PY", "Checking party"),

    /**
     * The party that is to print a specific document.
     */
    PZ("PZ", "Party to print some document"),

    /**
     * Identifies central bank or regulatory authority which must be informed of certain aspects
     * of a message.
     */
    RA("RA", "Central bank or regulatory authority"),

    /**
     * Financial institution designated to receive payment.
     */
    RB("RB", "Receiving financial institution"),

    /**
     * A party that is financially accountable for reconciliation, e.g. for the reconciled
     * volume of energy products for a profiled accounting point.
     */
    RCA("RCA", "Reconciliation accountable"),

    /**
     * A party that is responsible for reconciliation, e.g. reconciling volumes within a metering
     * grid area.
     */
    RCR("RCR", "Reconciliation responsible"),

    /**
     * Party to whom payment for a commercial invoice or bill should be remitted.
     */
    RE("RE", "Party to receive commercial invoice remittance"),

    /**
     * Name of a person or department which actually delivers the goods.
     */
    RF("RF", "Received from"),

    /**
     * Financial institution designated by seller to receive payment. RDFI (ACH transfers).
     */
    RH("RH", "Seller's financial institution"),

    /**
     * Intermediary party between ceding company and reinsurance.
     */
    RI("RI", "Reinsurance intermediary/broker"),

    /**
     * Party who makes the cargo report to Customs.
     */
    RL("RL", "Reporting carrier (Customs)"),

    /**
     * Agent who formally makes a cargo report to Customs on behalf of the carrier.
     */
    RM("RM", "Reporting carrier's nominated agent/representative (Customs)"),

    /**
     * Party responsible for the selection of the carrier(s).
     */
    RP("RP", "Routing party"),

    /**
     * Party to whom the statement of account should be sent.
     */
    RS("RS", "Party to receive statement of account"),

    /**
     * Identifies the party which is to receive the actual cheque, when different from the
     * receiver of funds.
     */
    RV("RV", "Receiver of cheque"),

    /**
     * Party issuing the contract (waybill) for carriage.
     */
    RW("RW", "Issuer of waybill"),

    /**
     * A party being responsible for sales.
     */
    SB("SB", "Sales responsibility"),

    /**
     * [3346] Party selling merchandise or services to a buyer.
     */
    SE("SE", "Seller"),

    /**
     * Identification of the party from where goods will be or have been shipped.
     */
    SF("SF", "Ship from"),

    /**
     * A chain of shops or stores.
     */
    SG("SG", "Store group"),

    /**
     * The party which issues a shipping schedule.
     */
    SI("SI", "Shipping schedule issuer"),

    /**
     * A party keeping a shop or store.
     */
    SN("SN", "Store keeper"),

    /**
     * Party to whom the goods have been sold, if different to the bill to party.
     */
    SO("SO", "Sold to if different than bill to"),

    /**
     * Party responsible for declaration of the verified gross mass (weight) of a packed transport
     * equipment according to SOLAS Chapter VI, Regulation 2, paragraphs 4-6.
     */
    SPC("SPC", "SOLAS verified gross mass responsible party"),

    /**
     * [3254] Party representing the seller for the purpose of a trade transaction.
     */
    SR("SR", "Seller agent"),

    /**
     * Party collecting social securities premiums.
     */
    SS("SS", "Social securities collector's office"),

    /**
     * Identification of the party to where goods will be or have been shipped.
     */
    ST("ST", "Ship to"),

    /**
     * Party who supplies goods and or services.
     */
    SU("SU", "Supplier"),

    /**
     * Natural of legal person (generally a bank of insurance company) who accepts responsibility
     * in due legal form for the financial guarantee to Customs of the payment of additional
     * duties or fees that become due against a particular shipment, which have not previously
     * been covered by surety.
     */
    SX("SX", "Surety for additions"),

    /**
     * Natural or legal person (generally a bank or insurance company) who accepts responsibility
     * in due legal form for the financial consequences of non-fulfillment of another's obligations
     * to the Customs (CCC).
     */
    SY("SY", "Surety"),

    /**
     * Natural or legal person that has been contracted by the importer to guarantee to Customs
     * the payment of antidumping and/or countervailing duties that become due against a particular
     * shipment.
     */
    SZ("SZ", "Surety for antidumping/countervailing duty"),

    /**
     * The party responsible for a receivership.
     */
    TA("TA", "Legal receiver"),

    /**
     * To specify that the party is a submitter.
     */
    TB("TB", "Submitter"),

    /**
     * Party collecting taxes.
     */
    TC("TC", "Tax collector's office"),

    /**
     * Party, other than the ordering party, which has to pay the charges concerning the transit
     * operations.
     */
    TCP("TCP", "Transit charge payer"),

    /**
     * Party responsible for transport capacity.
     */
    TCR("TCR", "Transport capacity responsible party"),

    /**
     * Party to whom technical documentation should be sent.
     */
    TD("TD", "Party to receive technical documentation"),

    /**
     * To specify that the party is a referee in a bankruptcy case.
     */
    TE("TE", "Bankruptcy referee"),

    /**
     * To specify that the party is the source of information.
     */
    TF("TF", "Source of information"),

    /**
     * To specify that the party is a judge.
     */
    TG("TG", "Judge"),

    /**
     * To specify that the party is an attorney.
     */
    TH("TH", "Attorney"),

    /**
     * To specify that the party is a law firm.
     */
    TI("TI", "Law firm"),

    /**
     * To specify that the party is a trustee.
     */
    TJ("TJ", "Trustee"),

    /**
     * To specify that the party is a signatory.
     */
    TK("TK", "Signatory"),

    /**
     * The party is an occupant.
     */
    TL("TL", "Occupant"),

    /**
     * The party is a co-occupant.
     */
    TM("TM", "Co-occupant"),

    /**
     * The party is the subject of an inquiry.
     */
    TN("TN", "Subject of inquiry"),

    /**
     * The party is a lessor.
     */
    TO("TO", "Lessor"),

    /**
     * Identifies the owner of a residence.
     */
    TP("TP", "Owner of residence"),

    /**
     * Identifies the founder.
     */
    TQ("TQ", "Founder"),

    /**
     * A party which handles the loading and unloading of means of transport.
     */
    TR("TR", "Terminal operator"),

    /**
     * Party to whom the certified test results should be sent.
     */
    TS("TS", "Party to receive certified test results"),

    /**
     * The party which is the recipient of a transfer.
     */
    TT("TT", "Transfer to"),

    /**
     * Identifies the president.
     */
    TU("TU", "President"),

    /**
     * Identifies the chairperson.
     */
    TV("TV", "Chairperson"),

    /**
     * Identifies the legal title holder.
     */
    TW("TW", "Legal title holder"),

    /**
     * Identifies a shareholder.
     */
    TX("TX", "Shareholder"),

    /**
     * Identifies the provider.
     */
    TY("TY", "Provider"),

    /**
     * Identifies the branch of the military.
     */
    TZ("TZ", "Military branch"),

    /**
     * Identifies a university, college or school.
     */
    UA("UA", "Educational institution"),

    /**
     * Identifies the assignor.
     */
    UB("UB", "Assignor"),

    /**
     * Party who has been designated on the invoice or packing list as the final recipient
     * of the stated merchandise.
     */
    UC("UC", "Ultimate consignee"),

    /**
     * The final recipient of goods.
     */
    UD("UD", "Ultimate customer"),

    /**
     * Identifies the advisor.
     */
    UE("UE", "Advisor"),

    /**
     * Identifies the co-defendant.
     */
    UF("UF", "Co-defendant"),

    /**
     * Company whose identity has been retained from a merger.
     */
    UG("UG", "Merged company with retained identity"),

    /**
     * Identifies the party represented.
     */
    UH("UH", "Party represented"),

    /**
     * Party authorized (during a voyage) to apply unexpected handling procedures or party
     * having applied these procedures.
     */
    UHP("UHP", "Unexpected handling party"),

    /**
     * Identifies the assignee.
     */
    UI("UI", "Assignee"),

    /**
     * Identifies the key person.
     */
    UJ("UJ", "Key person"),

    /**
     * Identifies the author.
     */
    UK("UK", "Author"),

    /**
     * Identifies the ultimate parent company.
     */
    UL("UL", "Ultimate parent company"),

    /**
     * Identifies a party not to be confused with another party.
     */
    UM("UM", "Party not to be confused with"),

    /**
     * Identifies the accountant.
     */
    UN("UN", "Accountant"),

    /**
     * Identifies the plaintiff.
     */
    UO("UO", "Plaintiff"),

    /**
     * A party to unload the goods.
     */
    UP("UP", "Unloading party"),

    /**
     * Identifies the parent company.
     */
    UQ("UQ", "Parent company"),

    /**
     * Identifies the affiliated company.
     */
    UR("UR", "Affiliated company"),

    /**
     * Identifies the bailiff.
     */
    US("US", "Bailiff"),

    /**
     * Identifies the company involved in a merger.
     */
    UT("UT", "Merged company"),

    /**
     * Identifies the defendant.
     */
    UU("UU", "Defendant"),

    /**
     * Identifies the petitioning creditor.
     */
    UV("UV", "Petitioning creditor"),

    /**
     * Identifies the guarantee agency.
     */
    UW("UW", "Guarantee agency"),

    /**
     * Identifies the organization group.
     */
    UX("UX", "Organization group"),

    /**
     * Identifies the subsidiary.
     */
    UY("UY", "Subsidiary"),

    /**
     * Identifies the industry association.
     */
    UZ("UZ", "Industry association"),

    /**
     * Identifies the joint owner.
     */
    VA("VA", "Joint owner"),

    /**
     * Identifies the joint venture.
     */
    VB("VB", "Joint venture"),

    /**
     * Identifies the filing office.
     */
    VC("VC", "Filing office"),

    /**
     * Identifies the court.
     */
    VE("VE", "Court"),

    /**
     * Identifies the liability holder.
     */
    VF("VF", "Liability holder"),

    /**
     * Identifies the local government sponsor.
     */
    VG("VG", "Local government sponsor"),

    /**
     * Identifies the mortgage company.
     */
    VH("VH", "Mortgage company"),

    /**
     * Identifies the notary public.
     */
    VI("VI", "Notary public"),

    /**
     * Identifies the officer.
     */
    VJ("VJ", "Officer"),

    /**
     * Identifies the publisher.
     */
    VK("VK", "Publisher"),

    /**
     * Identifies the party for whom manufacturing of goods is done.
     */
    VL("VL", "Party manufactured for"),

    /**
     * Identifies the previous owner.
     */
    VM("VM", "Previous owner"),

    /**
     * Party vending goods or services.
     */
    VN("VN", "Vendor"),

    /**
     * Identifies the purchased company.
     */
    VO("VO", "Purchased company"),

    /**
     * Manager of a business which is in receivership status and which will not be liquidated.
     */
    VP("VP", "Receiver manager"),

    /**
     * Identifies the responsible government agency.
     */
    VQ("VQ", "Responsible government agency"),

    /**
     * Identifies the sole proprietor.
     */
    VR("VR", "Sole proprietor"),

    /**
     * Identifies the auctioneer.
     */
    VS("VS", "Auctioneer"),

    /**
     * Identifies the branch.
     */
    VT("VT", "Branch"),

    /**
     * Identifies the business.
     */
    VU("VU", "Business"),

    /**
     * Identifies the highest level parent company in the same country.
     */
    VV("VV", "Ultimate same country parent company"),

    /**
     * Identifies the party that can be called to account.
     */
    VW("VW", "Responsible party"),

    /**
     * Identifies a party that is guaranteed against loss.
     */
    VX("VX", "Secured party"),

    /**
     * Identifies an entity as an unspecified but related party.
     */
    VY("VY", "Other related party"),

    /**
     * Identifies an entity as a joint or mutual debtor.
     */
    VZ("VZ", "Co-debtor"),

    /**
     * Identifies a company which holds any financial stake in an undertaking or organization.
     */
    WA("WA", "Company which holds financial interest"),

    /**
     * Identifies an organization responsible for assigning a classification or rating.
     */
    WB("WB", "Rating organization"),

    /**
     * The agency responsible for the reference of information.
     */
    WC("WC", "Information reference agency"),

    /**
     * [3004] Party depositing goods in a warehouse.
     */
    WD("WD", "Warehouse depositor"),

    /**
     * The agency responsible for the compilation of information.
     */
    WE("WE", "Compilation agency"),

    /**
     * The agency responsible for the maintenance of information.
     */
    WF("WF", "Information maintenance agency"),

    /**
     * The agency responsible for the dissemination of information.
     */
    WG("WG", "Information dissemination agency"),

    /**
     * [3022] Party taking responsibility for goods entered into a warehouse.
     */
    WH("WH", "Warehouse keeper"),

    /**
     * Specifies the address for an inspection.
     */
    WI("WI", "Inspection address"),

    /**
     * Identification of the party responsible for a refusal.
     */
    WJ("WJ", "Refusal party"),

    /**
     * A party that provides telecommunications interconnectivity services in an electronic
     * data interchange environment.
     */
    WK("WK", "Value added network provider"),

    /**
     * The business or establishment of an agent.
     */
    WL("WL", "Agency"),

    /**
     * A party managing works.
     */
    WM("WM", "Works manager"),

    /**
     * Party designated by the registering party to receive a binding direction to supply
     * something.
     */
    WN("WN", "Party to receive order to supply"),

    /**
     * An entity to receive an invitation to offer.
     */
    WO("WO", "Party to receive invitation to offer"),

    /**
     * A part into which an entity has been divided.
     */
    WP("WP", "Sub-entity"),

    /**
     * Party designated (legally accepted) to ascertain the weight.
     */
    WPA("WPA", "Weighting party"),

    /**
     * The name under which business is conducted.
     */
    WQ("WQ", "Doing business as"),

    /**
     * The party stating the price of something to be purchased.
     */
    WR("WR", "Party submitting quote"),

    /**
     * Seller of articles, often in large quantities, to be retailed by others.
     */
    WS("WS", "Wholesaler"),

    /**
     * A party attached or connected to another party.
     */
    WT("WT", "Affiliated party"),

    /**
     * Name of an entity used before the current name.
     */
    WU("WU", "Previous name"),

    /**
     * An entity responsible for performing a task to be undertaken.
     */
    WV("WV", "Party performing task"),

    /**
     * Party performing the registration.
     */
    WW("WW", "Registering party"),

    /**
     * Party that offers the facility for the goods or container(s) to be cleared by customs
     * authorities or other governmental authorities in the interior of a country.
     */
    WX("WX", "Inland clearance depot operator"),

    /**
     * Party that operates a terminal to which goods or containers are destined.
     */
    WY("WY", "Destination terminal operator"),

    /**
     * Party that operates a terminal from which goods or containers have departed or will
     * depart.
     */
    WZ("WZ", "Departure terminal operator"),

    /**
     * Party specification mutually agreed between interchanging parties.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    PartyRoleCodeType(String code, String name) {
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
