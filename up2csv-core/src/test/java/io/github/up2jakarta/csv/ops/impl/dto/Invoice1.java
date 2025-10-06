package io.github.up2jakarta.csv.ops.impl.dto;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.Truncated;
import io.github.up2jakarta.csv.ops.impl.SegmentType;

import static io.github.up2jakarta.csv.test.Tests.MD_LENGTH;

/**
 * {@link SegmentType#S01}
 */
@Truncated(MD_LENGTH)
@PositionOverride(path = "reference", value = @Position(0))
public class Invoice1 extends Invoice {

}
