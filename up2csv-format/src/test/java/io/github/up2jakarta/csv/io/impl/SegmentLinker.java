package io.github.up2jakarta.csv.io.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.fct.*;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.csv.io.dto.*;

import java.util.function.BiConsumer;

public class SegmentLinker<P extends Segment, T extends Segment> extends BeanLinker<T, P> {

    private SegmentLinker(Class<P> parentType, Class<T> type, IJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, SingleJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, ArrayJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, MapValueJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, MapKeyJoin<P, T> getter, BiConsumer<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    static SegmentLinker<Invoice, Invoice> invoice() {
        return new SegmentLinker<>(Invoice.class, Invoice.class, IJoin.empty(), (p, s) -> {
        });
    }

    static SegmentLinker<Invoice, Party> seller() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::getSeller, Invoice::setSeller);
    }

    static SegmentLinker<Invoice, Party> buyer() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::getBuyer, Invoice::setBuyer);
    }

    static SegmentLinker<Invoice, Party> payer() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::getPayer, Invoice::setPayer);
    }

    static SegmentLinker<Invoice, Party> payee() {
        return new SegmentLinker<>(Invoice.class, Party.class, Invoice::getPayee, Invoice::setPayee);
    }

    static SegmentLinker<Invoice, Item> items() {
        return new SegmentLinker<>(Invoice.class, Item.class, Invoice::getItems, (i, l) -> i.getItems().add(l));
    }

    static SegmentLinker<Item, Attribute> attributes() {
        return new SegmentLinker<>(Item.class, Attribute.class, Item::getAttributes, (i, a) -> i.getAttributes().put(a.getKey(), a));
    }

    static SegmentLinker<Invoice, Amount> amounts() {
        return new SegmentLinker<>(Invoice.class, Amount.class, Invoice::getAmounts, (i, a) -> {
            final Amount.Type at = switch (a.getValue().signum()) {
                case 0 -> Amount.Type.NONE;
                case 1 -> Amount.Type.CHARGE;
                default -> Amount.Type.ALLOWANCE;
            };
            i.getAmounts().put(a, at);
        });
    }

    static SegmentLinker<Invoice, Note> notes() {
        return new SegmentLinker<>(Invoice.class, Note.class, Invoice::getNotes, (i, n) -> i.setNotes(Beans.concat(i.getNotes(), n)));
    }

}
