package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

public record Record2Bean(Long id, @Position(0) String code, @Position(1) String label, Object src) implements Segment {
}
