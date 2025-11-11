package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

import java.util.Optional;

import static io.github.up2jakarta.csv.core.misc.acs.Optional5Bean.Content;

public record Optional6Bean(@Position(0) @Up2Number int id, @Fragment(1) Optional<Content> content) implements Segment {
}
