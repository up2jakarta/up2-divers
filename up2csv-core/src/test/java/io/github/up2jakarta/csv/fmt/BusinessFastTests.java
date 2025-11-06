package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.fmt.misc.AFastTest;
import io.github.up2jakarta.csv.impl.InputError;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.MyFastAggregator;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.csv.impl.dto.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;

import static io.github.up2jakarta.csv.core.ModeType.FAST;
import static io.github.up2jakarta.csv.fmt.misc.Tests.invoice;
import static io.github.up2jakarta.csv.impl.SegmentType.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class BusinessFastTests extends AFastTest<Invoice, InputRecord, InputError> {

    @Autowired
    BusinessFastTests(MyFastAggregator aggregator) throws BeanException {
        super(aggregator);
    }

    private static InputRecord record(SegmentType type, String... data) {
        final String[] td = (type == S01) ? data : Arrays.copyOfRange(data, 1, data.length);
        return new InputRecord(null, type, data[0], td);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new InputRecord[0]);
    }

    @Test
    void testUnknownType() throws BeanException {
        // Given
        final InputRecord unknown = record(S00, "TU2025R0099", "2025-03-12", "120", "100", "20");
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                unknown
        };
        // When & Then
        checkDetached(unknown, rows);
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S01, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S02, "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S04, S01, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final InputRecord detached = record(S90, "TU2025R0099", "9999", "Warning", "Detached");
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final InputRecord[] rows = invoice(S01, FAST);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record(S04, "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final InputRecord invalid = record(S90, "TU2025R0099", "1199", "Support", null);
        final InputRecord[] rows = {
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
