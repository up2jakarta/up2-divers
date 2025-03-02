package io.github.up2jakarta.cii.edi;

import io.github.up2jakarta.cii.api.Agency;
import io.github.up2jakarta.cii.api.Documented;
import io.github.up2jakarta.cii.api.Schema;
import io.github.up2jakarta.cii.api.SubList;
import io.github.up2jakarta.cii.edi.adapters.ChargePayingPartyRoleCodeAdapter;
import io.github.up2jakarta.csv.extension.CodeList;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.annotation.processing.Generated;

/**
 * SubList of UN/CEFACT 3035 (Charge Paying) : Party function code qualifier.
 * {@see https://service.unece.org/trade/untdid/d16b/tred/tred3035.htm}
 */
@Generated(value = "CII", comments = "by A.ABBESSI")
@SubList(value = "3035", type = PartyRoleCodeType.class)
@Documented(value = "Party Role Code_Charge Paying", agency = Agency.UN_ECE, version = "D22B")
@Schema(agency = "UN/CEFACT", version = "3.5", date = "2008-08-23")
@XmlJavaTypeAdapter(ChargePayingPartyRoleCodeAdapter.class)
public enum ChargePayingPartyRoleCodeType implements CodeList<ChargePayingPartyRoleCodeType> {

    /**
     * Third party who arranged the purchase of merchandise on behalf of the actual buyer.
     */
    AB(PartyRoleCodeType.AB),

    /**
     * Any natural or legal person who makes a declaration to an official body on behalf of
     * another natural or legal person, where legally permitted (CCC).
     */
    AE(PartyRoleCodeType.AE),

    /**
     * Natural or legal person responsible for the satisfactory performance of a Customs transit
     * operation. Source: CCC.
     */
    AF(PartyRoleCodeType.AF),

    /**
     * Agent acting on behalf of the transit principal (CCC).
     */
    AH(PartyRoleCodeType.AH),

    /**
     * Person or company approved by the relevant authority in the country to pack and export
     * specific goods under Customs supervision.
     */
    AQ(PartyRoleCodeType.AQ),

    /**
     * Exporter authorized/approved by Customs for special Customs procedures e.g. simplified
     * procedure.
     */
    AR(PartyRoleCodeType.AR),

    /**
     * Importer authorized/approved by Customs for special Customs procedures e.g. simplified
     * procedure.
     */
    AT(PartyRoleCodeType.AT),

    /**
     * Trader authorized/approved by Customs for special transit procedures e.g. simplified
     * procedure.
     */
    AU(PartyRoleCodeType.AU),

    /**
     * [3126] Party undertaking or arranging transport of goods between named points.
     */
    CA(PartyRoleCodeType.CA),

    /**
     * [3052] Party authorized to act for or on behalf of carrier.
     */
    CG(PartyRoleCodeType.CG),

    /**
     * [3132] Party to which goods are consigned.
     */
    CN(PartyRoleCodeType.CN),

    /**
     * Party, other than the ordering party, which has to pay the charges concerning the destination
     * operations.
     */
    CPD(PartyRoleCodeType.CPD),

    /**
     * Party authorized to act on behalf of the consignee.
     */
    CX(PartyRoleCodeType.CX),

    /**
     * [3336] Party which, by contract with a carrier, consigns or sends goods with the carrier,
     * or has them conveyed by him. Synonym: shipper, sender.
     */
    CZ(PartyRoleCodeType.CZ),

    /**
     * Party to whom the invoice is sent and who processes the invoice on behalf of the invoicee.
     * Note, the invoicee is legally responsible for the invoice and can be different to the
     * processing party.
     */
    DGB(PartyRoleCodeType.DGB),

    /**
     * [3030] Party who makes, or on whose behalf the export declaration is made, and who
     * is the owner of the goods or has similar rights of disposal over them at the time when
     * the declaration is accepted.
     */
    EX(PartyRoleCodeType.EX),

    /**
     * [3170] Party arranging forwarding of goods.
     */
    FW(PartyRoleCodeType.FW),

    /**
     * Party authorised to represent the consignor.
     */
    GS(PartyRoleCodeType.GS),

    /**
     * [3020] Party who makes - or on whose behalf a Customs clearing agent or other authorized
     * person makes - an import declaration. This may include a person who has possession
     * of the goods or to whom the goods are consigned.
     */
    IM(PartyRoleCodeType.IM),

    /**
     * [3006] Party to whom an invoice is issued.
     */
    IV(PartyRoleCodeType.IV),

    /**
     * Identifies the credit party when other than the beneficiary.
     */
    PE(PartyRoleCodeType.PE),
    ;

    private final String name;
    private final String code;

    ChargePayingPartyRoleCodeType(PartyRoleCodeType cl) {
        this.code = cl.getCode();
        this.name = cl.getName();
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
