package io.github.up2jakarta.csv.ops.misc;

import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.impl.dto.Invoice;

/**
 * {@link io.github.up2jakarta.csv.impl.SegmentType#S11}
 */
@PositionOverride(path = "reference") // Skipping @Position(0)
@ValidOverride(disable = true) // reference is not set automatically
public class Dummy1 extends Invoice {

}
