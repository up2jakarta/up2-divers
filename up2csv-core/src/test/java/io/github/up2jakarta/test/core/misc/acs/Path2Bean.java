package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.lov.core.Identifiable;

@ValidOverride(path = "404")
@FragmentOverride(path = "404")
@PositionOverride(path = "404")
public final class Path2Bean implements Segment, Identifiable<PFragment> {
    private final @Fragment(0) PFragment key;

    public Path2Bean(PFragment key) {
        this.key = key;
    }

    @Override
    public PFragment getKey() {
        return key;
    }
}