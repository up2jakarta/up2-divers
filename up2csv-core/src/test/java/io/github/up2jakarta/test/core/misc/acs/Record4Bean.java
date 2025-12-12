package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public record Record4Bean(@Position(0) String code, int i1, int i2, @Position(0) String label) implements Segment {
}
