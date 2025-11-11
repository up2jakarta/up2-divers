package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.Segment;

public record BId4Bean(@Position(0) @Up2Number @BusinessId Integer id) implements Segment {
}