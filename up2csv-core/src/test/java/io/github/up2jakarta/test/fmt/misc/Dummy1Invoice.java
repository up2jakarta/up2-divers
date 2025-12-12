package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

/**
 * {@link SegmentType#S11}
 */
// Skipping @Position(0)
@PositionOverride(path = "reference")
public class Dummy1Invoice extends Invoice implements BusinessObject<String> {

}
