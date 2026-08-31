package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.*;
import io.github.up2jakarta.lov.core.Identifiable;
import jakarta.validation.Valid;

@Valid
public final class Path1Bean implements Segment, Identifiable<PFragment> {

    @ValidOverride(path = "404")
    @FragmentOverride(path = "404")
    @PositionOverride(path = "404")
    private final @Fragment(0) PFragment key;

    public Path1Bean(PFragment key) {
        this.key = key;
    }

    @Override
    public PFragment getKey() {
        return key;
    }
}

final class PFragment implements Segment, Identifiable<String> {
    private final @Position(0) String key;

    PFragment(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}