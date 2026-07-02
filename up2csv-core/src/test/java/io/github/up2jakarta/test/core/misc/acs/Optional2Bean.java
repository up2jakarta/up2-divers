package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.persistence.Access;

import java.util.Optional;

import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public record Optional2Bean(@Position(0) @Up2Number int id, @Position(1) Optional<String> content) implements Segment {
}
