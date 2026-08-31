package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AFullTest;
import io.github.up2jakarta.test.fmt.misc.Dummy2Invoice;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.github.up2jakarta.test.fmt.misc.Tests.*;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static io.github.up2jakarta.test.impl.TermType.A002;
import static io.github.up2jakarta.test.impl.TermType.D009;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FullDummy2Tests extends AFullTest<Dummy2Invoice, TURecord, TUError> {

    @Autowired
    FullDummy2Tests(Up2Factory<TermType> factory) throws BeanException {
        super(factory.builder().full(Dummy2Invoice.class).build(SegmentType.class).build(TUError::new));
    }

    private TURecord record(SegmentType type, String... data) throws CodeListException {
        return new TURecord(type, "TU2025R0099", data);
    }

    @Test
    void testEmpty() {
        checkEmpty(new TURecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S21, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S02, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows, importer.toExporter());
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S21, rows, importer.toExporter());
    }

    @Test
    void testDetached() {
        // Given
        final TURecord detached = record(S09, "9999", "Warning", "Detached");
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, D009, rows);
    }

    @Test
    void testValid1() throws BeanException {
        // Given
        final TURecord[] rows = messInvoice(S21);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() {
        // Given
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                record(S04, "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final TURecord invalid = record(S09, "1199", "Support", null);
        final TURecord[] rows = {
                record(S21, "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, A002, rows);
    }

}
