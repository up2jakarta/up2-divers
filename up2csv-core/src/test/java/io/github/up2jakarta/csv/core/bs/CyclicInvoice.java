package io.github.up2jakarta.csv.core.bs;

import io.github.up2jakarta.csv.core.misc.Parsable;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link io.github.up2jakarta.csv.impl.SegmentType#S61}
 */
public final class CyclicInvoice extends Parsable {

    private final List<CyclicItem> items = new LinkedList<>();

    public List<CyclicItem> getItems() {
        return items;
    }

}
