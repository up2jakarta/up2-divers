package io.github.up2jakarta.test.core.misc.xml;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;
import io.github.up2jakarta.csv.data.Segment;

@Up2EnableXML
public class Test3Bean implements Segment {

    @Position(0)
    private XML4Enum invalid;

}
