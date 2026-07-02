package io.github.up2jakarta.test.fmt.misc;

import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.test.impl.dto.Invoice;

// Skipping @Position(0)
@BusinessObject("11")
@PositionOverride(path = "reference")
public class Dummy1Invoice extends Invoice {

}
