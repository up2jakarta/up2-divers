package io.github.up2jakarta.csv.fmt;

import io.github.up2jakarta.csv.TUConfiguration;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.fmt.misc.AFastTest;
import io.github.up2jakarta.csv.fmt.misc.Dummy1Invoice;
import io.github.up2jakarta.csv.impl.GroupType;
import io.github.up2jakarta.csv.impl.SegmentType;
import io.github.up2jakarta.xml.clv.CodeListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static io.github.up2jakarta.csv.api.IEvent.ERROR_CODE_LIST;
import static io.github.up2jakarta.csv.fmt.misc.Tests.fastInvoice;
import static io.github.up2jakarta.csv.impl.SegmentType.*;
import static io.github.up2jakarta.xml.api.SeverityType.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class FastDummy1Tests extends AFastTest<Dummy1Invoice, MiniRecord<SegmentType>, MiniError<GroupType, MiniRecord<SegmentType>>> {

    private final SimpleFastImporter<Dummy1Invoice, GroupType, SegmentType> fastImporter;

    @Autowired
    FastDummy1Tests(Up2Factory<GroupType> factory) throws BeanException {
        super(new SimpleFastImporter<>(factory, Dummy1Invoice.class, S11));
        this.fastImporter = this.get();
    }

    private MiniRecord<SegmentType> record(String... row) throws CodeListException {
        return fastImporter.transform(row);
    }

    @Test
    void testEmpty() throws BeanException {
        checkEmpty(new MiniRecord[0]);
    }

    @Test
    void testTypeException() throws BeanException {
        // Given
        final List<String[]> rows = Arrays.asList(
                new String[]{"RT", "TU2025R0099", null}, new String[]{"01", "TU2025R0099", null}
        );
        var pingPong = this.fastImporter.toExporter().toImporter();
        // When
        final CodeListException error = assertThrows(CodeListException.class, () -> pingPong.parse(rows));
        // Then
        assertEquals(ERROR_CODE_LIST, error.getCode());
        assertEquals(ERROR, error.getSeverity());
        assertEquals("Unknown value [RT] for CodeList[SegmentType]", error.getMessage());
    }

    @Test
    void testCardinality1() {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("11", "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check1Cardinality1(rows);
    }

    @Test
    void testCardinality2() throws BeanException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("12", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("12", "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S12, rows);
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("12", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S14, S11, rows);
    }

    @Test
    void testDetached() throws BeanException {
        // Given
        final MiniRecord<SegmentType> detached = record("90", "TU2025R0099", "9999", "Warning", "Detached");
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("12", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, rows);
    }

    @Test
    void testValid1() throws BeanException, IOException {
        // Given
        final MiniRecord<SegmentType>[] rows = fastInvoice(S11);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() throws BeanException, IOException {
        // Given
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("12", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record("14", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() throws BeanException {
        // Given
        final MiniRecord<SegmentType> invalid = record("90", "TU2025R0099", "1199", "Support", null);
        final MiniRecord<SegmentType>[] rows = new MiniRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("12", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("13", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("14", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, rows);
    }

}
