package io.github.up2jakarta.cii.format.minified.ram;

import io.github.up2jakarta.cii.edi.PaymentMeansCodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import javax.annotation.processing.Generated;

@Generated(value = "v3.0", comments = "by A.ABBESSI")
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "TradeSettlementPaymentMeansType", propOrder = {
        "typeCode",
        "information",
        "applicableTradeSettlementFinancialCard",
        "payerPartyDebtorFinancialAccount",
        "payeePartyCreditorFinancialAccount",
        "payeeSpecifiedCreditorFinancialInstitution"
})
public class TradeSettlementPaymentMeansType {

    // BT-81
    private PaymentMeansCodeType typeCode;

    // BT-82
    private String information;

    // BG-18
    private TradeSettlementFinancialCardType applicableTradeSettlementFinancialCard;

    private DebtorFinancialAccountType payerPartyDebtorFinancialAccount;

    // BG-17
    private CreditorFinancialAccountType payeePartyCreditorFinancialAccount;

    private CreditorFinancialInstitutionType payeeSpecifiedCreditorFinancialInstitution;

    @XmlElement(name = "TypeCode")
    public PaymentMeansCodeType getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(PaymentMeansCodeType typeCode) {
        this.typeCode = typeCode;
    }

    @XmlElement(name = "Information")
    public String getInformation() {
        return this.information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @XmlElement(name = "ApplicableTradeSettlementFinancialCard")
    public TradeSettlementFinancialCardType getApplicableTradeSettlementFinancialCard() {
        return this.applicableTradeSettlementFinancialCard;
    }

    public void setApplicableTradeSettlementFinancialCard(TradeSettlementFinancialCardType applicableTradeSettlementFinancialCard) {
        this.applicableTradeSettlementFinancialCard = applicableTradeSettlementFinancialCard;
    }

    @XmlElement(name = "PayerPartyDebtorFinancialAccount")
    public DebtorFinancialAccountType getPayerPartyDebtorFinancialAccount() {
        return this.payerPartyDebtorFinancialAccount;
    }

    public void setPayerPartyDebtorFinancialAccount(DebtorFinancialAccountType payerPartyDebtorFinancialAccount) {
        this.payerPartyDebtorFinancialAccount = payerPartyDebtorFinancialAccount;
    }

    @XmlElement(name = "PayeePartyCreditorFinancialAccount")
    public CreditorFinancialAccountType getPayeePartyCreditorFinancialAccount() {
        return this.payeePartyCreditorFinancialAccount;
    }

    public void setPayeePartyCreditorFinancialAccount(CreditorFinancialAccountType payeePartyCreditorFinancialAccount) {
        this.payeePartyCreditorFinancialAccount = payeePartyCreditorFinancialAccount;
    }

    @XmlElement(name = "PayeeSpecifiedCreditorFinancialInstitution")
    public CreditorFinancialInstitutionType getPayeeSpecifiedCreditorFinancialInstitution() {
        return this.payeeSpecifiedCreditorFinancialInstitution;
    }

    public void setPayeeSpecifiedCreditorFinancialInstitution(CreditorFinancialInstitutionType payeeSpecifiedCreditorFinancialInstitution) {
        this.payeeSpecifiedCreditorFinancialInstitution = payeeSpecifiedCreditorFinancialInstitution;
    }

}
