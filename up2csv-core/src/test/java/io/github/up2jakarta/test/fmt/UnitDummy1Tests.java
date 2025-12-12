package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AUnitTest;
import io.github.up2jakarta.test.fmt.misc.Dummy1Invoice;
import io.github.up2jakarta.test.impl.GroupType;
import io.github.up2jakarta.test.impl.SegmentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.test.fmt.misc.Tests.*;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class UnitDummy1Tests extends AUnitTest<Dummy1Invoice, TURecord, TUError> {

    @Autowired
    UnitDummy1Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(factory.builder().unit(Dummy1Invoice.class).build(S11).build(TUError::new));
    }

    public TURecord record(String code, String... data) throws CodeListException {
        final SegmentType type = PARSER.parse(code);
        return new TURecord(type, "TU2025R0099", data);
    }

    @Test
    void testEmpty() {
        checkEmpty(new TURecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("11", null, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
        assertEquals(1, rows[0].getEvents().size());
        assertEquals(1, rows[1].getEvents().size());
    }

    @Test
    void testCardinality2() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
        assertEquals(3, rows[0].getEvents().size());
    }

    @Test
    void testCardinality3() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("12", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S12, rows);
        assertEquals(0, rows[0].getEvents().size());
        assertEquals(1, rows[1].getEvents().size());
        assertEquals(1, rows[2].getEvents().size());
    }

    @Test
    void testCardinality4() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S14, S11, rows);
        assertEquals(1, rows[0].getEvents().size());
    }

    @Test
    void testDetached() {
        // Given
        final TURecord detached = record("90", "9999", "Warning", "Detached");
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
        assertEquals(0, rows[0].getEvents().size());
        assertEquals(1, detached.getEvents().size());
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final TURecord[] rows = fastInvoice(S11);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws IOException {
        // Given
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
                record("14", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final TURecord invalid = record("90", "1199", "Support", null);
        final TURecord[] rows = new TURecord[]{
                record("11", null, "2025-03-12", "120", "100", "20"),
                record("12", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
        assertEquals(0, rows[0].getEvents().size());
        assertEquals(1, invalid.getEvents().size());
    }

}
