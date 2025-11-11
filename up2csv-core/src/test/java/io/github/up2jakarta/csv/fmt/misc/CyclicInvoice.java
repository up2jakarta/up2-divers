package io.github.up2jakarta.csv.fmt.misc;

import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.impl.dto.Item;

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

    public static final class CyclicItem extends Item {

        private CyclicInvoice invoice;

        public CyclicInvoice getInvoice() {
            return invoice;
        }

        public void setInvoice(CyclicInvoice invoice) {
            this.invoice = invoice;
        }

    }
}
