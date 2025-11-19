package io.github.up2jakarta.job.csv.impl;

import io.github.up2jakarta.csv.api.fct.IJoin;
import io.github.up2jakarta.csv.api.fct.ILink;
import io.github.up2jakarta.csv.api.fct.IMapValue;
import io.github.up2jakarta.csv.api.fct.ISegment;
import io.github.up2jakarta.csv.core.BeanLinker;
import io.github.up2jakarta.csv.data.Segment;
import io.github.up2jakarta.job.csv.dto.*;

public class SegmentLinker<P extends Segment, T extends Segment> extends BeanLinker<T, P> {

    private SegmentLinker(Class<P> parentType, Class<T> type, IJoin<P, T> getter, ILink<P, T> linker) {
        super(parentType, type, getter, linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, ISegment<P, T> getter, ILink<P, T> linker) {
        super(parentType, type, getter.many(), linker);
    }

    private SegmentLinker(Class<P> parentType, Class<T> type, IMapValue<P, T> getter, ILink<P, T> linker) {
        super(parentType, type, getter.values(), linker);
    }

    static SegmentLinker<Invoice, Invoice> invoice() {
        return new SegmentLinker<>(Invoice.class, Invoice.class, IJoin.empty(), ILink.empty());
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
        return new SegmentLinker<>(Invoice.class, Item.class, Invoice::getItems, ILink.of(Invoice::getItems));
    }

    static SegmentLinker<Item, Attribute> attributes() {
        return new SegmentLinker<>(
                Item.class, Attribute.class, Item::getAttributes,
                ILink.of(Item::getAttributes, Attribute::getKey)
        );
    }

    static SegmentLinker<Invoice, Amount> amounts() {
        return new SegmentLinker<>(Invoice.class, Amount.class, Invoice::getAmounts, ILink.of(Invoice::getAmounts));
    }

    static SegmentLinker<Invoice, Note> notes() {
        return new SegmentLinker<>(Invoice.class, Note.class, Invoice::getNotes, ILink.of(Invoice::getNotes));
    }

}
