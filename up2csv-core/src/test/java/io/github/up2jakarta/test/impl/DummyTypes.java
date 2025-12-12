package io.github.up2jakarta.test.impl;

import io.github.up2jakarta.csv.api.fct.IJoin;
import io.github.up2jakarta.csv.api.fct.ILink;
import io.github.up2jakarta.lov.CodeList;
import io.github.up2jakarta.lov.core.AccessException;
import io.github.up2jakarta.test.core.bs.CyclicInvoice;
import io.github.up2jakarta.test.core.bs.CyclicItem;
import io.github.up2jakarta.test.core.bs.DummyAttribute;
import io.github.up2jakarta.test.core.bs.DummyReference;
import io.github.up2jakarta.test.core.misc.Parsable;
import io.github.up2jakarta.test.fmt.misc.*;
import io.github.up2jakarta.test.impl.dto.*;

import static io.github.up2jakarta.test.impl.GroupType.*;

/**
 * {@link io.github.up2jakarta.csv.api.IType} for Business Tests used for test coverage.
 */
@SuppressWarnings("unused")
sealed interface DummyTypes extends CodeList<SegmentType> permits SegmentType {

    // DUMMY
    SegmentType S00 = none(NONE, "00", Parsable.class);
    // Test 2 (Unknown reference)
    SegmentType S11 = none(D001, "11", Dummy1Invoice.class);
    SegmentType S12 = seller("12", Dummy1Invoice.class);
    SegmentType S13 = buyer("13", Dummy1Invoice.class);
    SegmentType S14 = items("14", Dummy1Invoice.class);
    SegmentType S15 = notes("15", Dummy1Invoice.class);
    SegmentType S16 = amounts("16", Dummy1Invoice.class);
    SegmentType S17 = payer("17", Dummy1Invoice.class);
    SegmentType S18 = payee("18", Dummy1Invoice.class);
    // Test 2 (Full truncated)
    SegmentType S21 = none(D001, "21", Dummy2Invoice.class);
    SegmentType S22 = seller("22", Dummy2Invoice.class);
    SegmentType S23 = buyer("23", Dummy2Invoice.class);
    SegmentType S24 = items("24", Dummy2Invoice.class);
    SegmentType S25 = notes("25", Dummy2Invoice.class);
    SegmentType S26 = amounts("26", Dummy2Invoice.class);
    SegmentType S27 = payer("27", Dummy2Invoice.class);
    SegmentType S28 = payee("28", Dummy2Invoice.class);
    // Test 3 (Fast truncated)
    SegmentType S31 = none(D001, "31", Dummy3Invoice.class);
    SegmentType S32 = seller("32", Dummy3Invoice.class);
    SegmentType S33 = buyer("33", Dummy3Invoice.class);
    SegmentType S34 = items("34", Dummy3Invoice.class);
    SegmentType S35 = notes("35", Dummy3Invoice.class);
    SegmentType S36 = amounts("36", Dummy3Invoice.class);
    SegmentType S37 = payer("37", Dummy3Invoice.class);
    SegmentType S38 = payee("38", Dummy3Invoice.class);
    // Test 4 (Null reference)
    SegmentType S41 = none(D001, "41", Dummy4Invoice.class);
    SegmentType S42 = seller("42", Dummy4Invoice.class);
    SegmentType S43 = buyer("43", Dummy4Invoice.class);
    SegmentType S44 = items("44", Dummy4Invoice.class);
    SegmentType S45 = notes("45", Dummy4Invoice.class);
    SegmentType S46 = amounts("46", Dummy4Invoice.class);
    SegmentType S47 = payer("47", Dummy4Invoice.class);
    SegmentType S48 = payee("48", Dummy4Invoice.class);
    // Test 5 (Null reference without validation)
    SegmentType S51 = none(D001, "51", Dummy5Invoice.class);
    SegmentType S52 = seller("52", Dummy5Invoice.class);
    SegmentType S53 = buyer("53", Dummy5Invoice.class);
    SegmentType S54 = items("54", Dummy5Invoice.class);
    SegmentType S55 = notes("55", Dummy5Invoice.class);
    SegmentType S56 = amounts("56", Dummy5Invoice.class);
    SegmentType S57 = payer("57", Dummy5Invoice.class);
    SegmentType S58 = payee("58", Dummy5Invoice.class);
    // Test 6 (Cyclic segments)
    SegmentType S61 = none(D001, "61", CyclicInvoice.class);
    SegmentType S62 = cyclicItems();
    SegmentType S63 = cyclicInvoice();
    // Test 7 (Dummy reference)
    SegmentType S71 = none(NONE, "71", DummyReference.class);
    SegmentType S72 = dummyAttributes();

    private static SegmentType cyclicItems() {
        return new SegmentType(
                D004, "62", CyclicInvoice.class, CyclicItem.class,
                CyclicInvoice::getItems, ILink.of(CyclicInvoice::getItems)
        );
    }

    private static SegmentType cyclicInvoice() {
        return new SegmentType(
                D001, "63", CyclicItem.class, CyclicInvoice.class,
                CyclicItem::getInvoice, CyclicItem::setInvoice
        );
    }

    private static SegmentType dummyAttributes() {
        return new SegmentType(
                NONE, "72",
                DummyReference.class, DummyAttribute.class, DummyReference::getAttributes,
                (r, a) -> {
                    if ("*".equals(a.getValue())) {
                        throw new AccessException(DummyAttribute.class, "value", "invalid value");
                    }
                    r.getAttributes().add(a);
                }
        );
    }

    private static Amount.Type typeOf(Amount amount) {
        return switch (amount.getValue().signum()) {
            case 0 -> Amount.Type.NONE;
            case 1 -> Amount.Type.CHARGE;
            default -> Amount.Type.ALLOWANCE;
        };
    }

    static <S extends Parsable> SegmentType none(GroupType type, String code, Class<S> c) {
        return new SegmentType(type, code, c, c, IJoin.empty(), ILink.empty());
    }

    static <S extends Invoice> SegmentType seller(String code, Class<S> c) {
        return new SegmentType(D002, code, c, Party.class, Invoice::getSeller, Invoice::setSeller);
    }

    static <S extends Invoice> SegmentType buyer(String code, Class<S> c) {
        return new SegmentType(D003, code, c, Party.class, Invoice::getBuyer, Invoice::setBuyer);
    }

    static <S extends Invoice> SegmentType payer(String code, Class<S> c) {
        return new SegmentType(D008, code, c, Party.class, Invoice::getPayer, ILink.of(Invoice::setPayer));
    }

    static <S extends Invoice> SegmentType payee(String code, Class<S> c) {
        return new SegmentType(D009, code, c, Party.class, Invoice::getPayee, ILink.of(Invoice::setPayee));
    }

    static <S extends Invoice> SegmentType items(String code, Class<S> c) {
        return new SegmentType(D004, code, c, Item.class, Invoice::getItems, ILink.of(Invoice::getItems));
    }

    static <S extends Invoice> SegmentType amounts(String code, Class<S> c) {
        return new SegmentType(
                D006, code, c, Amount.class,
                Invoice::getAmounts, ILink.mk(Invoice::getAmounts, DummyTypes::typeOf)
        );
    }

    static <S extends Invoice> SegmentType notes(String code, Class<S> c) {
        return new SegmentType(
                D007, code, c, Note.class,
                Invoice::getNotes, ILink.of(Invoice::getNotes, Invoice::setNotes)
        );
    }

}
