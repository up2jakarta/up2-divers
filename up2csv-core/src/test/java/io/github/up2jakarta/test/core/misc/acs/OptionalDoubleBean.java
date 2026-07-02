package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2OptionalDouble;
import jakarta.persistence.Access;

import java.util.OptionalDouble;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public record OptionalDoubleBean(@Position(0) @Up2OptionalDouble(2) OptionalDouble amount) implements Segment {
}
