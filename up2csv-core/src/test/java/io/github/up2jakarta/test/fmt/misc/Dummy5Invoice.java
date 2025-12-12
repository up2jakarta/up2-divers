package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.fmt.FastRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;

/**
 * {@link SegmentType#S51}
 * <p>
 * No need to set {@link FastRecord#getPivot()},
 * because this class does not implement {@link io.github.up2jakarta.csv.data.BusinessObject}
 *
 * @see Tests#assertReference(ModeType, Invoice)
 */
@ValidOverride(disable = true)
@PositionOverride(path = "reference")
public class Dummy5Invoice extends Invoice {

}
