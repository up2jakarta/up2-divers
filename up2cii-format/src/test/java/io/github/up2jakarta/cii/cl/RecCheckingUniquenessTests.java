package io.github.up2jakarta.cii.cl;

import io.github.up2jakarta.cii.edi.*;
import org.junit.jupiter.api.Test;

import static io.github.up2jakarta.cii.api.TestUtil.assertUniqueness;

public class RecCheckingUniquenessTests {

    /**
     * @see TransportModeCodeType
     */
    @Test
    public void rec19() {
        assertUniqueness(TransportModeCodeType.class, TransportModeCodeType::getCode);
    }

    /**
     * @see WeightMeasurementUnitCodeType
     */
    @Test
    public void rec20Weight() {
        assertUniqueness(WeightMeasurementUnitCodeType.class, WeightMeasurementUnitCodeType::getCode);
    }

    /**
     * @see VolumeMeasurementUnitCodeType
     */
    @Test
    public void rec20Volume() {
        assertUniqueness(VolumeMeasurementUnitCodeType.class, VolumeMeasurementUnitCodeType::getCode);
    }

    /**
     * @see LinearMeasurementUnitCodeType
     */
    @Test
    public void rec20Linear() {
        assertUniqueness(LinearMeasurementUnitCodeType.class, LinearMeasurementUnitCodeType::getCode);
    }

    /**
     * @see TransportMeansTypeCodeType
     */
    @Test
    public void rec28() {
        assertUniqueness(TransportMeansTypeCodeType.class, TransportMeansTypeCodeType::getCode);
    }

    /**
     * @see FreightChargeTypeIDType
     */
    @Test
    public void rec23() {
        assertUniqueness(FreightChargeTypeIDType.class, FreightChargeTypeIDType::getCode);
    }

    /**
     * @see CargoCategoryCodeType
     */
    @Test
    public void rec21() {
        assertUniqueness(CargoCategoryCodeType.class, CargoCategoryCodeType::getCode);
    }

}
