package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2OptionalInt;
import jakarta.persistence.Access;

import java.util.OptionalInt;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public record OptionalIntBean(@Position(0) @Up2OptionalInt OptionalInt id) implements Segment {
}
