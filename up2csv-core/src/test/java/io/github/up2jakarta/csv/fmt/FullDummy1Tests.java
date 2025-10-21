package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.hdl.PathError;
import io.github.up2jakarta.csv.fmt.hdl.PathRecord;
import io.github.up2jakarta.csv.fmt.misc.AFullTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy1Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.InputRecord;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static io.github.up2jakarta.csv.core.EventHandler.ERROR_CODE_LIST;
import static io.github.up2jakarta.csv.core.ModeType.FULL;
import static io.github.up2jakarta.csv.fmt.misc.Tests.invoice;
import static io.github.up2jakarta.csv.fmt.misc.Tests.record;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FullDummy1Tests extends AFullTest<Dummy1Invoice, PathRecord<SegmentType>, PathError<GroupType, PathRecord<SegmentType>>> {

    @Autowired
    FullDummy1Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(new SimpleFullImporter<>(factory, Dummy1Invoice.class, S11).toExporter().toImporter());
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new InputRecord[0]);
    }

    @Test
    void testCodeListException() throws BeanException {
        // Given
        var pingPong = this.importer.toExporter().toImporter();
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> {
            var row = pingPong.record(null, 0, "R001", "RT", "TU2025R0099", null);
            pingPong.parse(List.of(row));
        });
        // Then
        assertEquals(ERROR_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getSeverity());
        assertEquals("Unknown value [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testCardinality1() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S11, "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
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
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S12, "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record(S14, "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S12, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final InputRecord[] rows = {
                record(S11, "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record(S12, "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record(S13, "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S14, S11, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final InputRecord detached = record(S90, "9999", "Warning", "Detached");
        final InputRecord[] rows = {
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
        final InputRecord[] rows = invoice(S11, FULL);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final InputRecord[] rows = {
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
    void testValidation() throws BeanException {
        // Given
        final InputRecord invalid = record(S90, "1199", "Support", null);
        final InputRecord[] rows = {
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
