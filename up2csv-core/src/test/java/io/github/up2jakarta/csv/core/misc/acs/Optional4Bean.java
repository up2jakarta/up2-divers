package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.core.misc.cvr.NumberConverter;

@PositionOverride(path = "key", value = @Position(value = 0, converter = NumberConverter.class))
public final class Optional4Bean extends Generic3Bean<Number> {
}
