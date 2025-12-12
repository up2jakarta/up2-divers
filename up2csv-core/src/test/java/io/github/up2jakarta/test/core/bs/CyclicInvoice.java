package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.impl.SegmentType;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link SegmentType#S61}
 */
public final class CyclicInvoice extends Parsable {

    private final List<CyclicItem> items = new LinkedList<>();

    public List<CyclicItem> getItems() {
        return items;
    }

}
