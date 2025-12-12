package io.github.up2jakarta.test.api;

import io.github.up2jakarta.cii.edi.AllowanceChargeIdentificationCodeType;
import io.github.up2jakarta.cii.ppf.SpecialServiceDescriptionCodeType;
import io.github.up2jakarta.cii.ppf.adapters.ChargeReasonCodeAdapter;
import io.github.up2jakarta.test.TUConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
public class ChargeReasonCodeTest {

    @Autowired
    private ChargeReasonCodeAdapter adapter;

    @Test
    public void BR_CL_19() {
        var codes = "41 42 60 62 63 64 65 66 67 68 70 71 88 95 100 102 103 104 105";
        for (var code : codes.split(" ")) {
            var token = adapter.unmarshal(code);
            assertNotNull(token);
            var cl = ChargeReasonCodeAdapter.from(token, false);
            assertInstanceOf(AllowanceChargeIdentificationCodeType.class, cl);
        }
    }

    @Test
    public void BR_CL_20() {
        var codes = "AA AAA AAC AAD AAE AAF AAH AAI AAS AAT AAV AAY AAZ ABA ABB ABC ABD ABF ABK ABL ABN ABR ABS ABT" +
                " ABU ACF ACG ACH ACI ACJ ACK ACL ACM ACS ADC ADE ADJ ADK ADL ADM ADN ADO ADP ADQ ADR ADT ADW ADY " +
                "ADZ AEA AEB AEC AED AEF AEH AEI AEJ AEK AEL AEM AEN AEO AEP AES AET AEU AEV AEW AEX AEY AEZ AJ AU " +
                "CA CAB CAD CAE CAF CAI CAJ CAK CAL CAM CAN CAO CAP CAQ CAR CAS CAT CAU CAV CAW CAX CAY CAZ CD CG " +
                "CS CT DAB DAD DAC DAF DAG DAH DAI DAJ DAK DAL DAM DAN DAO DAP DAQ DL EG EP ER FAA FAB FAC FC FH FI " +
                "GAA HAA HD HH IAA IAB ID IF IR IS KO L1 LA LAA LAB LF MAE MI ML NAA OA PA PAA PC PL RAB RAC RAD RAF " +
                "RE RF RH RV SA SAA SAD SAE SAI SG SH SM SU TAB TAC TT TV V1 V2 WH XAA YY ZZZ";
        for (var code : codes.split(" ")) {
            var token = adapter.unmarshal(code);
            assertNotNull(token);
            var cl = ChargeReasonCodeAdapter.from(token, true);
            assertInstanceOf(SpecialServiceDescriptionCodeType.class, cl);
        }
    }
}
