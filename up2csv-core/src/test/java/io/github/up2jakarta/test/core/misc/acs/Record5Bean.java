package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;

public record Record5Bean(@Position(0) @Up2Number int id, @Position(1) CharSequence content) implements Segment {
}
