package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.FragmentOverride;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.csv.cfg.ValidOverride;
import io.github.up2jakarta.lov.core.Identifiable;
import jakarta.validation.Valid;

@Valid
public final class Path3Bean extends XFragment {
    public Path3Bean(String key) {
        super(key);
    }
}

@ValidOverride(path = "404")
@FragmentOverride(path = "404")
@PositionOverride(path = "404")
abstract class XFragment implements Segment, Identifiable<String> {
    private final @Position(0) String key;

    XFragment(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}