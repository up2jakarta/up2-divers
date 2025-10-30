package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public record Record2Bean(Long id, @Position(0) String code, @Position(1) String label, Object src) implements Segment {
}
