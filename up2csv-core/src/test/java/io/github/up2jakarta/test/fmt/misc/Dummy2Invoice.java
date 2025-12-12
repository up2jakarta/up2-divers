package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.test.impl.SegmentType;

/**
 * {@link SegmentType#S21}
 */
@Truncated(3)
@PositionOverride(path = "issueDate", value = @Position(0))
@PositionOverride(path = "grossAmount", value = @Position(1))
@PositionOverride(path = "netAmount", value = @Position(2))
@PositionOverride(path = "taxAmount", value = @Position(3))
public final class Dummy2Invoice extends Dummy1Invoice {

}
