package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Fragment;

public record Inner4Segment(@Fragment(0) Inner1Segment fragment) implements Segment {

}
