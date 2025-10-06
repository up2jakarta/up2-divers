package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.edi.adapters.ContactTypeCodeAdapter;
import io.github.up2jakarta.xml.clv.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 3139 (ContactTypeCode) : Contact function code.
 * {@see https://service.unece.org/trade/untdid/d22b/tred/tred3139.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList("3139")
@Documented(value = "Contact Function Code", agency = Agency.UN_ECE, version = "D22A")
@Schema(agency = "UN/CEFACT", version = "3.6", date = "2008-08-23")
@XmlJavaTypeAdapter(ContactTypeCodeAdapter.class)
public enum ContactTypeCodeType implements CodeList<ContactTypeCodeType> {

    /**
     * Department/person to contact for matters regarding insurance.
     */
    AA("AA", "Insurance contact"),

    /**
     * Department/person to contact for matters regarding the workshop.
     */
    AB("AB", "Workshop contact"),

    /**
     * Department/person in charge of accepting incoming goods.
     */
    AC("AC", "Accepting contact"),

    /**
     * The contact responsible for accounting matters.
     */
    AD("AD", "Accounting contact"),

    /**
     * Department/person to contact for matters regarding contracts.
     */
    AE("AE", "Contract contact"),

    /**
     * Department/person to contact for matters regarding land registry.
     */
    AF("AF", "Land registry contact"),

    /**
     * Department/person of the agent which acts on behalf of another party.
     */
    AG("AG", "Agent"),

    /**
     * Department/person to contact for matters regarding technical coordination of works.
     */
    AH("AH", "Coordination contact"),

    /**
     * Department/person to contact for matters regarding project management on behalf of the
     * contractor.
     */
    AI("AI", "Project management contact"),

    /**
     * Department/person to contact for matters regarding investments.
     */
    AJ("AJ", "Investment contact"),

    /**
     * Department/person to contact for matters regarding management of works on behalf of
     * the owner.
     */
    AK("AK", "Works management contact"),

    /**
     * Department/person to contact for matters regarding personnel (human resources).
     */
    AL("AL", "Personnel contact"),

    /**
     * Department/person to contact for matters regarding claims.
     */
    AM("AM", "Claims contact"),

    /**
     * Department/person to contact for laboratory matters.
     */
    AN("AN", "Laboratory contact"),

    /**
     * Department/person to contact for matters regarding plant/equipment.
     */
    AO("AO", "Plant/equipment contact"),

    /**
     * Department/person responsible for the accounts payable function within a corporation.
     */
    AP("AP", "Accounts payable contact"),

    /**
     * Department/person to contact for matters regarding quantity surveying.
     */
    AQ("AQ", "Quantity surveyor contact"),

    /**
     * Department/person responsible for the accounts receivable within a corporation.
     */
    AR("AR", "Accounts receivable contact"),

    /**
     * Department/person to contact for matters regarding public relations.
     */
    AS("AS", "Public relations contact"),

    /**
     * Department/person to contact for matters regarding technical issues.
     */
    AT("AT", "Technical contact"),

    /**
     * Department/person to contact for matters regarding city works.
     */
    AU("AU", "City works authority contact"),

    /**
     * Department/person to contact for matters regarding maintenance.
     */
    AV("AV", "Maintenance contact"),

    /**
     * Department/person to contact for matters regarding town planning.
     */
    AW("AW", "Town planning contact"),

    /**
     * Department/person to contact for matters regarding traffic.
     */
    AX("AX", "Traffic authority contact"),

    /**
     * Department/person to contact for matters regarding electricity supply.
     */
    AY("AY", "Electricity supply contact"),

    /**
     * Department/person to contact for matters regarding gas supply.
     */
    AZ("AZ", "Gas supply contact"),

    /**
     * Department/person to contact for matters regarding water supply.
     */
    BA("BA", "Water supply contact"),

    /**
     * Department/person to contact for matters regarding telecommunications network.
     */
    BB("BB", "Telecommunications network contact"),

    /**
     * Contact person for bank.
     */
    BC("BC", "Banking contact"),

    /**
     * Department/person to contact for matters regarding new developments (e.g. construction).
     */
    BD("BD", "New developments contact"),

    /**
     * Department/person to contact for matters regarding transport infrastructure.
     */
    BE("BE", "Transport infrastructure authority"),

    /**
     * Department/person to be contacted in service matters.
     */
    BF("BF", "Service contact"),

    /**
     * Department or person to contact with regard to auditing.
     */
    BG("BG", "Auditing contact"),

    /**
     * Department or person to contact with regard to legal auditing.
     */
    BH("BH", "Legal auditing contact"),

    /**
     * Department or person to contact with regard to software house.
     */
    BI("BI", "Software house contact"),

    /**
     * Identification of the department or person responsible for the processing of purchase
     * orders.
     */
    BJ("BJ", "Department or person responsible for processing purchase order"),

    /**
     * Code specifying a person responsible for the coordination of matters related to the
     * exchange of information in electronic data interchange format.
     */
    BK("BK", "Electronic data interchange coordinator"),

    /**
     * Code specifying a party knowledgeable about a waiver.
     */
    BL("BL", "Waiver contact"),

    /**
     * Code specifying a person to be contacted at an automated clearing house.
     */
    BM("BM", "Automated clearing house (ACH) contact"),

    /**
     * Code specifying a contact with knowledge of a certification action.
     */
    BN("BN", "Certification contact"),

    /**
     * Department/person to contact after normal working hours.
     */
    BO("BO", "After business hours contact"),

    /**
     * The round the clock contact of the Company Security Officer who is responsible for the
     * vessel.
     */
    BP("BP", "Company Security Officer’s 24-hour contact"),

    /**
     * Contact details of the agent of the ship at the intended port of arrival.
     */
    BQ("BQ", "Agent of ship at the intended port of arrival"),

    /**
     * Person responsible for cooking.
     */
    BR("BR", "Cook"),

    /**
     * The main department/person to be contacted at the customer.
     */
    BS("BS", "Customer contact"),

    /**
     * Department/person to contact for matters regarding meter reading, including access to
     * the meter.
     */
    BT("BT", "Meter access contact"),

    /**
     * Final recipient of the consignment.
     */
    BU("BU", "Ultimate consignee"),

    /**
     * (3126) Party undertaking or arranging transport of goods between named points.
     */
    CA("CA", "Carrier"),

    /**
     * Person who made the change.
     */
    CB("CB", "Changed by"),

    /**
     * Responsible person to contact for matters regarding the production of information.
     */
    CC("CC", "Responsible person for information production"),

    /**
     * Responsible person to contact for matters regarding information dissemination.
     */
    CD("CD", "Responsible person for information dissemination"),

    /**
     * Head of unit to contact for matters regarding computer data processing.
     */
    CE("CE", "Head of unit for computer data processing"),

    /**
     * Head of unit to contact for matters regarding the production of information.
     */
    CF("CF", "Head of unit for information production"),

    /**
     * Head of unit to contact for matters regarding dissemination of information.
     */
    CG("CG", "Head of unit for information dissemination"),

    /**
     * (3132) Party to which goods are consigned.
     */
    CN("CN", "Consignee"),

    /**
     * (3336) Party which, by contract with a carrier, consigns or sends goods with the carrier,
     * or has them conveyed by him. Synonym: shipper/sender.
     */
    CO("CO", "Consignor"),

    /**
     * Responsible person to contact for matters regarding computer data processing.
     */
    CP("CP", "Responsible person for computer data processing"),

    /**
     * Individual responsible for customer relations.
     */
    CR("CR", "Customer relations"),

    /**
     * Person with whom the contents of the purchase order has been discussed and agreed (e.g.
     * by telephone) prior to the sending of this message.
     */
    CW("CW", "Confirmed with"),

    /**
     * Department/employee which/who executes export procedures.
     */
    DE("DE", "Department/employee to execute export procedures"),

    /**
     * Department/employee which/who executes import procedures.
     */
    DI("DI", "Department/employee to execute import procedures"),

    /**
     * Department/person responsible for delivery.
     */
    DL("DL", "Delivery contact"),

    /**
     * Name of an individual who made the entry.
     */
    EB("EB", "Entered by"),

    /**
     * Person in charge of coordination of education.
     */
    EC("EC", "Education coordinator"),

    /**
     * Department/person to contact for matters regarding engineering.
     */
    ED("ED", "Engineering contact"),

    /**
     * The contact for expediting.
     */
    EX("EX", "Expeditor"),

    /**
     * Department/person responsible for receiving the goods at the place of delivery.
     */
    GR("GR", "Goods receiving contact"),

    /**
     * [3058] Party who is to be contacted to intervene in case of emergency.
     */
    HE("HE", "Emergency dangerous goods contact"),

    /**
     * [3060] Department/person to be contacted for details about the transportation of dangerous
     * goods/hazardous material.
     */
    HG("HG", "Dangerous goods contact"),

    /**
     * Department/person responsible for hazardous material control.
     */
    HM("HM", "Hazardous material contact"),

    /**
     * Department/person to contact for questions regarding transactions.
     */
    IC("IC", "Information contact"),

    /**
     * Department/employee to be contacted at the insurer.
     */
    IN("IN", "Insurer contact"),

    /**
     * Department/employee to be contacted at the place of delivery.
     */
    LB("LB", "Place of delivery contact"),

    /**
     * Department/employee to be contacted at the place of collection.
     */
    LO("LO", "Place of collection contact"),

    /**
     * Department/person responsible for the controlling/inspection of goods.
     */
    MC("MC", "Material control contact"),

    /**
     * Department/person responsible for the disposition/scheduling of goods.
     */
    MD("MD", "Material disposition contact"),

    /**
     * Department/employee to be contacted for material handling.
     */
    MH("MH", "Material handling contact"),

    /**
     * Department/employee to be contacted at the message recipient.
     */
    MR("MR", "Message recipient contact"),

    /**
     * Department/employee to be contacted at the message sender.
     */
    MS("MS", "Message sender contact"),

    /**
     * Department/employee to be notified.
     */
    NT("NT", "Notification contact"),

    /**
     * An individual to contact for questions regarding this order.
     */
    OC("OC", "Order contact"),

    /**
     * Department/employee to be contacted as prototype co-ordinator.
     */
    PA("PA", "Prototype coordinator"),

    /**
     * Department/person responsible for issuing this purchase order.
     */
    PD("PD", "Purchasing contact"),

    /**
     * Department/employee to be contacted at the payee.
     */
    PE("PE", "Payee contact"),

    /**
     * Department/person to contact for questions regarding this order.
     */
    PM("PM", "Product management contact"),

    /**
     * Quality assurance contact within an organization.
     */
    QA("QA", "Quality assurance contact"),

    /**
     * Quality coordinator contact within an organization.
     */
    QC("QC", "Quality coordinator contact"),

    /**
     * The receiving dock contact within an organization.
     */
    RD("RD", "Receiving dock contact"),

    /**
     * Responsible person who is authorized to sign official documents.
     */
    RP("RP", "Authorized responsible person"),

    /**
     * Name of the sales administration contact within a corporation.
     */
    SA("SA", "Sales administration"),

    /**
     * Name of the scheduling contact within a corporation.
     */
    SC("SC", "Schedule contact"),

    /**
     * The shipping department contact within an organization.
     */
    SD("SD", "Shipping contact"),

    /**
     * The sales representative or department contact within an organization.
     */
    SR("SR", "Sales representative or department"),

    /**
     * Department/person to be contacted at the supplier.
     */
    SU("SU", "Supplier contact"),

    /**
     * The traffic administrator contact within an organization.
     */
    TA("TA", "Traffic administrator"),

    /**
     * Department/person responsible for testing contact.
     */
    TD("TD", "Test contact"),

    /**
     * Department/person to receive technical documentation.
     */
    TI("TI", "Technical documentation recipient"),

    /**
     * Department/person in charge of transportation.
     */
    TR("TR", "Transport contact"),

    /**
     * The warehouse contact within an organization.
     */
    WH("WH", "Warehouse"),

    /**
     * Alternate department or person to contact.
     */
    WI("WI", "Alternate contact"),

    /**
     * An individual responsible for managing the day to day activities of an office.
     */
    WJ("WJ", "Office Manager"),

    /**
     * Code identifying a chartered accountant contact.
     */
    WK("WK", "Chartered accountant contact"),

    /**
     * A code assigned within a code list to be used on an interim basis and as defined among
     * trading partners until a precise code can be assigned to the code list.
     */
    ZZZ("ZZZ", "Mutually defined"),
    ;

    private final String name;
    private final String code;

    ContactTypeCodeType(String code, String name) {
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
