package io.github.up2jakarta.csv.impl;

import io.github.up2jakarta.csv.api.fct.ILink;
import io.github.up2jakarta.csv.impl.dto.Attribute;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import io.github.up2jakarta.csv.impl.dto.Item;
import io.github.up2jakarta.lov.CodeList;

import static io.github.up2jakarta.csv.impl.DummyTypes.*;
import static io.github.up2jakarta.csv.impl.GroupType.D001;
import static io.github.up2jakarta.csv.impl.GroupType.D005;

/**
 * Main {@link io.github.up2jakarta.csv.api.IType} for {@link Invoice} business-object.
 */
@SuppressWarnings("unused")
public sealed interface InvoiceType extends CodeList<SegmentType> permits SegmentType {

    SegmentType S01 = none(D001, "01", Invoice.class);
    SegmentType S02 = seller("02", Invoice.class);
    SegmentType S03 = buyer("03", Invoice.class);
    SegmentType S04 = items("04", Invoice.class);
    SegmentType S05 = notes("05", Invoice.class);
    SegmentType S06 = amounts("06", Invoice.class);
    SegmentType S07 = payer("07", Invoice.class);
    SegmentType S08 = payee("08", Invoice.class);
    SegmentType S09 = attributes();

    private static SegmentType attributes() {
        return new SegmentType(
                D005, "90", Item.class, Attribute.class,
                Item::getAttributes, ILink.of(Item::getAttributes, Attribute::getKey)
        );
    }

}
