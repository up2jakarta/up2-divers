package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.ModeType;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.misc.AFullTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy1Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.EC_CODE_LIST;
import static io.github.up2jakarta.csv.fmt.misc.Tests.invoice;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FullDummy1Tests extends AFullTest<Dummy1Invoice, FullRecord<SegmentType, String>, FullError<GroupType, String, FullRecord<SegmentType, String>>> {

    private final Fixed06Generator rid = new Fixed06Generator();
    private final SimpleFullImporter<Dummy1Invoice, GroupType, SegmentType> fullImporter;

    @Autowired
    FullDummy1Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(factory.builder().full(Dummy1Invoice.class).build(S11).build());
        this.fullImporter = this.get();
    }

    private io.github.up2jakarta.csv.impl.InputRecord record(SegmentType type, String... data) throws CodeListException {
        return new io.github.up2jakarta.csv.impl.InputRecord(rid.get(), type, "TU2025R0099", data);
    }

    @Test
    void testEmpty() {
        checkEmpty(new io.github.up2jakarta.csv.impl.InputRecord[0]);
    }

    @Test
    void testCodeListException() throws BeanException {
        // Given
        var pingPong = this.fullImporter.toExporter().toImporter();
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> {
            var row = pingPong.transform("R001", "RT", "TU2025R0099", null);
            pingPong.parse(List.of(row));
        });
        // Then
        assertEquals(EC_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getLevel());
        assertEquals("Unknown value [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testCardinality1() {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S11, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S12, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S14, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S12, rows);
    }

    @Test
    void testCardinality4() {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S14, S11, rows);
    }

    @Test
    void testDetached() {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord detached = record(S09, "9999", "Warning", "Detached");
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S14, "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = invoice(S11, ModeType.FULL);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws IOException {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S14, "1199", "Software", "2", "120", "100", "20"),
                record(S14, "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final io.github.up2jakarta.csv.impl.InputRecord invalid = record(S09, "1199", "Support", null);
        final io.github.up2jakarta.csv.impl.InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S14, "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
