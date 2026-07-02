package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.PartyRoleCodeType;
import io.github.up2jakarta.cii.format.minified.udt.PartyIDType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradePartyType", propOrder = {
        "globalId",
        "name",
        "roleCode",
        "description",
        "specifiedLegalOrganization",
        "definedTradeContact",
        "postalTradeAddress",
        "uriUniversalCommunication",
        "specifiedTaxRegistration"
})
public class TradePartyType {

    // BT-29a, BT-29b, BT-29c, BT-29d, BT-46a, BT-46b, BT-46c, EXT-FR-FE-06a, EXT-FR-FE-06b and (18) specifications too.
    @XmlElement(name = "GlobalID")
    private List<PartyIDType> globalId;

    // BT-27, BT-44, EXT-FR-FE-03, BT-59, EXT-FR-FE-43, EXT-FR-FE-66, EXT-FR-FE-89, EXT-FR-FE-112 and (3) specifications too.
    @XmlElement(name = "Name")
    private String name;

    // EXT-FR-FE-04, EXT-FR-FE-26, EXT-FR-FE-44, EXT-FR-FE-67, EXT-FR-FE-90, EXT-FR-FE-113
    @XmlElement(name = "RoleCode")
    private PartyRoleCodeType roleCode;

    // BT-33
    @XmlElement(name = "Description")
    private String description;

    @XmlElement(name = "SpecifiedLegalOrganization")
    private LegalOrganizationType specifiedLegalOrganization;

    // BG-6, BG-9, EXT-FR-FE-22, EXT-FR-FE-39, EXT-FR-FE-62, EXT-FR-FE-85, EXT-FR-FE-108 and (1) specifications too.
    @XmlElement(name = "DefinedTradeContact")
    private TradeContactType definedTradeContact;

    // BG-5, BG-8, EXT-FR-FE-14, EXT-FR-FE-31, EXT-FR-FE-54, EXT-FR-FE-77, EXT-FR-FE-100 and (4) specifications too.
    @XmlElement(name = "PostalTradeAddress")
    private TradeAddressType postalTradeAddress;

    @XmlElement(name = "URIUniversalCommunication")
    private UniversalCommunicationType uriUniversalCommunication;

    @XmlElement(name = "SpecifiedTaxRegistration")
    private List<TaxRegistrationType> specifiedTaxRegistration;

    public List<PartyIDType> getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(List<PartyIDType> globalId) {
        this.globalId = globalId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartyRoleCodeType getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(PartyRoleCodeType roleCode) {
        this.roleCode = roleCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LegalOrganizationType getSpecifiedLegalOrganization() {
        return this.specifiedLegalOrganization;
    }

    public void setSpecifiedLegalOrganization(LegalOrganizationType specifiedLegalOrganization) {
        this.specifiedLegalOrganization = specifiedLegalOrganization;
    }

    public TradeContactType getDefinedTradeContact() {
        return this.definedTradeContact;
    }

    public void setDefinedTradeContact(TradeContactType definedTradeContact) {
        this.definedTradeContact = definedTradeContact;
    }

    public TradeAddressType getPostalTradeAddress() {
        return this.postalTradeAddress;
    }

    public void setPostalTradeAddress(TradeAddressType postalTradeAddress) {
        this.postalTradeAddress = postalTradeAddress;
    }

    public UniversalCommunicationType getUriUniversalCommunication() {
        return this.uriUniversalCommunication;
    }

    public void setUriUniversalCommunication(UniversalCommunicationType uriUniversalCommunication) {
        this.uriUniversalCommunication = uriUniversalCommunication;
    }

    public List<TaxRegistrationType> getSpecifiedTaxRegistration() {
        return this.specifiedTaxRegistration;
    }

    public void setSpecifiedTaxRegistration(List<TaxRegistrationType> specifiedTaxRegistration) {
        this.specifiedTaxRegistration = specifiedTaxRegistration;
    }

}
