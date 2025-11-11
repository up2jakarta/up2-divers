package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.misc.AFastTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy3Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.BusinessBuilder.MAX_LEVEL;
import static io.github.up2jakarta.csv.fmt.misc.Tests.*;
import static io.github.up2jakarta.csv.impl.SegmentType.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FastDummy3Tests extends AFastTest<Dummy3Invoice, TURecord, TUError> {

    @Autowired
    FastDummy3Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(factory.builder().fast(Dummy3Invoice.class).build(S31).build(TUError::new, MAX_LEVEL));
    }

    private TURecord record(SegmentType type, String key, String... data) throws CodeListException {
        return new TURecord(type, key, data);
    }

    @Test
    void testEmpty() {
        checkEmpty(new TURecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S31, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check1Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S32, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S32, "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S33, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S34, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S32, rows);
    }

    @Test
    void testCardinality4() {
        // Given
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S32, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S33, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S34, S31, rows);
    }

    @Test
    void testDetached() {
        // Given
        final TURecord detached = record(S90, "TU2025R0099", "9999", "Warning", "Detached");
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S32, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S33, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S34, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final TURecord[] rows = fastInvoice(S31);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws IOException {
        // Given
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S32, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S33, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S34, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record(S34, "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final TURecord invalid = record(S90, "TU2025R0099", "1199", "Support", null);
        final TURecord[] rows = new TURecord[]{
                record(S31, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S32, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S33, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S34, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
