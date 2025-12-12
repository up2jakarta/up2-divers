package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AUnitTest;
import io.github.up2jakarta.test.impl.InputError;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.MyUnitAggregator;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.dto.Invoice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static io.github.up2jakarta.csv.core.ModeType.UNIT;
import static io.github.up2jakarta.test.fmt.misc.Tests.invoice;
import static io.github.up2jakarta.test.impl.SegmentType.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class BusinessUnitTests extends AUnitTest<Invoice, InputRecord, InputError> {

    @Autowired
    BusinessUnitTests(MyUnitAggregator aggregator) throws BeanException {
        super(aggregator);
    }

    private static InputRecord record(SegmentType type, String... data) throws CodeListException {
        return new InputRecord(null, type, null, data);
    }

    @Test
    void testEmpty() {
        checkEmpty(new InputRecord[0]);
    }

    @Test
    void testCardinality1() {
        // Given
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S01, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() {
        // Given
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S02, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows);
    }

    @Test
    void testCardinality4() {
        // Given
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S04, S01, rows);
    }

    @Test
    void testDetached() {
        // Given
        final InputRecord detached = record(S09, "9999", "Warning", "Detached");
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final InputRecord[] rows = invoice(S01, UNIT);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws IOException {
        // Given
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
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
        final InputRecord invalid = record(S09, "1199", "Support", null);
        final InputRecord[] rows = new InputRecord[]{
                record(S01, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
