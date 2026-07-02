package io.github.up2jakarta.test.core.misc.acs;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Date;

import java.sql.Timestamp;
import java.util.Date;

public class Access10Bean extends Access10Super {

    // No guarantee that @Up2Date returns Timestamp
    public Access10Bean(Timestamp date) {
        super(date);
    }
}

abstract class Access10Super implements Segment {
    @Position(0)
    private final @Up2Date Date date;

    public Access10Super(Date date) {
        this.date = date;
    }
}
