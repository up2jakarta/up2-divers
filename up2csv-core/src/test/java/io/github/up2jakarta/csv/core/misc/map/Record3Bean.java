package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public record Record3Bean(
        short s, int i, long l, @Position(0) String code, char c, byte b, boolean f, float n, double d
) implements Segment {
}
