package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2OptionalLong;
import jakarta.persistence.Access;

import java.util.OptionalLong;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public record OptionalLongBean(@Position(0) @Up2OptionalLong OptionalLong id) implements Segment {
}
