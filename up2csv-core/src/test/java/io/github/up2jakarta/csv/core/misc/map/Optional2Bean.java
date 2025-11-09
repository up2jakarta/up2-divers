package io.github.up2jakarta.csv.core.misc.map;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Optional;

public record Optional2Bean(@Position(0) @Up2Number int id, @Position(1) Optional<String> content) implements Segment {
}
