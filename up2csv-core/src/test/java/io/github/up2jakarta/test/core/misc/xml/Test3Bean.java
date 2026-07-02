package io.github.up2jakarta.test.core.misc.xml;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.Up2EnableXML;

@Up2EnableXML
public class Test3Bean implements Segment {

    @Position(0)
    private XML4Enum invalid;

}
