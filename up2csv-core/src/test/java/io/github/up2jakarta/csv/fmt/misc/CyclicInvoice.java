package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.data.Referencable;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link io.github.up2jakarta.csv.impl.SegmentType#S61}
 */
public final class CyclicInvoice extends Parsable implements Referencable {

    private final List<CyclicItem> items = new LinkedList<>();

    @Override
    public String getReference() {
        return null;
    }

    public List<CyclicItem> getItems() {
        return items;
    }

}
