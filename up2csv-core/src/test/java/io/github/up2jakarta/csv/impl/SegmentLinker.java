package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.input.InputLinker;
import io.github.up2jakarta.csv.test.agg.Attribute;
import io.github.up2jakarta.csv.test.agg.Invoice;
import io.github.up2jakarta.csv.test.agg.Item;
import io.github.up2jakarta.csv.test.agg.Party;

import java.util.function.BiConsumer;

public class SegmentLinker<P extends Parsable, T extends Parsable> extends InputLinker<SegmentType, T, P> {

    private SegmentLinker(Class<P> parentType, Class<T> type, BiConsumer<P, T> linker) {
        super(parentType, type, linker);
    }

    static SegmentLinker<Invoice, Invoice> none() {
        return new SegmentLinker<>(Invoice.class, Invoice.class, (nan, i) -> {
        });
    }

    static SegmentLinker<Invoice, Party> seller() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::setSeller);
    }

    static SegmentLinker<Invoice, Party> buyer() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::setBuyer);
    }

    static SegmentLinker<Invoice, Item> items() {
        return new SegmentLinker<>(Invoice.class, Item.class, (i, l) -> i.getItems().add(l));
    }

    static SegmentLinker<Item, Attribute> attributes() {
        return new SegmentLinker<>(Item.class, Attribute.class, (i, a) -> i.getAttributes().add(a));
    }

}
