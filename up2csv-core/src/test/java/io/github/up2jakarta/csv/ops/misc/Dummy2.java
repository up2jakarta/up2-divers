package io.github.up2jakarta.csv.ops.misc;

import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.data.BusinessId;
import io.github.up2jakarta.csv.data.BusinessObject;
import io.github.up2jakarta.csv.impl.Parsable;
import io.github.up2jakarta.csv.impl.dto.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link io.github.up2jakarta.csv.impl.SegmentType#S31}
 */
@SuppressWarnings("unused")
public class Dummy2 extends Parsable implements BusinessObject {

    @Position(0)
    @BusinessId
    private String reference;

    private List<Item2> items = new LinkedList<>();

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Item2> getItems() {
        return items;
    }

    public void setItems(List<Item2> items) {
        this.items = items;
    }

    public static class Item2 extends Item {

        private Dummy2 invoice;

        public Dummy2 getInvoice() {
            return invoice;
        }

        public void setInvoice(Dummy2 invoice) {
            this.invoice = invoice;
        }
    }

}
