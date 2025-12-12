package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

/**
 * {@link SegmentType#S61}
 *
 * @see Tests#assertReference(ModeType, Invoice)
 */
@ValidOverride(disable = true) // reference is always null
public final class Dummy4Invoice extends Dummy1Invoice {

}
