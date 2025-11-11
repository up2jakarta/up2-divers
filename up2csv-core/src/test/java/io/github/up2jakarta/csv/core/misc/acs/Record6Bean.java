package io.github.up2jakarta.csv.core.misc.acs;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.csv.data.Segment;

@SuppressWarnings("unused")
public record Record6Bean(@Position(0) @Up2Number int id, @Position(1) String content) implements Segment {

    public Record6Bean(String content) {
        this(999, content);
    }

}

