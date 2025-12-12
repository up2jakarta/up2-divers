package io.github.up2jakarta.test.cl;

import io.github.up2jakarta.cii.edi.*;
import org.junit.jupiter.api.Test;

import static io.github.up2jakarta.test.api.TestUtil.assertUniqueness;

public class CefactCheckingUniquenessTests {

    /**
     * @see PaymentMeansCodeType
     */
    @Test
    public void tred4461() {
        assertUniqueness(PaymentMeansCodeType.class, PaymentMeansCodeType::getCode);
    }

    /**
     * @see ReferenceCodeType
     */
    @Test
    public void tred1153() {
        assertUniqueness(ReferenceCodeType.class, ReferenceCodeType::getCode);
    }

    /**
     * @see DocumentCodeType
     */
    @Test
    public void tred1001() {
        assertUniqueness(DocumentCodeType.class, DocumentCodeType::getCode);
    }

    /**
     * @see AccountingDocumentCodeType
     */
    @Test
    public void tred1001Accounting() {
        assertUniqueness(AccountingDocumentCodeType.class, AccountingDocumentCodeType::getCode);
    }

    /**
     * @see TaxCategoryCodeType
     */
    @Test
    public void tred5305() {
        assertUniqueness(TaxCategoryCodeType.class, TaxCategoryCodeType::getCode);
    }

    /**
     * @see TaxTypeCodeType
     */
    @Test
    public void tred5153() {
        assertUniqueness(TaxTypeCodeType.class, TaxTypeCodeType::getCode);
    }

    /**
     * @see PartyRoleCodeType
     */
    @Test
    public void tred3035() {
        assertUniqueness(PartyRoleCodeType.class, PartyRoleCodeType::getCode);
    }

    /**
     * @see ChargePayingPartyRoleCodeType
     */
    @Test
    public void tred3035ChargePaying() {
        assertUniqueness(ChargePayingPartyRoleCodeType.class, ChargePayingPartyRoleCodeType::getCode);
    }

    /**
     * @see CommunicationChannelCodeType
     */
    @Test
    public void tred3155() {
        assertUniqueness(CommunicationChannelCodeType.class, CommunicationChannelCodeType::getCode);
    }

    /**
     * @see LineStatusCodeType
     */
    @Test
    public void tred1229() {
        assertUniqueness(LineStatusCodeType.class, LineStatusCodeType::getCode);
    }

    /**
     * @see DocumentStatusCodeType
     */
    @Test
    public void tred1373() {
        assertUniqueness(DocumentStatusCodeType.class, DocumentStatusCodeType::getCode);
    }

    /**
     * @see ContactTypeCodeType
     */
    @Test
    public void tred3139() {
        assertUniqueness(ContactTypeCodeType.class, ContactTypeCodeType::getCode);
    }

    /**
     * @see PriceTypeCodeType
     */
    @Test
    public void tred5375() {
        assertUniqueness(PriceTypeCodeType.class, PriceTypeCodeType::getCode);
    }

    /**
     * @see TimePointFormatCodeType
     */
    @Test
    public void tred2379() {
        assertUniqueness(TimePointFormatCodeType.class, TimePointFormatCodeType::getCode);
    }

    /**
     * @see TimeReferenceCodeType
     */
    @Test
    public void tred2475() {
        assertUniqueness(TimeReferenceCodeType.class, TimeReferenceCodeType::getCode);
    }

    /**
     * @see PaymentTermsEventTimeReferenceCodeType
     */
    @Test
    public void tred2475PaymentTermsEvent() {
        assertUniqueness(PaymentTermsEventTimeReferenceCodeType.class, PaymentTermsEventTimeReferenceCodeType::getCode);
    }

    /**
     * @see TransportEquipmentCategoryCodeType
     */
    @Test
    public void tred8053() {
        assertUniqueness(TransportEquipmentCategoryCodeType.class, TransportEquipmentCategoryCodeType::getCode);
    }

    /**
     * @see TransportEquipmentFullnessCodeType
     */
    @Test
    public void tred8169() {
        assertUniqueness(TransportEquipmentFullnessCodeType.class, TransportEquipmentFullnessCodeType::getCode);
    }

    /**
     * @see TransportEquipmentSizeTypeCodeType
     */
    @Test
    public void tred8155() {
        assertUniqueness(TransportEquipmentSizeTypeCodeType.class, TransportEquipmentSizeTypeCodeType::getCode);
    }

    /**
     * @see TransportMovementStageCodeType
     */
    @Test
    public void tred8051() {
        assertUniqueness(TransportMovementStageCodeType.class, TransportMovementStageCodeType::getCode);
    }

    /**
     * @see DimensionTypeCodeType
     */
    @Test
    public void tred6145() {
        assertUniqueness(DimensionTypeCodeType.class, DimensionTypeCodeType::getCode);
    }

    /**
     * @see PackageTypeCodeType
     */
    @Test
    public void tred7065() {
        assertUniqueness(PackageTypeCodeType.class, PackageTypeCodeType::getCode);
    }

    /**
     * @see PaymentTermsTypeCodeType
     */
    @Test
    public void tred4279() {
        assertUniqueness(PaymentTermsTypeCodeType.class, PaymentTermsTypeCodeType::getCode);
    }

    /**
     * @see PaymentTermsIDType
     */
    @Test
    public void tred4277() {
        assertUniqueness(PaymentTermsIDType.class, PaymentTermsIDType::getCode);
    }

    /**
     * @see PaymentMeansChannelCodeType
     */
    @Test
    public void tred4435() {
        assertUniqueness(PaymentMeansChannelCodeType.class, PaymentMeansChannelCodeType::getCode);
    }

    /**
     * @see PaymentGuaranteeMeansCodeType
     */
    @Test
    public void tred4431() {
        assertUniqueness(PaymentGuaranteeMeansCodeType.class, PaymentGuaranteeMeansCodeType::getCode);
    }

    /**
     * @see PackagingMarkingCodeType
     */
    @Test
    public void tred7233() {
        assertUniqueness(PackagingMarkingCodeType.class, PackagingMarkingCodeType::getCode);
    }

    /**
     * @see AutomaticDataCaptureMethodCodeType
     */
    @Test
    public void tred7233AutomaticDataCaptureMethodCode() {
        assertUniqueness(AutomaticDataCaptureMethodCodeType.class, AutomaticDataCaptureMethodCodeType::getCode);
    }

    /**
     * @see MessageFunctionCodeType
     */
    @Test
    public void tred1225() {
        assertUniqueness(MessageFunctionCodeType.class, MessageFunctionCodeType::getCode);
    }

    /**
     * @see LogisticsChargeCalculationBasisCodeType
     */
    @Test
    public void tred6131() {
        assertUniqueness(LogisticsChargeCalculationBasisCodeType.class, LogisticsChargeCalculationBasisCodeType::getCode);
    }

    /**
     * @see GoodsTypeExtensionCodeType
     */
    @Test
    public void tred7361() {
        assertUniqueness(GoodsTypeExtensionCodeType.class, GoodsTypeExtensionCodeType::getCode);
    }

    /**
     * @see GoodsTypeCodeType
     */
    @Test
    public void tred7357() {
        assertUniqueness(GoodsTypeCodeType.class, GoodsTypeCodeType::getCode);
    }

    /**
     * @see CargoCommodityCategoryCodeType
     */
    @Test
    public void tred7357Commodity() {
        assertUniqueness(CargoCommodityCategoryCodeType.class, CargoCommodityCategoryCodeType::getCode);
    }

    /**
     * @see FreightChargeTariffClassCodeType
     */
    @Test
    public void tred5243() {
        assertUniqueness(FreightChargeTariffClassCodeType.class, FreightChargeTariffClassCodeType::getCode);
    }

    /**
     * @see DeliveryTermsCodeType
     */
    @Test
    public void tred4053() {
        assertUniqueness(DeliveryTermsCodeType.class, DeliveryTermsCodeType::getCode);
    }

    /**
     * @see AdjustmentReasonCodeType
     */
    @Test
    public void tred4465() {
        assertUniqueness(AdjustmentReasonCodeType.class, AdjustmentReasonCodeType::getCode);
    }

    /**
     * @see AllowanceChargeReasonCodeType
     */
    @Test
    public void tred4465AllowanceChargeReason() {
        assertUniqueness(AllowanceChargeReasonCodeType.class, AllowanceChargeReasonCodeType::getCode);
    }

    /**
     * @see CargoOperationalCategoryCodeType
     */
    @Test
    public void tred7085() {
        assertUniqueness(CargoOperationalCategoryCodeType.class, CargoOperationalCategoryCodeType::getCode);
    }

    /**
     * @see AllowanceChargeIdentificationCodeType
     */
    @Test
    public void tred5189() {
        assertUniqueness(AllowanceChargeIdentificationCodeType.class, AllowanceChargeIdentificationCodeType::getCode);
    }

}
