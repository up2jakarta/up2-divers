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
@XmlAccessorType(XmlAccessType.PROPERTY)
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
    private List<PartyIDType> globalId;

    // BT-27, BT-44, EXT-FR-FE-03, BT-59, EXT-FR-FE-43, EXT-FR-FE-66, EXT-FR-FE-89, EXT-FR-FE-112 and (3) specifications too.
    private String name;

    // EXT-FR-FE-04, EXT-FR-FE-26, EXT-FR-FE-44, EXT-FR-FE-67, EXT-FR-FE-90, EXT-FR-FE-113
    private PartyRoleCodeType roleCode;

    // BT-33
    private String description;

    private LegalOrganizationType specifiedLegalOrganization;

    // BG-6, BG-9, EXT-FR-FE-22, EXT-FR-FE-39, EXT-FR-FE-62, EXT-FR-FE-85, EXT-FR-FE-108 and (1) specifications too.
    private List<TradeContactType> definedTradeContact;

    // BG-5, BG-8, EXT-FR-FE-14, EXT-FR-FE-31, EXT-FR-FE-54, EXT-FR-FE-77, EXT-FR-FE-100 and (4) specifications too.
    private TradeAddressType postalTradeAddress;

    private UniversalCommunicationType uriUniversalCommunication;

    private TaxRegistrationType specifiedTaxRegistration;

    @XmlElement(name = "GlobalID")
    public List<PartyIDType> getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(List<PartyIDType> globalId) {
        this.globalId = globalId;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "RoleCode")
    public PartyRoleCodeType getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(PartyRoleCodeType roleCode) {
        this.roleCode = roleCode;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "SpecifiedLegalOrganization")
    public LegalOrganizationType getSpecifiedLegalOrganization() {
        return this.specifiedLegalOrganization;
    }

    public void setSpecifiedLegalOrganization(LegalOrganizationType specifiedLegalOrganization) {
        this.specifiedLegalOrganization = specifiedLegalOrganization;
    }

    @XmlElement(name = "DefinedTradeContact")
    public List<TradeContactType> getDefinedTradeContact() {
        return this.definedTradeContact;
    }

    public void setDefinedTradeContact(List<TradeContactType> definedTradeContact) {
        this.definedTradeContact = definedTradeContact;
    }

    @XmlElement(name = "PostalTradeAddress")
    public TradeAddressType getPostalTradeAddress() {
        return this.postalTradeAddress;
    }

    public void setPostalTradeAddress(TradeAddressType postalTradeAddress) {
        this.postalTradeAddress = postalTradeAddress;
    }

    @XmlElement(name = "URIUniversalCommunication")
    public UniversalCommunicationType getUriUniversalCommunication() {
        return this.uriUniversalCommunication;
    }

    public void setUriUniversalCommunication(UniversalCommunicationType uriUniversalCommunication) {
        this.uriUniversalCommunication = uriUniversalCommunication;
    }

    @XmlElement(name = "SpecifiedTaxRegistration")
    public TaxRegistrationType getSpecifiedTaxRegistration() {
        return this.specifiedTaxRegistration;
    }

    public void setSpecifiedTaxRegistration(TaxRegistrationType specifiedTaxRegistration) {
        this.specifiedTaxRegistration = specifiedTaxRegistration;
    }

}
