package io.github.up2jakarta.test.core.misc.map;

import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.data.Segment;

public record Inner4Segment(@Fragment(0) Inner1Segment fragment) implements Segment {

}
