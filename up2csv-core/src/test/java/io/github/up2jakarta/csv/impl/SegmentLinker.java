package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.input.InputLinker;
import io.github.up2jakarta.csv.input.ManyJoin;
import io.github.up2jakarta.csv.input.SingleJoin;
import io.github.up2jakarta.csv.test.agg.Attribute;
import io.github.up2jakarta.csv.test.agg.Invoice;
import io.github.up2jakarta.csv.test.agg.Item;
import io.github.up2jakarta.csv.test.agg.Party;

import java.util.function.BiConsumer;

public class SegmentLinker<P extends Parsable, T extends Parsable> extends InputLinker<SegmentType, T, P> {

    private SegmentLinker(Class<P> parentType, Class<T> type, ManyJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, SingleJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    static SegmentLinker<Parsable, Parsable> none() {
        return none(Parsable.class);
    }

    static <S extends Parsable> SegmentLinker<S, S> none(Class<S> type) {
        return new SegmentLinker<>(type, type, ManyJoin.none(), (p, s) -> {
        });
    }

    static SegmentLinker<Invoice, Party> seller() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::getSeller, Invoice::setSeller);
    }

    static SegmentLinker<Invoice, Party> buyer() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::getBuyer, Invoice::setBuyer);
    }

    static SegmentLinker<Invoice, Item> items() {
        return new SegmentLinker<>(Invoice.class, Item.class, Invoice::getItems, (i, l) -> i.getItems().add(l));
    }

    static SegmentLinker<Item, Attribute> attributes() {
        return new SegmentLinker<>(Item.class, Attribute.class, Item::getAttributes, (i, a) -> i.getAttributes().add(a));
    }

}
