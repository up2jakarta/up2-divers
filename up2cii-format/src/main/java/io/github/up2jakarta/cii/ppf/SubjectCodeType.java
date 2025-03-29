package io.github.up2jakarta.cii.ppf;

import io.github.up2jakarta.cii.ppf.adapters.SubjectCodeAdapter;
import io.github.up2jakarta.xml.codelist.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 4451 (SubjectCode) : Text subject code qualifier.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred4451.htm}
 */
@Generated(value = "PPF", comments = "by A.ABBESSI")
@SubList("4451")
@Documented(value = "Text subject code qualifier", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.5", date = "2008-08-23")
@XmlJavaTypeAdapter(SubjectCodeAdapter.class)
public enum SubjectCodeType implements CodeList<SubjectCodeType> {

    /**
     * [7002] Plain language description of the nature of a goods item sufficient to identify
     * it for customs, statistical or transport purposes.
     */
    AAA("AAA", "Goods item description"),

    /**
     * [4276] Free form description of the conditions of payment between the parties to a transaction.
     */
    AAB("AAB", "Payment term"),

    /**
     * [7488] Additional information concerning dangerous substances and/or article in a consignment.
     */
    AAC("AAC", "Dangerous goods additional information"),

    /**
     * [7254] Proper shipping name, supplemented as necessary with the correct technical name,
     * by which a dangerous substance or article may be correctly identified, or which is sufficiently
     * informative to permit identification by reference to generally available literature.
     */
    AAD("AAD", "Dangerous goods technical name"),

    /**
     * The content of an acknowledgement.
     */
    AAE("AAE", "Acknowledgement description"),

    /**
     * Specific details applying to rates.
     */
    AAF("AAF", "Rate additional information"),

    /**
     * Indicates that the segment contains instructions to be passed on to the identified party.
     */
    AAG("AAG", "Party instructions"),

    /**
     * The text contains general information.
     */
    AAI("AAI", "General information"),

    /**
     * Additional conditions specific to this order or project.
     */
    AAJ("AAJ", "Additional conditions of sale/purchase"),

    /**
     * Information on the price conditions that are expected or given.
     */
    AAK("AAK", "Price conditions"),

    /**
     * Expression of a number in characters as length of ten meters.
     */
    AAL("AAL", "Goods dimensions in characters"),

    /**
     * Technical or commercial reasons why a piece of equipment may not be re-used after the
     * current transport terminates.
     */
    AAM("AAM", "Equipment re-usage restrictions"),

    /**
     * Restrictions in handling depending on the technical characteristics of the piece of
     * equipment or on the nature of the goods.
     */
    AAN("AAN", "Handling restriction"),

    /**
     * Error described by a free text.
     */
    AAO("AAO", "Error description (free text)"),

    /**
     * Free text of the response to a communication.
     */
    AAP("AAP", "Response (free text)"),

    /**
     * A description of the contents of a package.
     */
    AAQ("AAQ", "Package content's description"),

    /**
     * (4053) Free text of the non Incoterms terms of delivery. For Incoterms, use: 4053.
     */
    AAR("AAR", "Terms of delivery"),

    /**
     * The remarks printed or to be printed on a bill of lading.
     */
    AAS("AAS", "Bill of lading remarks"),

    /**
     * Free text information on an IATA Air Waybill to indicate means by which account is to
     * be settled.
     */
    AAT("AAT", "Mode of settlement information"),

    /**
     * Information pertaining to the invoice covering the consignment.
     */
    AAU("AAU", "Consignment invoice information"),

    /**
     * Information pertaining to the invoice covering clearance of the cargo.
     */
    AAV("AAV", "Clearance invoice information"),

    /**
     * Information pertaining to the letter of credit.
     */
    AAW("AAW", "Letter of credit information"),

    /**
     * Information pertaining to a license.
     */
    AAX("AAX", "License information"),

    /**
     * The text contains certification statements.
     */
    AAY("AAY", "Certification statements"),

    /**
     * The text contains additional export information.
     */
    AAZ("AAZ", "Additional export information"),

    /**
     * Description of parameters relating to a tariff.
     */
    ABA("ABA", "Tariff statements"),

    /**
     * Historical details of a patients medical events.
     */
    ABB("ABB", "Medical history"),

    /**
     * (4490) (4372) Additional information regarding terms and conditions which apply to the
     * transaction.
     */
    ABC("ABC", "Conditions of sale or purchase"),

    /**
     * [4422] Textual representation of the type of contract.
     */
    ABD("ABD", "Contract document type"),

    /**
     * (4260) Additional terms and/or conditions to the documentary credit.
     */
    ABE("ABE", "Additional terms and/or conditions (documentary credit)"),

    /**
     * Instruction or information about a standby documentary credit.
     */
    ABF("ABF", "Instructions or information about standby documentary credit"),

    /**
     * Instructions or information about partial shipment(s).
     */
    ABG("ABG", "Instructions or information about partial shipment(s)"),

    /**
     * Instructions or information about transhipment(s).
     */
    ABH("ABH", "Instructions or information about transhipment(s)"),

    /**
     * Additional handling instructions for a documentary credit.
     */
    ABI("ABI", "Additional handling instructions documentary credit"),

    /**
     * Information regarding the domestic routing.
     */
    ABJ("ABJ", "Domestic routing information"),

    /**
     * Equipment types are coded by category for financial purposes.
     */
    ABK("ABK", "Chargeable category of equipment"),

    /**
     * Information pertaining to government.
     */
    ABL("ABL", "Government information"),

    /**
     * The text contains onward routing information.
     */
    ABM("ABM", "Onward routing information"),

    /**
     * [4410] The text contains information related to accounting.
     */
    ABN("ABN", "Accounting information"),

    /**
     * Free text or coded information to indicate a specific discrepancy.
     */
    ABO("ABO", "Discrepancy information"),

    /**
     * Documentary credit confirmation instructions.
     */
    ABP("ABP", "Confirmation instructions"),

    /**
     * Method of issuance of documentary credit.
     */
    ABQ("ABQ", "Method of issuance"),

    /**
     * Delivery instructions for documents required under a documentary credit.
     */
    ABR("ABR", "Documents delivery instructions"),

    /**
     * Additional conditions to the issuance of a documentary credit.
     */
    ABS("ABS", "Additional conditions"),

    /**
     * Additional amounts information/instruction.
     */
    ABT("ABT", "Information/instructions about additional amounts covered"),

    /**
     * Additional terms concerning deferred payment.
     */
    ABU("ABU", "Deferred payment termed additional"),

    /**
     * Additional terms concerning acceptance.
     */
    ABV("ABV", "Acceptance terms additional"),

    /**
     * Additional terms concerning negotiation.
     */
    ABW("ABW", "Negotiation terms additional"),

    /**
     * Document name and documentary requirements.
     */
    ABX("ABX", "Document name and documentary requirements"),

    /**
     * Instructions/information about a revolving documentary credit.
     */
    ABZ("ABZ", "Instructions/information about revolving documentary credit"),

    /**
     * Specification of the documentary requirements.
     */
    ACA("ACA", "Documentary requirements"),

    /**
     * (4270) The text contains additional information.
     */
    ACB("ACB", "Additional information"),

    /**
     * Assignment based on an agreement between seller and factor.
     */
    ACC("ACC", "Factor assignment clause"),

    /**
     * Reason for a request or response.
     */
    ACD("ACD", "Reason"),

    /**
     * A notice, usually from buyer to seller, that something was found wrong with goods delivered
     * or the services rendered, or with the related invoice.
     */
    ACE("ACE", "Dispute"),

    /**
     * The text refers to information about an additional attribute not otherwise specified.
     */
    ACF("ACF", "Additional attribute information"),

    /**
     * A declaration on the reason of the absence.
     */
    ACG("ACG", "Absence declaration"),

    /**
     * A statement on the way a specific variable or set of variables has been aggregated.
     */
    ACH("ACH", "Aggregation statement"),

    /**
     * A statement on the compilation status of an array or other set of figures or calculations.
     */
    ACI("ACI", "Compilation statement"),

    /**
     * An exception to the agreed definition of a term, concept, formula or other object.
     */
    ACJ("ACJ", "Definitional exception"),

    /**
     * A statement on the privacy or confidential nature of an object.
     */
    ACK("ACK", "Privacy statement"),

    /**
     * A statement on the quality of an object.
     */
    ACL("ACL", "Quality statement"),

    /**
     * The description of a statistical object such as a value list, concept, or structure
     * definition.
     */
    ACM("ACM", "Statistical description"),

    /**
     * The definition of a statistical object such as a value list, concept, or structure definition.
     */
    ACN("ACN", "Statistical definition"),

    /**
     * The name of a statistical object such as a value list, concept or structure definition.
     */
    ACO("ACO", "Statistical name"),

    /**
     * The title of a statistical object such as a value list, concept, or structure definition.
     */
    ACP("ACP", "Statistical title"),

    /**
     * Information relating to differences between the actual transport dimensions and the
     * normally applicable dimensions.
     */
    ACQ("ACQ", "Off-dimension information"),

    /**
     * Information relating to unexpected stops during a conveyance.
     */
    ACR("ACR", "Unexpected stops information"),

    /**
     * Text subject is principles section of the UN/EDIFACT rules for presentation of standardized
     * message and directories documentation.
     */
    ACS("ACS", "Principles"),

    /**
     * Text subject is terms and definition section of the UN/EDIFACT rules for presentation
     * of standardized message and directories documentation.
     */
    ACT("ACT", "Terms and definition"),

    /**
     * Text subject is segment name.
     */
    ACU("ACU", "Segment name"),

    /**
     * Text subject is name of simple data element.
     */
    ACV("ACV", "Simple data element name"),

    /**
     * Text subject is scope section of the UN/EDIFACT rules for presentation of standardized
     * message and directories documentation.
     */
    ACW("ACW", "Scope"),

    /**
     * Text subject is name of message type.
     */
    ACX("ACX", "Message type name"),

    /**
     * Text subject is introduction section of the UN/EDIFACT rules for presentation of standardized
     * message and directories documentation.
     */
    ACY("ACY", "Introduction"),

    /**
     * Text subject is glossary section of the UN/EDIFACT rules for presentation of standardized
     * message and directories documentation.
     */
    ACZ("ACZ", "Glossary"),

    /**
     * Text subject is functional definition section of the UN/EDIFACT rules for presentation
     * of standardized message and directories documentation.
     */
    ADA("ADA", "Functional definition"),

    /**
     * Text subject is examples as given in the example(s) section of the UN/EDIFACT rules
     * for presentation of standardized message and directories documentation.
     */
    ADB("ADB", "Examples"),

    /**
     * Text subject is cover page of the UN/EDIFACT rules for presentation of standardized
     * message and directories documentation.
     */
    ADC("ADC", "Cover page"),

    /**
     * Denotes that the associated text is a dependency (syntax) note.
     */
    ADD("ADD", "Dependency (syntax) notes"),

    /**
     * Text subject is name of code value.
     */
    ADE("ADE", "Code value name"),

    /**
     * Text subject is name of code list.
     */
    ADF("ADF", "Code list name"),

    /**
     * Text subject is an explanation of the intended usage of a segment or segment group.
     */
    ADG("ADG", "Clarification of usage"),

    /**
     * Text subject is name of composite data element.
     */
    ADH("ADH", "Composite data element name"),

    /**
     * Text subject is field of application of the UN/EDIFACT rules for presentation of standardized
     * message and directories documentation.
     */
    ADI("ADI", "Field of application"),

    /**
     * Information describing the type of assets and liabilities.
     */
    ADJ("ADJ", "Type of assets and liabilities"),

    /**
     * The text contains information about a promotion.
     */
    ADK("ADK", "Promotion information"),

    /**
     * Description of the condition of a meter.
     */
    ADL("ADL", "Meter condition"),

    /**
     * Information related to a particular reading of a meter.
     */
    ADM("ADM", "Meter reading information"),

    /**
     * Information describing the type of the reason of transaction.
     */
    ADN("ADN", "Type of transaction reason"),

    /**
     * Type of survey question.
     */
    ADO("ADO", "Type of survey question"),

    /**
     * Information for use at the counter of the carrier's agent.
     */
    ADP("ADP", "Carrier's agent counter information"),

    /**
     * Description or code for the operation to be executed on the equipment.
     */
    ADQ("ADQ", "Description of work item on equipment"),

    /**
     * Text subject is message definition.
     */
    ADR("ADR", "Message definition"),

    /**
     * Information pertaining to a booked item.
     */
    ADS("ADS", "Booked item information"),

    /**
     * Text subject is source of document.
     */
    ADT("ADT", "Source of document"),

    /**
     * Text subject is note.
     */
    ADU("ADU", "Note"),

    /**
     * Text subject is fixed part of segment clarification text.
     */
    ADV("ADV", "Fixed part of segment clarification text"),

    /**
     * Description of the characteristic of goods in addition to the description of the goods.
     */
    ADW("ADW", "Characteristics of goods"),

    /**
     * Special discharge instructions concerning the goods.
     */
    ADX("ADX", "Additional discharge instructions"),

    /**
     * Instructions regarding the stripping of container(s).
     */
    ADY("ADY", "Container stripping instructions"),

    /**
     * Information on the CSC (Container Safety Convention) plate that is attached to the container.
     */
    ADZ("ADZ", "CSC (Container Safety Convention) plate information"),

    /**
     * Additional remarks concerning the cargo.
     */
    AEA("AEA", "Cargo remarks"),

    /**
     * Instruction regarding the temperature control of the cargo.
     */
    AEB("AEB", "Temperature control instructions"),

    /**
     * Remarks refer to data that was expected.
     */
    AEC("AEC", "Text refers to expected data"),

    /**
     * Remarks refer to data that was received.
     */
    AED("AED", "Text refers to received data"),

    /**
     * Text subject is section clarification text.
     */
    AEE("AEE", "Section clarification text"),

    /**
     * Information given to the beneficiary.
     */
    AEF("AEF", "Information to the beneficiary"),

    /**
     * Information given to the applicant.
     */
    AEG("AEG", "Information to the applicant"),

    /**
     * Instructions made to the beneficiary.
     */
    AEH("AEH", "Instructions to the beneficiary"),

    /**
     * Instructions given to the applicant.
     */
    AEI("AEI", "Instructions to the applicant"),

    /**
     * Information about the controlled atmosphere.
     */
    AEJ("AEJ", "Controlled atmosphere"),

    /**
     * Additional information in plain text to support a take off annotation. Taking off is
     * the process of assessing the quantity work from extracting the measurement from construction
     * documentation.
     */
    AEK("AEK", "Take off annotation"),

    /**
     * Additional information in plain language to support a price variation.
     */
    AEL("AEL", "Price variation narrative"),

    /**
     * Documentary credit amendment instructions.
     */
    AEM("AEM", "Documentary credit amendment instructions"),

    /**
     * Additional information in plain language to support a standard method.
     */
    AEN("AEN", "Standard method narrative"),

    /**
     * Additional information in plain language to support the project.
     */
    AEO("AEO", "Project narrative"),

    /**
     * Additional information related to radioactive goods.
     */
    AEP("AEP", "Radioactive goods, additional information"),

    /**
     * Information given from one bank to another.
     */
    AEQ("AEQ", "Bank-to-bank information"),

    /**
     * Instructions given for reimbursement purposes.
     */
    AER("AER", "Reimbursement instructions"),

    /**
     * Identification of the reason for amending a message.
     */
    AES("AES", "Reason for amending a message"),

    /**
     * Instructions to the paying and/or accepting and/or negotiating bank.
     */
    AET("AET", "Instructions to the paying and/or accepting and/or negotiating bank"),

    /**
     * Instructions given about the interest.
     */
    AEU("AEU", "Interest instructions"),

    /**
     * Instructions about agent commission.
     */
    AEV("AEV", "Agent commission"),

    /**
     * Instructions to the remitting bank.
     */
    AEW("AEW", "Remitting bank instructions"),

    /**
     * Instructions to the bank, other than the remitting bank, involved in processing the
     * collection.
     */
    AEX("AEX", "Instructions to the collecting bank"),

    /**
     * Instructions about the collection amount.
     */
    AEY("AEY", "Collection amount instructions"),

    /**
     * Text relating to internal auditing information.
     */
    AEZ("AEZ", "Internal auditing information"),

    /**
     * Denotes that the associated text is a constraint.
     */
    AFA("AFA", "Constraint"),

    /**
     * Denotes that the associated text is a comment.
     */
    AFB("AFB", "Comment"),

    /**
     * Denotes that the associated text is a semantic note.
     */
    AFC("AFC", "Semantic note"),

    /**
     * Denotes that the associated text is an item of help text.
     */
    AFD("AFD", "Help text"),

    /**
     * Denotes that the associated text is a legend.
     */
    AFE("AFE", "Legend"),

    /**
     * A description of the structure of a batch code.
     */
    AFF("AFF", "Batch code structure"),

    /**
     * A general description of the application of a product.
     */
    AFG("AFG", "Product application"),

    /**
     * Complaint of customer.
     */
    AFH("AFH", "Customer complaint"),

    /**
     * The probable cause of fault.
     */
    AFI("AFI", "Probable cause of fault"),

    /**
     * Description of the defect.
     */
    AFJ("AFJ", "Defect description"),

    /**
     * The description of the work performed during the repair.
     */
    AFK("AFK", "Repair description"),

    /**
     * Comments relevant to a review.
     */
    AFL("AFL", "Review comments"),

    /**
     * Denotes that the associated text is a title.
     */
    AFM("AFM", "Title"),

    /**
     * An amount description in clear text.
     */
    AFN("AFN", "Description of amount"),

    /**
     * Information describing the responsibilities.
     */
    AFO("AFO", "Responsibilities"),

    /**
     * Information concerning suppliers.
     */
    AFP("AFP", "Supplier"),

    /**
     * Information concerning the region(s) where purchases are made.
     */
    AFQ("AFQ", "Purchase region"),

    /**
     * Information concerning an association of one party with another party(ies).
     */
    AFR("AFR", "Affiliation"),

    /**
     * Information concerning the borrower.
     */
    AFS("AFS", "Borrower"),

    /**
     * Information concerning an entity's line of business.
     */
    AFT("AFT", "Line of business"),

    /**
     * Description of financial institution(s) used by an entity.
     */
    AFU("AFU", "Financial institution"),

    /**
     * Information about the business founder.
     */
    AFV("AFV", "Business founder"),

    /**
     * Description of the business history.
     */
    AFW("AFW", "Business history"),

    /**
     * Information concerning the general banking arrangements.
     */
    AFX("AFX", "Banking arrangements"),

    /**
     * Description of the business origin.
     */
    AFY("AFY", "Business origin"),

    /**
     * Description of the entity's brands.
     */
    AFZ("AFZ", "Brand names' description"),

    /**
     * Details about the financing of the business.
     */
    AGA("AGA", "Business financing details"),

    /**
     * Information concerning an entity's competition.
     */
    AGB("AGB", "Competition"),

    /**
     * Details about the construction process.
     */
    AGC("AGC", "Construction process details"),

    /**
     * Information concerning the line of business of a construction entity.
     */
    AGD("AGD", "Construction specialty"),

    /**
     * Details about contract(s).
     */
    AGE("AGE", "Contract information"),

    /**
     * Details about a corporate filing.
     */
    AGF("AGF", "Corporate filing"),

    /**
     * Description of customers.
     */
    AGG("AGG", "Customer information"),

    /**
     * Information concerning the copyright notice.
     */
    AGH("AGH", "Copyright notice"),

    /**
     * Details about the contingent debt.
     */
    AGI("AGI", "Contingent debt"),

    /**
     * Details about the law or penal codes that resulted in conviction.
     */
    AGJ("AGJ", "Conviction details"),

    /**
     * Description of equipment.
     */
    AGK("AGK", "Equipment"),

    /**
     * Comments about the workforce.
     */
    AGL("AGL", "Workforce description"),

    /**
     * Description about exemptions.
     */
    AGM("AGM", "Exemption"),

    /**
     * Information on future plans.
     */
    AGN("AGN", "Future plans"),

    /**
     * Information concerning the interviewee conversation.
     */
    AGO("AGO", "Interviewee conversation information"),

    /**
     * Description of intangible asset(s).
     */
    AGP("AGP", "Intangible asset"),

    /**
     * Description of the inventory.
     */
    AGQ("AGQ", "Inventory"),

    /**
     * Description of the investments.
     */
    AGR("AGR", "Investment"),

    /**
     * Description of the intercompany relations.
     */
    AGS("AGS", "Intercompany relations information"),

    /**
     * Description of the joint venture.
     */
    AGT("AGT", "Joint venture"),

    /**
     * Description of a loan.
     */
    AGU("AGU", "Loan"),

    /**
     * Description of the long term debt.
     */
    AGV("AGV", "Long term debt"),

    /**
     * Description of a location.
     */
    AGW("AGW", "Location"),

    /**
     * Details on the current legal structure.
     */
    AGX("AGX", "Current legal structure"),

    /**
     * Details on a marital contract.
     */
    AGY("AGY", "Marital contract"),

    /**
     * Information concerning marketing activities.
     */
    AGZ("AGZ", "Marketing activities"),

    /**
     * Description of a merger.
     */
    AHA("AHA", "Merger"),

    /**
     * Description of the marketable securities.
     */
    AHB("AHB", "Marketable securities"),

    /**
     * Description of the business debt(s).
     */
    AHC("AHC", "Business debt"),

    /**
     * Information concerning the original legal structure.
     */
    AHD("AHD", "Original legal structure"),

    /**
     * Information describing how a company uses employees from another company.
     */
    AHE("AHE", "Employee sharing arrangements"),

    /**
     * Description about the organization of a company.
     */
    AHF("AHF", "Organization details"),

    /**
     * Information concerning public records.
     */
    AHG("AHG", "Public record details"),

    /**
     * Information concerning the price range of products made or sold.
     */
    AHH("AHH", "Price range"),

    /**
     * Information on the accomplishments fitting a party for a position.
     */
    AHI("AHI", "Qualifications"),

    /**
     * Information concerning the registered activity.
     */
    AHJ("AHJ", "Registered activity"),

    /**
     * Description of the sentence imposed in a criminal proceeding.
     */
    AHK("AHK", "Criminal sentence"),

    /**
     * Description of the selling means.
     */
    AHL("AHL", "Sales method"),

    /**
     * Free form description relating to the school(s) attended.
     */
    AHM("AHM", "Educational institution information"),

    /**
     * Describes the status details.
     */
    AHN("AHN", "Status details"),

    /**
     * Description of the sales.
     */
    AHO("AHO", "Sales"),

    /**
     * Information about the spouse.
     */
    AHP("AHP", "Spouse information"),

    /**
     * Details about the educational degree received from a school.
     */
    AHQ("AHQ", "Educational degree information"),

    /**
     * General description of shareholding.
     */
    AHR("AHR", "Shareholding information"),

    /**
     * Information on the sales territory.
     */
    AHS("AHS", "Sales territory"),

    /**
     * Comments made by an accountant regarding a financial statement.
     */
    AHT("AHT", "Accountant's comments"),

    /**
     * Description of the exemption provided to a location by a law.
     */
    AHU("AHU", "Exemption law location"),

    /**
     * Information about the classes or categories of shares.
     */
    AHV("AHV", "Share classifications"),

    /**
     * Description of a prediction.
     */
    AHW("AHW", "Forecast"),

    /**
     * Description of the location of an event.
     */
    AHX("AHX", "Event location"),

    /**
     * Information related to occupancy of a facility.
     */
    AHY("AHY", "Facility occupancy"),

    /**
     * Specific information provided about the importation and exportation of goods.
     */
    AHZ("AHZ", "Import and export details"),

    /**
     * Additional information about a facility.
     */
    AIA("AIA", "Additional facility information"),

    /**
     * Description of the value of inventory.
     */
    AIB("AIB", "Inventory value"),

    /**
     * Description of the education of a person.
     */
    AIC("AIC", "Education"),

    /**
     * Description of a thing that happens or takes place.
     */
    AID("AID", "Event"),

    /**
     * Information about agents the entity uses.
     */
    AIE("AIE", "Agent"),

    /**
     * Details of domestically agreed financial statement.
     */
    AIF("AIF", "Domestically agreed financial statement details"),

    /**
     * Description of other current asset.
     */
    AIG("AIG", "Other current asset description"),

    /**
     * Description of other current liability.
     */
    AIH("AIH", "Other current liability description"),

    /**
     * Description of the former line of business.
     */
    AII("AII", "Former business activity"),

    /**
     * Description of how a trading name is used.
     */
    AIJ("AIJ", "Trade name use"),

    /**
     * Description of the authorized signatory.
     */
    AIK("AIK", "Signing authority"),

    /**
     * [4376] Description of guarantee.
     */
    AIL("AIL", "Guarantee"),

    /**
     * Description of the operation of a holding company.
     */
    AIM("AIM", "Holding company operation"),

    /**
     * Information on routing of the consignment.
     */
    AIN("AIN", "Consignment routing"),

    /**
     * A letter citing any condition in dispute.
     */
    AIO("AIO", "Letter of protest"),

    /**
     * A free text question.
     */
    AIP("AIP", "Question"),

    /**
     * Free text information related to a party.
     */
    AIQ("AIQ", "Party information"),

    /**
     * Description of the boundaries of a geographical area.
     */
    AIR("AIR", "Area boundaries description"),

    /**
     * The free text contains advertisement information.
     */
    AIS("AIS", "Advertisement information"),

    /**
     * Details regarding the financial statement in free text.
     */
    AIT("AIT", "Financial statement details"),

    /**
     * Description of how to access an entity.
     */
    AIU("AIU", "Access instructions"),

    /**
     * Description of an entity's liquidity.
     */
    AIV("AIV", "Liquidity"),

    /**
     * Description of the line of credit available to an entity.
     */
    AIW("AIW", "Credit line"),

    /**
     * Text describing the terms of warranty which apply to a product or service.
     */
    AIX("AIX", "Warranty terms"),

    /**
     * Plain language description of a division of an entity.
     */
    AIY("AIY", "Division description"),

    /**
     * Instruction on how to report.
     */
    AIZ("AIZ", "Reporting instruction"),

    /**
     * The result of an examination.
     */
    AJA("AJA", "Examination result"),

    /**
     * The result of a laboratory investigation.
     */
    AJB("AJB", "Laboratory result"),

    /**
     * Information referring to allowance/charge.
     */
    ALC("ALC", "Allowance/charge information"),

    /**
     * The result of an X-ray examination.
     */
    ALD("ALD", "X-ray result"),

    /**
     * The result of a pathology investigation.
     */
    ALE("ALE", "Pathology result"),

    /**
     * Details of an intervention.
     */
    ALF("ALF", "Intervention description"),

    /**
     * Summary description of admittance.
     */
    ALG("ALG", "Summary of admittance"),

    /**
     * Details of a course of medical treatment.
     */
    ALH("ALH", "Medical treatment course detail"),

    /**
     * Details of a prognosis.
     */
    ALI("ALI", "Prognosis"),

    /**
     * Instruction given to a patient.
     */
    ALJ("ALJ", "Instruction to patient"),

    /**
     * Instruction given to a physician.
     */
    ALK("ALK", "Instruction to physician"),

    /**
     * The note implies to all documents.
     */
    ALL("ALL", "All documents"),

    /**
     * Details of medicine treatment.
     */
    ALM("ALM", "Medicine treatment"),

    /**
     * Details of medicine dosage and method of administration.
     */
    ALN("ALN", "Medicine dosage and administration"),

    /**
     * Details of when and/or where the patient is available.
     */
    ALO("ALO", "Availability of patient"),

    /**
     * Details of the reason for a requested service.
     */
    ALP("ALP", "Reason for service request"),

    /**
     * Details of the purpose of a service.
     */
    ALQ("ALQ", "Purpose of service"),

    /**
     * Conditions under which arrival takes place.
     */
    ARR("ARR", "Arrival conditions"),

    /**
     * Comment by the requester of a service.
     */
    ARS("ARS", "Service requester's comment"),

    /**
     * (4130) (4136) (4426) Name, code, password etc. given for authentication purposes.
     */
    AUT("AUT", "Authentication"),

    /**
     * The description of the location requested.
     */
    AUU("AUU", "Requested location description"),

    /**
     * The event or condition that initiates the administration of a single dose of medicine
     * or a period of treatment.
     */
    AUV("AUV", "Medicine administration condition"),

    /**
     * Information concerning a patient.
     */
    AUW("AUW", "Patient information"),

    /**
     * Action to be taken to avert possible harmful affects.
     */
    AUX("AUX", "Precautionary measure"),

    /**
     * Free text description is related to a service characteristic.
     */
    AUY("AUY", "Service characteristic"),

    /**
     * Comment about an event that is planned.
     */
    AUZ("AUZ", "Planned event comment"),

    /**
     * Comment about the expected delay.
     */
    AVA("AVA", "Expected delay comment"),

    /**
     * Comment about the requirements for transport.
     */
    AVB("AVB", "Transport requirements comment"),

    /**
     * The condition under which the approval is considered.
     */
    AVC("AVC", "Temporary approval condition"),

    /**
     * Information provided in this category will be used by the trader to make certain declarations
     * in relation to Customs Valuation.
     */
    AVD("AVD", "Customs Valuation Information"),

    /**
     * Description of the VAT margin scheme applied.
     */
    AVE("AVE", "Value Added Tax (VAT) margin scheme"),

    /**
     * Information about Maritime Declaration of Health.
     */
    AVF("AVF", "Maritime Declaration of Health"),

    /**
     * Information related to baggage tendered by a passenger, such as odd size indication,
     * tag.
     */
    BAG("BAG", "Passenger baggage information"),

    /**
     * Information about Maritime Declaration of Health.
     */
    BAH("BAH", "Maritime Declaration of Health"),

    /**
     * Address at which additional information on the product can be found.
     */
    BAI("BAI", "Additional product information address"),

    /**
     * Specification of free text information which is to be printed on a despatch advice.
     */
    BAJ("BAJ", "Information to be printed on despatch advice"),

    /**
     * Remarks concerning missing goods.
     */
    BAK("BAK", "Missing goods remarks"),

    /**
     * Information related to the non-acceptance of an order, goods or a consignment.
     */
    BAL("BAL", "Non-acceptance information"),

    /**
     * Information related to the return of items.
     */
    BAM("BAM", "Returns information"),

    /**
     * Note contains information related to sub-line item data.
     */
    BAN("BAN", "Sub-line item information"),

    /**
     * Information related to a test.
     */
    BAO("BAO", "Test information"),

    /**
     * The external link to a digital document (e.g.: URL).
     */
    BAP("BAP", "External link"),

    /**
     * Reason for Value Added Tax (VAT) exemption.
     */
    BAQ("BAQ", "VAT exemption reason"),

    /**
     * Instructions for processing.
     */
    BAR("BAR", "Processing Instructions"),

    /**
     * Instructions for relaying.
     */
    BAS("BAS", "Relay Instructions"),

    /**
     * Identifies that Special Import Measures Act applies
     */
    @Deprecated(forRemoval = true)
    BAT("BAT", "SIMA applicable"),

    /**
     * Identifies information related to an appeals program.
     */
    @Deprecated(forRemoval = true)
    BAU("BAU", "Appeals program code"),

    /**
     * Identifies if the goods are subject to a Special Import Measures Act measure.
     */
    @Deprecated(forRemoval = true)
    BAV("BAV", "SIMA subject"),

    /**
     * Identifies that surtax applies
     */
    @Deprecated(forRemoval = true)
    BAW("BAW", "Surtax applicable"),

    /**
     * Identifies that there is a security bond in hand that could theoretically be used
     * to cover Special Import Measures Act charges
     */
    @Deprecated(forRemoval = true)
    BAX("BAX", "SIMA security bond"),

    /**
     * Identifies if the goods are subject to a surtax measure
     */
    @Deprecated(forRemoval = true)
    BAY("BAY", "Surtax subject"),

    /**
     * Identifies safeguard applies
     */
    @Deprecated(forRemoval = true)
    BAZ("BAZ", "Safeguard applicable"),

    /**
     * Identifies safeguard applies
     */
    @Deprecated(forRemoval = true)
    BBA("BBA", "Safeguard applicable"),

    /**
     * Identifies if the goods are subject to a safeguard measure
     */
    @Deprecated(forRemoval = true)
    BBB("BBB", "Safeguard subject"),

    /**
     * [4180] Clause on a transport document regarding the cargo being consigned. Synonym:
     * Bill of Lading clause.
     */
    BLC("BLC", "Transport contract document clause"),

    /**
     * Instruction with the purpose of preparing the patient.
     */
    BLD("BLD", "Instruction to prepare the patient"),

    /**
     * Comment about treatment with medicine.
     */
    BLE("BLE", "Medicine treatment comment"),

    /**
     * Comment about the result of an examination.
     */
    BLF("BLF", "Examination result comment"),

    /**
     * Comment about the requested service.
     */
    BLG("BLG", "Service request comment"),

    /**
     * Details of the reason for a prescription.
     */
    BLH("BLH", "Prescription reason"),

    /**
     * Comment concerning a specified prescription.
     */
    BLI("BLI", "Prescription comment"),

    /**
     * Comment concerning a clinical investigation.
     */
    BLJ("BLJ", "Clinical investigation comment"),

    /**
     * Comment concerning the specification of a medicinal product.
     */
    BLK("BLK", "Medicinal specification comment"),

    /**
     * Comment concerning economic contribution.
     */
    BLL("BLL", "Economic contribution comment"),

    /**
     * Comment about the status of a plan.
     */
    BLM("BLM", "Status of a plan"),

    /**
     * Information regarding a random sample test.
     */
    BLN("BLN", "Random sample test information"),

    /**
     * Text subject is a period of time.
     */
    BLO("BLO", "Period of time"),

    /**
     * Information about legislation.
     */
    BLP("BLP", "Legislation"),

    /**
     * Text describing security measures that are requested to be executed (e.g. access controls,
     * supervision of ship's stores).
     */
    BLQ("BLQ", "Security measures requested"),

    /**
     * [4244] Remarks concerning the complete consignment to be printed on the transport document.
     * Synonym: Bill of Lading remark.
     */
    BLR("BLR", "Transport contract document remark"),

    /**
     * Text describing the security information as applicable at the port facility in the previous
     * port where a ship/port interface was conducted.
     */
    BLS("BLS", "Previous port of call security information"),

    /**
     * Text describing security related information (e.g security measures currently in force
     * on a vessel).
     */
    BLT("BLT", "Security information"),

    /**
     * Text describing waste related information.
     */
    BLU("BLU", "Waste information"),

    /**
     * Consumer marketing information, short description.
     */
    BLV("BLV", "B2C marketing information, short description"),

    /**
     * Trading partner marketing information, long description.
     */
    BLW("BLW", "B2B marketing information, long description"),

    /**
     * Consumer marketing information, long description.
     */
    BLX("BLX", "B2C marketing information, long description"),

    /**
     * Information on the ingredient make up of the product.
     */
    BLY("BLY", "Product ingredients"),

    /**
     * Short name of a location e.g. for display or printing purposes.
     */
    BLZ("BLZ", "Location short name"),

    /**
     * The text contains a description of the material used for packaging.
     */
    BMA("BMA", "Packaging material information"),

    /**
     * Text contains information on the material used for stuffing.
     */
    BMB("BMB", "Filler material information"),

    /**
     * Text contains information on ship-to-ship activities.
     */
    BMC("BMC", "Ship-to-ship activity information"),

    /**
     * A description of the type of material for packaging beyond the level covered by standards
     * such as UN Recommendation 21.
     */
    BMD("BMD", "Package material description"),

    /**
     * Textual representation of the markings on a consumer level package.
     */
    BME("BME", "Consumer level package marking"),

    /**
     * Identifies the specific Special Import Measures Act measure related to the goods
     */
    @Deprecated(forRemoval = true)
    BMF("BMF", "SIMA measure in force"),

    /**
     * Identifiication of how the transmission should be processed regarding submissions transmitted
     * prior to implementation of Canada Border Services Agency’s Assessment and Revenue Management
     * (CARM) project
     */
    @Deprecated(forRemoval = true)
    BMG("BMG", "Pre-CARM"),

    /**
     * Identification of the type of Special Import Measures Act measure
     */
    @Deprecated(forRemoval = true)
    BMH("BMH", "SIMA measure type"),

    /**
     * Any coded or clear instruction agreed by customer and carrier regarding the declaration
     * of the goods.
     */
    CCI("CCI", "Customs clearance instructions"),

    /**
     * Code which identifies a secondary form type
     */
    @Deprecated(forRemoval = true)
    CCJ("CCJ", "Sub Type Code"),

    /**
     * Additional information detailing Special Import Measures Act information
     */
    @Deprecated(forRemoval = true)
    CCK("CCK", "SIMA information"),

    /**
     * The date the goods exited the economy or warehouse
     */
    @Deprecated(forRemoval = true)
    CCL("CCL", "Time limit end"),

    /**
     * The date the goods entered the economy or warehouse
     */
    @Deprecated(forRemoval = true)
    CCM("CCM", "Time limit start"),

    /**
     * The amount of time goods may remain in the warehouse
     */
    @Deprecated(forRemoval = true)
    CCN("CCN", "Warehouse time limit"),

    /**
     * Additional information detailing the basis on which the value for duty was determined
     */
    @Deprecated(forRemoval = true)
    CCO("CCO", "Value for duty information"),

    /**
     * Any coded or clear instruction agreed by customer and carrier regarding the export declaration
     * of the goods.
     */
    CEX("CEX", "Customs clearance instructions export"),

    /**
     * Note contains change information.
     */
    CHG("CHG", "Change information"),

    /**
     * Any coded or clear instruction agreed by customer and carrier regarding the import declaration
     * of the goods.
     */
    CIP("CIP", "Customs clearance instruction import"),

    /**
     * Name of the place where Customs clearance is asked to be executed as requested by the
     * consignee/consignor.
     */
    CLP("CLP", "Clearance place requested"),

    /**
     * Instructions concerning the loading of the container.
     */
    CLR("CLR", "Loading remarks"),

    /**
     * Additional information related to an order.
     */
    COI("COI", "Order information"),

    /**
     * Remarks from or for a supplier of goods or services.
     */
    CUR("CUR", "Customer remarks"),

    /**
     * (4034) Note contains customs declaration information.
     */
    CUS("CUS", "Customs declaration information"),

    /**
     * Remarks concerning damage on the cargo.
     */
    DAR("DAR", "Damage remarks"),

    /**
     * [4020] Text of a declaration made by the issuer of a document.
     */
    DCL("DCL", "Document issuer declaration"),

    /**
     * Information about delivery.
     */
    DEL("DEL", "Delivery information"),

    /**
     * [4492] Instructions regarding the delivery of the cargo.
     */
    DIN("DIN", "Delivery instructions"),

    /**
     * Instructions pertaining to the documentation.
     */
    DOC("DOC", "Documentation instructions"),

    /**
     * The text contains a statement constituting a duty declaration.
     */
    DUT("DUT", "Duty declaration"),

    /**
     * Physical route effectively used for the movement of the means of transport.
     */
    EUR("EUR", "Effective used routing"),

    /**
     * The first block of text to be printed on the transport contract.
     */
    FBC("FBC", "First block to be printed on the transport contract"),

    /**
     * Free text information on a transport document to indicate payment information by Government
     * Bill of Lading.
     */
    GBL("GBL", "Government bill of lading information"),

    /**
     * Note is general in nature, applies to entire transaction segment.
     */
    GEN("GEN", "Entire transaction set"),

    /**
     * Special permission for road transport of certain goods in the German dangerous goods
     * regulation for road transport.
     */
    GS7("GS7", "Further information concerning GGVS par. 7"),

    /**
     * [4078] Free form description of a set of handling instructions. For example how specified
     * goods, packages or transport equipment (container) should be handled.
     */
    HAN("HAN", "Consignment handling instruction"),

    /**
     * Information pertaining to a hazard.
     */
    HAZ("HAZ", "Hazard information"),

    /**
     * [4070] Any remarks given for the information of the consignee.
     */
    ICN("ICN", "Consignment information for consignee"),

    /**
     * (4112) Instructions regarding the cargo insurance.
     */
    IIN("IIN", "Insurance instructions"),

    /**
     * Instructions as to which freight and charges components have to be mailed to whom.
     */
    IMI("IMI", "Invoice mailing instructions"),

    /**
     * Free text describing goods on a commercial invoice line.
     */
    IND("IND", "Commercial invoice item description"),

    /**
     * Specific note contains insurance information.
     */
    INS("INS", "Insurance information"),

    /**
     * Note contains invoice instructions.
     */
    INV("INV", "Invoice instruction"),

    /**
     * Data entered by railway stations when required, e.g. specified trains, additional sheets
     * for freight calculations, special measures, etc.
     */
    IRP("IRP", "Information for railway purpose"),

    /**
     * Information concerning the pre-carriage to the port of discharge if by other means than
     * a vessel.
     */
    ITR("ITR", "Inland transport details"),

    /**
     * Instructions regarding the testing that is required to be carried out on the items in
     * the transaction.
     */
    ITS("ITS", "Testing instructions"),

    /**
     * Alternative name for a location.
     */
    LAN("LAN", "Location Alias"),

    /**
     * Note contains line item information.
     */
    LIN("LIN", "Line item"),

    /**
     * [4080] Instructions where specified packages or containers are to be loaded on a means
     * of transport.
     */
    LOI("LOI", "Loading instruction"),

    /**
     * Free text accounting information on an IATA Air Waybill to indicate payment information
     * by Miscellaneous charge order.
     */
    MCO("MCO", "Miscellaneous charge order"),

    /**
     * Information about Maritime Declaration of Health.
     */
    MDH("MDH", "Maritime Declaration of Health"),

    /**
     * Additional information regarding the marks and numbers.
     */
    MKS("MKS", "Additional marks/numbers information"),

    /**
     * Free text contains order instructions.
     */
    ORI("ORI", "Order instruction"),

    /**
     * General information created by the sender of general or specific value.
     */
    OSI("OSI", "Other service information"),

    /**
     * Information regarding the packaging and/or marking of goods.
     */
    PAC("PAC", "Packing/marking information"),

    /**
     * The free text contains payment instructions information relevant to the message.
     */
    PAI("PAI", "Payment instructions information"),

    /**
     * Note contains payables information.
     */
    PAY("PAY", "Payables information"),

    /**
     * Note contains packaging information.
     */
    PKG("PKG", "Packaging information"),

    /**
     * The text contains packaging terms information.
     */
    PKT("PKT", "Packaging terms information"),

    /**
     * The free text contains payment details.
     */
    PMD("PMD", "Payment detail/remittance information"),

    /**
     * (4438) Note contains payments information.
     */
    PMT("PMT", "Payment information"),

    /**
     * The text contains product information.
     */
    PRD("PRD", "Product information"),

    /**
     * Additional information regarding the price formula used for calculating the item price.
     */
    PRF("PRF", "Price calculation formula"),

    /**
     * (4218) Note contains priority information.
     */
    PRI("PRI", "Priority information"),

    /**
     * Note contains purchasing information.
     */
    PUR("PUR", "Purchasing information"),

    /**
     * Instructions regarding quarantine, i.e. the period during which an arriving vessel,
     * including its equipment, cargo, crew or passengers, suspected to carry or carrying a
     * contagious disease is detained in strict isolation to prevent the spread of such a disease.
     */
    QIN("QIN", "Quarantine instructions"),

    /**
     * Specification of the quality/performance expectations or standards to which the items
     * must conform.
     */
    QQD("QQD", "Quality demands/requirements"),

    /**
     * Note contains quotation information.
     */
    QUT("QUT", "Quotation instruction/information"),

    /**
     * Information concerning risks induced by the goods and/or handling instruction.
     */
    RAH("RAH", "Risk and handling information"),

    /**
     * The free text contains information for regulatory authority.
     */
    REG("REG", "Regulatory information"),

    /**
     * Free text information on an IATA Air Waybill to indicate consignment returned because
     * of non delivery.
     */
    RET("RET", "Return to origin information"),

    /**
     * The text contains receivables information.
     */
    REV("REV", "Receivables"),

    /**
     * [3050] Description of a route to be used for the transport of goods.
     */
    RQR("RQR", "Consignment route"),

    /**
     * The text contains safety information.
     */
    SAF("SAF", "Safety information"),

    /**
     * [4284] Instructions given and declarations made by the sender to the carrier concerning
     * Customs, insurance, and other formalities.
     */
    SIC("SIC", "Consignment documentary instruction"),

    /**
     * Special instructions like licence no, high value, handle with care, glass.
     */
    SIN("SIN", "Special instructions"),

    /**
     * Shipping line requested to be used for traffic between European continent and U.K. for
     * Ireland.
     */
    SLR("SLR", "Ship line requested"),

    /**
     * Statement that a special permission has been obtained for the transport (and/or routing)
     * in general, and reference to such permission.
     */
    SPA("SPA", "Special permission for transport, generally"),

    /**
     * Statement that a special permission has been obtained for the transport (and/or routing)
     * of the goods specified, and reference to such permission.
     */
    SPG("SPG", "Special permission concerning the goods to be transported"),

    /**
     * Note contains special handling information.
     */
    SPH("SPH", "Special handling"),

    /**
     * Statement that a special permission has been obtained for the packaging, and reference
     * to such permission.
     */
    SPP("SPP", "Special permission concerning package"),

    /**
     * Statement that a special permission has been obtained for the use of the means transport,
     * and reference to such permission.
     */
    SPT("SPT", "Special permission concerning transport means"),

    /**
     * Number(s) of subsidiary risks, induced by the goods, according to the valid classification.
     */
    SRN("SRN", "Subsidiary risk number (IATA/DGR)"),

    /**
     * Request for a special service concerning the transport of the goods.
     */
    SSR("SSR", "Special service request"),

    /**
     * Remarks from or for a supplier of goods or services.
     */
    SUR("SUR", "Supplier remarks"),

    /**
     * [5430] Free text specification of tariff applied to a consignment.
     */
    TCA("TCA", "Consignment tariff"),

    /**
     * [8012] Transport information for commercial purposes (generic term).
     */
    TDT("TDT", "Consignment transport"),

    /**
     * General information regarding the transport of the cargo.
     */
    TRA("TRA", "Transportation information"),

    /**
     * Stipulation of the tariffs to be applied showing, where applicable, special agreement
     * numbers or references.
     */
    TRR("TRR", "Requested tariff"),

    /**
     * The text contains a statement constituting a tax declaration.
     */
    TXD("TXD", "Tax declaration"),

    /**
     * Note contains warehouse information.
     */
    WHI("WHI", "Warehouse instruction/information"),

    /**
     * Note contains information mutually defined by trading partners.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    SubjectCodeType(String code, String name) {
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
