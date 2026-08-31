package io.github.up2jakarta.test.fmt;

import io.github.up2jakarta.csv.core.Up2Factory;
import io.github.up2jakarta.csv.data.MessRecord;
import io.github.up2jakarta.csv.data.SimpleMessImporter;
import io.github.up2jakarta.csv.hdl.PropertyEvent;
import io.github.up2jakarta.lov.CodeListException;
import io.github.up2jakarta.lov.core.BeanException;
import io.github.up2jakarta.test.TUConfiguration;
import io.github.up2jakarta.test.fmt.misc.AMessTest;
import io.github.up2jakarta.test.fmt.misc.Dummy1Invoice;
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
import static io.github.up2jakarta.lov.SeverityType.ERROR;
import static io.github.up2jakarta.test.fmt.misc.Tests.messInvoice;
import static io.github.up2jakarta.test.impl.SegmentType.S02;
import static io.github.up2jakarta.test.impl.SegmentType.S11;
import static io.github.up2jakarta.test.impl.TermType.A002;
import static io.github.up2jakarta.test.impl.TermType.D009;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TUConfiguration.class)
class MessDummy1Tests extends AMessTest<Dummy1Invoice, MessRecord<SegmentType>, PropertyEvent<TermType, MessRecord<SegmentType>>> {

    private final SimpleMessImporter<Dummy1Invoice, TermType, SegmentType> smImporter;

    @Autowired
    MessDummy1Tests(Up2Factory<TermType> factory) throws BeanException {
        super(new SimpleMessImporter<>(factory, Dummy1Invoice.class, SegmentType.class));
        this.smImporter = this.get();
    }

    private MessRecord<SegmentType> record(String... row) throws CodeListException {
        return smImporter.transform(row);
    }

    @Test
    void testEmpty() {
        checkEmpty(new MessRecord[0]);
    }

    @Test
    void testCodeListException() throws BeanException {
        // Given
        final List<String[]> rows = Arrays.asList(
                new String[]{"RT", "TU2025R0099", null}, new String[]{"11", "TU2025R0099", null}
        );
        var pingPong = this.smImporter.toExporter().toImporter();
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
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("11", "TU2025R0088", "2025-03-12", "120", "100", "20"),
        };
        // When & Then
        check2Cardinality1(rows);
    }

    @Test
    void testCardinality2() {
        // Given
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20")
        };
        // When & Then
        checkCardinality2(rows);
    }

    @Test
    void testCardinality3() throws BeanException {
        // Given
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("02", "TU2025R0099", "SEL0088", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
        };
        // When & Then
        checkCardinality3(S02, rows, importer.toExporter());
    }

    @Test
    void testCardinality4() throws BeanException {
        // Given
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
        };
        // When & Then
        checkCardinality4(S11, rows, importer.toExporter());
    }

    @Test
    void testDetached() {
        // Given
        final MessRecord<SegmentType> detached = record("09", "TU2025R0099", "9999", "Warning", "Detached");
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                detached,
        };
        // When & Then
        checkDetached(detached, D009, rows);
    }

    @Test
    void testValid1() throws BeanException {
        // Given
        final MessRecord<SegmentType>[] rows = messInvoice(S11);
        // When & Then
        checkValid1(rows);
    }

    @Test
    void testValid2() {
        // Given
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                record("04", "TU2025R0099", "2299", "Hardware", "1", "600", "500", "100"),
        };
        // When & Then
        checkValid2(rows);
    }

    @Test
    void testValidation() {
        // Given
        final MessRecord<SegmentType> invalid = record("09", "TU2025R0099", "1199", "Support", null);
        final MessRecord<SegmentType>[] rows = new MessRecord[]{
                record("11", "TU2025R0099", "2025-03-12", "120", "100", "20"),
                record("02", "TU2025R0099", "SEL0099", "FR", "Paris", "75020", "99 Rue Up2JS", "Up2JS"),
                record("03", "TU2025R0099", "BUY0099", "FR", "Paris", "75020", "99 Rue Up2JB", "Up2JB"),
                record("04", "TU2025R0099", "1199", "Software", "2", "120", "100", "20"),
                invalid,
        };
        // When & Then
        checkValidation(invalid, A002, rows);
    }

}
