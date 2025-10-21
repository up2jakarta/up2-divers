package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.BeanLinker;
import io.github.up2jakarta.csv.api.fct.*;
import io.github.up2jakarta.csv.core.ext.Beans;
import io.github.up2jakarta.csv.core.misc.Parsable;
import io.github.up2jakarta.csv.fmt.misc.CyclicInvoice;
import io.github.up2jakarta.csv.fmt.misc.CyclicItem;
import io.github.up2jakarta.csv.impl.dto.*;

import java.util.function.BiConsumer;

public class SegmentLinker<P extends Parsable, T extends Parsable> extends BeanLinker<T, P> {

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

    static SegmentLinker<Parsable, Parsable> none() {
        return none(Parsable.class);
    }

    static <S extends Parsable> SegmentLinker<S, S> none(Class<S> type) {
        return new SegmentLinker<>(type, type, IJoin.empty(), (p, s) -> {
        });
    }

    static <T extends Invoice> SegmentLinker<T, Party> seller(Class<T> type) {
        return new SegmentLinker<>(type, Party.class, Invoice::getSeller, Invoice::setSeller);
    }

    static <T extends Invoice> SegmentLinker<T, Party> buyer(Class<T> type) {
        return new SegmentLinker<>(type, Party.class, Invoice::getBuyer, Invoice::setBuyer);
    }

    static <T extends Invoice> SegmentLinker<T, Party> payer(Class<T> type) {
        return new SegmentLinker<>(type, Party.class, Invoice::getPayer, Invoice::setPayer);
    }

    static <T extends Invoice> SegmentLinker<T, Party> payee(Class<T> type) {
        return new SegmentLinker<>(type, Party.class, Invoice::getPayee, Invoice::setPayee);
    }

    static <T extends Invoice> SegmentLinker<T, Item> items(Class<T> type) {
        return new SegmentLinker<>(type, Item.class, Invoice::getItems, (i, l) -> i.getItems().add(l));
    }

    static SegmentLinker<Item, Attribute> attributes() {
        return new SegmentLinker<>(Item.class, Attribute.class, Item::getAttributes, (i, a) -> i.getAttributes().put(a.getKey(), a));
    }

    static <T extends Invoice> SegmentLinker<T, Amount> amounts(Class<T> type) {
        return new SegmentLinker<>(type, Amount.class, Invoice::getAmounts, (i, a) -> {
            final Amount.Type at = switch (a.getValue().signum()) {
                case 0 -> Amount.Type.NONE;
                case 1 -> Amount.Type.CHARGE;
                default -> Amount.Type.ALLOWANCE;
            };
            i.getAmounts().put(a, at);
        });
    }

    static <T extends Invoice> SegmentLinker<T, Note> notes(Class<T> type) {
        return new SegmentLinker<>(type, Note.class, Invoice::getNotes, (i, n) -> i.setNotes(Beans.concat(i.getNotes(), n)));
    }

    static SegmentLinker<CyclicInvoice, CyclicItem> cyclicItems() {
        return new SegmentLinker<>(CyclicInvoice.class, CyclicItem.class, CyclicInvoice::getItems, (i, l) -> i.getItems().add(l));
    }

    static SegmentLinker<CyclicItem, CyclicInvoice> cyclicInvoice() {
        return new SegmentLinker<>(CyclicItem.class, CyclicInvoice.class, CyclicItem::getInvoice, CyclicItem::setInvoice);
    }

}
