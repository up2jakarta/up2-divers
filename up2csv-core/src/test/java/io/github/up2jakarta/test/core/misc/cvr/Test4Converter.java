package io.github.up2jakarta.test.core.misc.cvr;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2Date;

public class Test4Converter implements Segment {

    @Up2Date
    @Position(0)
    private java.util.Date date;

    @Up2Date
    @Position(1)
    private java.sql.Date sqlDate;

    @Up2Date
    @Position(2)
    private java.sql.Time sqlTime;

    @Up2Date
    @Position(3)
    private java.sql.Timestamp timestamp;

}
