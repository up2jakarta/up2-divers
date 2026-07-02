package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import jakarta.persistence.Access;

import java.util.Optional;

import static io.github.up2jakarta.test.core.misc.acs.Optional5Bean.Content;
import static jakarta.persistence.AccessType.PROPERTY;

@Access(PROPERTY)
public record Optional6Bean(@Position(0) @Up2Number int id, @Fragment(1) Optional<Content> content) implements Segment {
}
