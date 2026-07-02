package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.test.impl.dto.Invoice;

/**
 * Disable validation because this class does not implement {@link io.github.up2jakarta.csv.BusinessObject},
 * so {@link #getReference()} is always <code>null</code>.
 *
 * @see Tests#assertReference(io.github.up2jakarta.csv.core.ModeType, Invoice)
 */
@BusinessObject("51")
@ValidOverride(disable = true)
@PositionOverride(path = "reference")
public class Dummy5Invoice extends Invoice {

}
