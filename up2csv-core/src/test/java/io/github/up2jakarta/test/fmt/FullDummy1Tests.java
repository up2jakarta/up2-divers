package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.Fixed06Generator;
import io.github.up2jakarta.csv.data.FullError;
import io.github.up2jakarta.csv.data.FullRecord;
import io.github.up2jakarta.csv.data.SimpleFullImporter;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AFullTest;
import io.github.up2jakarta.test.fmt.misc.Dummy1Invoice;
import io.github.up2jakarta.test.impl.InputRecord;
import io.github.up2jakarta.test.impl.SegmentType;
import io.github.up2jakarta.test.impl.TermType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.core.IMode.FULL;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.test.fmt.misc.Tests.invoice;
import static io.github.up2jakarta.test.impl.SegmentType.*;
import static io.github.up2jakarta.test.impl.TermType.A002;
import static io.github.up2jakarta.test.impl.TermType.D009;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FullDummy1Tests extends AFullTest<Dummy1Invoice, FullRecord<SegmentType>, FullError<TermType, FullRecord<SegmentType>>> {

    private final Fixed06Generator rid = new Fixed06Generator();
    private final SimpleFullImporter<Dummy1Invoice, TermType, SegmentType> fullImporter;

    @Autowired
    FullDummy1Tests(Up2Factory<TermType> factory) throws BeanException {
        super(factory.builder().full(Dummy1Invoice.class).build(SegmentType.class).build());
        this.fullImporter = this.get();
    }

    private InputRecord record(SegmentType type, String... data) throws CodeListException {
        return new InputRecord(rid.get(), type, "TU2025R0099", data);
    }

    @Test
    void testEmpty() {
        checkEmpty(new InputRecord[0]);
    }

    @Test
    void testCodeListException() throws BeanException {
        // Given
        final List<String[]> rows = Arrays.asList(
                new String[]{"R01", "RT", "TU2025R0099", null},
                new String[]{"R02", "01", "TU2025R0099", null}
        );
        var pingPong = this.fullImporter.toExporter().toImporter();
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> pingPong.transform(rows));
        // Then
        assertEquals(EC_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getLevel());
        assertEquals("Unknown input [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testCardinality1() {
        // Given
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S11, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
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
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S11, rows, importer.toExporter());
    }

    @Test
    void testDetached() {
        // Given
        final InputRecord detached = record(S09, "9999", "Warning", "Detached");
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
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
        final InputRecord[] rows = invoice(S11, FULL);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() {
        // Given
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
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
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S02, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S03, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S04, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, A002, rows);
    }

}
