package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.Up2Converter;
import io.github.up2jakarta.test.core.misc.cvr.NumberConverter;

@PositionOverride(path = "key", value = @Position(value = 0, converter = @Up2Converter(NumberConverter.class)))
public final class Optional4Bean extends Optional3Super<Number> {
}
