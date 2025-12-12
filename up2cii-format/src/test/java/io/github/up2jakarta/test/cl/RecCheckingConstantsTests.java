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
public class RecCheckingConstantsTests {

    @Autowired
    private TestUtil util;

    /**
     * @see TransportModeCodeType
     */
    @Test
    public void rec19() throws Exception {
        util.assertSameBinding(TransportModeCodeType.class);
    }

    /**
     * @see WeightMeasurementUnitCodeType
     */
    @Test
    public void rec20Weight() throws Exception {
        util.assertSameBinding(WeightMeasurementUnitCodeType.class);
    }

    /**
     * @see VolumeMeasurementUnitCodeType
     */
    @Test
    public void rec20Volume() throws Exception {
        util.assertSameBinding(VolumeMeasurementUnitCodeType.class);
    }

    /**
     * @see LinearMeasurementUnitCodeType
     */
    @Test
    public void rec20Linear() throws Exception {
        util.assertSameBinding(LinearMeasurementUnitCodeType.class);
    }

    /**
     * @see TransportMeansTypeCodeType
     */
    @Test
    public void rec28() throws Exception {
        util.assertSameBinding(TransportMeansTypeCodeType.class);
    }

    /**
     * @see FreightChargeTypeIDType
     */
    @Test
    public void rec23() throws Exception {
        util.assertSameBinding(FreightChargeTypeIDType.class);
    }

    /**
     * @see CargoCategoryCodeType
     */
    @Test
    public void rec21() throws Exception {
        util.assertSameBinding(CargoCategoryCodeType.class);
    }

}
