package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public record Record1Bean(@Position(0) String id) implements Segment {
}
