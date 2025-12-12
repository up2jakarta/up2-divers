package io.github.up2jakarta.test.cl;

import io.github.up2jakarta.cii.edi.*;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.api.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class CefactCheckingConstantsTests {

    @Autowired
    private TestUtil util;

    /**
     * @see PaymentMeansCodeType
     */
    @Test
    public void tred4461() throws Exception {
        util.assertSameBinding(PaymentMeansCodeType.class);
    }

    /**
     * @see ReferenceCodeType
     */
    @Test
    public void tred1153() throws Exception {
        util.assertSameBinding(ReferenceCodeType.class);
    }

    /**
     * @see DocumentCodeType
     */
    @Test
    public void tred1001() throws Exception {
        util.assertSameBinding(DocumentCodeType.class);
    }

    /**
     * @see AccountingDocumentCodeType
     */
    @Test
    public void tred1001Accounting() throws Exception {
        util.assertSameBinding(AccountingDocumentCodeType.class);
    }

    /**
     * @see TaxCategoryCodeType
     */
    @Test
    public void tred5305() throws Exception {
        util.assertSameBinding(TaxCategoryCodeType.class);
    }

    /**
     * @see TaxTypeCodeType
     */
    @Test
    public void tred5153() throws Exception {
        util.assertSameBinding(TaxTypeCodeType.class);
    }

    /**
     * @see PartyRoleCodeType
     */
    @Test
    public void tred3035() throws Exception {
        util.assertSameBinding(PartyRoleCodeType.class);
    }

    /**
     * @see ChargePayingPartyRoleCodeType
     */
    @Test
    public void tred3035ChargePaying() throws Exception {
        util.assertSameBinding(ChargePayingPartyRoleCodeType.class);
    }

    /**
     * @see CommunicationChannelCodeType
     */
    @Test
    public void tred3155() throws Exception {
        util.assertSameBinding(CommunicationChannelCodeType.class);
    }

    /**
     * @see LineStatusCodeType
     */
    @Test
    public void tred1229() throws Exception {
        util.assertSameBinding(LineStatusCodeType.class);
    }

    /**
     * @see DocumentStatusCodeType
     */
    @Test
    public void tred1373() throws Exception {
        util.assertSameBinding(DocumentStatusCodeType.class);
    }

    /**
     * @see ContactTypeCodeType
     */
    @Test
    public void tred3139() throws Exception {
        util.assertSameBinding(ContactTypeCodeType.class);
    }

    /**
     * @see PriceTypeCodeType
     */
    @Test
    public void tred5375() throws Exception {
        util.assertSameBinding(PriceTypeCodeType.class);
    }

    /**
     * @see TimePointFormatCodeType
     */
    @Test
    public void tred2379() throws Exception {
        util.assertSameBinding(TimePointFormatCodeType.class);
    }

    /**
     * @see TimeReferenceCodeType
     */
    @Test
    public void tred2475() throws Exception {
        util.assertSameBinding(TimeReferenceCodeType.class);
    }

    /**
     * @see PaymentTermsEventTimeReferenceCodeType
     */
    @Test
    public void tred2475PaymentTermsEvent() throws Exception {
        util.assertSameBinding(PaymentTermsEventTimeReferenceCodeType.class);
    }

    /**
     * @see TransportEquipmentCategoryCodeType
     */
    @Test
    public void tred8053() throws Exception {
        util.assertSameBinding(TransportEquipmentCategoryCodeType.class);
    }

    /**
     * @see TransportEquipmentFullnessCodeType
     */
    @Test
    public void tred8169() throws Exception {
        util.assertSameBinding(TransportEquipmentFullnessCodeType.class);
    }

    /**
     * @see TransportEquipmentSizeTypeCodeType
     */
    @Test
    public void tred8155() throws Exception {
        util.assertSameBinding(TransportEquipmentSizeTypeCodeType.class);
    }

    /**
     * @see TransportMovementStageCodeType
     */
    @Test
    public void tred8051() throws Exception {
        util.assertSameBinding(TransportMovementStageCodeType.class);
    }

    /**
     * @see DimensionTypeCodeType
     */
    @Test
    public void tred6145() throws Exception {
        util.assertSameBinding(DimensionTypeCodeType.class);
    }

    /**
     * @see PackageTypeCodeType
     */
    @Test
    public void tred7065() throws Exception {
        util.assertSameBinding(PackageTypeCodeType.class);
    }

    /**
     * @see PaymentTermsTypeCodeType
     */
    @Test
    public void tred4279() throws Exception {
        util.assertSameBinding(PaymentTermsTypeCodeType.class);
    }

    /**
     * @see PaymentTermsIDType
     */
    @Test
    public void tred4277() throws Exception {
        util.assertSameBinding(PaymentTermsIDType.class);
    }

    /**
     * @see PaymentMeansChannelCodeType
     */
    @Test
    public void tred4435() throws Exception {
        util.assertSameBinding(PaymentMeansChannelCodeType.class);
    }

    /**
     * @see PaymentGuaranteeMeansCodeType
     */
    @Test
    public void tred4431() throws Exception {
        util.assertSameBinding(PaymentGuaranteeMeansCodeType.class);
    }

    /**
     * @see PackagingMarkingCodeType
     */
    @Test
    public void tred7233() throws Exception {
        util.assertSameBinding(PackagingMarkingCodeType.class);
    }

    /**
     * @see AutomaticDataCaptureMethodCodeType
     */
    @Test
    public void tred7233AutomaticDataCaptureMethodCode() throws Exception {
        util.assertSameBinding(AutomaticDataCaptureMethodCodeType.class);
    }

    /**
     * @see MessageFunctionCodeType
     */
    @Test
    public void tred1225() throws Exception {
        util.assertSameBinding(MessageFunctionCodeType.class);
    }

    /**
     * @see LogisticsChargeCalculationBasisCodeType
     */
    @Test
    public void tred6131() throws Exception {
        util.assertSameBinding(LogisticsChargeCalculationBasisCodeType.class);
    }

    /**
     * @see GoodsTypeExtensionCodeType
     */
    @Test
    public void tred7361() throws Exception {
        util.assertSameBinding(GoodsTypeExtensionCodeType.class);
    }

    /**
     * @see GoodsTypeCodeType
     */
    @Test
    public void tred7357() throws Exception {
        util.assertSameBinding(GoodsTypeCodeType.class);
    }

    /**
     * @see CargoCommodityCategoryCodeType
     */
    @Test
    public void tred7357Commodity() throws Exception {
        util.assertSameBinding(CargoCommodityCategoryCodeType.class);
    }

    /**
     * @see FreightChargeTariffClassCodeType
     */
    @Test
    public void tred5243() throws Exception {
        util.assertSameBinding(FreightChargeTariffClassCodeType.class);
    }

    /**
     * @see DeliveryTermsCodeType
     */
    @Test
    public void tred4053() throws Exception {
        util.assertSameBinding(DeliveryTermsCodeType.class);
    }

    /**
     * @see AdjustmentReasonCodeType
     */
    @Test
    public void tred4465() throws Exception {
        util.assertSameBinding(AdjustmentReasonCodeType.class);
    }

    /**
     * @see AllowanceChargeReasonCodeType
     */
    @Test
    public void tred4465AllowanceChargeReason() throws Exception {
        util.assertSameBinding(AllowanceChargeReasonCodeType.class);
    }

    /**
     * @see CargoOperationalCategoryCodeType
     */
    @Test
    public void tred7085() throws Exception {
        util.assertSameBinding(CargoOperationalCategoryCodeType.class);
    }

    /**
     * @see AllowanceChargeIdentificationCodeType
     */
    @Test
    public void tred5189() throws Exception {
        util.assertSameBinding(AllowanceChargeIdentificationCodeType.class);
    }

}
