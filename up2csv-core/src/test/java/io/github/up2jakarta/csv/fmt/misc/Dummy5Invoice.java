package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.fmt.hdl.MiniRecord;
import io.github.up2jakarta.csv.impl.dto.Invoice;

/**
 * {@link io.github.up2jakarta.csv.impl.SegmentType#S51}
 * <p>
 * No need to set {@link MiniRecord#getBusinessReference()},
 * because this class does not implement {@link io.github.up2jakarta.csv.data.BusinessObject}
 *
 * @see Tests#assertReference(ModeType, Invoice)
 */
@ValidOverride(disable = true)
@PositionOverride(path = "reference")
public class Dummy5Invoice extends Invoice {

}
